class Training < ApplicationRecord
  has_many :training_versions, dependent: :destroy
  has_many :department_trainings, dependent: :destroy
  has_many :departments, through: :department_trainings
  
  validates :title, presence: true, uniqueness: true
  
  # Escopo para versões ativas
  def active_versions
    training_versions.where(status: :active)
  end

  # Método para obter a versão ativa mais recente
  def active_version
    training_versions.where(status: :active).order(version_number: :desc).first
  end
  
  # Cria uma nova versão e atualiza automaticamente os usuários atribuídos a este treinamento
  def create_new_version(attributes = {})
    # Encontra o último número de versão
    last_version_number = training_versions.maximum(:version_number) || 0
    
    # Cria nova versão com número incrementado
    new_version = training_versions.create!(
      version_number: last_version_number + 1,
      release_date: Date.current,
      status: :active,
      **attributes
    )
    
    # Atualiza os usuários automaticamente para a nova versão
    update_users_to_new_version(new_version)
    
    new_version
  end
  
  # Método para atualizar usuários para uma nova versão de treinamento
  def update_users_to_new_version(new_version)
    # Encontrar todos os usuários atribuídos a qualquer versão deste treinamento
    existing_users = User.joins(user_training_versions: :training_version)
                        .where(training_versions: { training_id: id })
                        .distinct
                        
    existing_users.each do |user|
      # Verificar se o usuário já tem a nova versão
      unless UserTrainingVersion.exists?(user: user, training_version: new_version)
        # Verificar se o treinamento era obrigatório para o usuário
        last_training = UserTrainingVersion.joins(:training_version)
                                          .where(user: user, training_versions: { training_id: id })
                                          .order(created_at: :desc)
                                          .first
                                          
        # Criar registro da nova versão com o mesmo status de "obrigatório"
        required = last_training&.required? || false
        
        UserTrainingVersion.create(
          user: user,
          training_version: new_version,
          required: required,
          assigned_at: Time.current,
          status: :pending
        )
      end
    end
  end
  
  # Desativa todas as versões anteriores
  def deactivate_previous_versions(current_version)
    training_versions.where.not(id: current_version.id).update_all(status: :inactive)
  end

  # Método para obter usuários atribuídos especificamente a este treinamento
  def assigned_users
    UserTrainingVersion.joins(:training_version)
                      .where(training_versions: { training_id: id })
                      .where.not(status: :expired)
                      .includes(:user)
  end
  
  # Atribui treinamento a um usuário
  def assign_to_user(user, required = false)
    version = active_version
    return false unless version
    
    # Verificar se o usuário já tem uma versão ativa deste treinamento
    existing = UserTrainingVersion.joins(:training_version)
                                .where(user: user, training_versions: { training_id: id })
                                .where.not(status: :expired)
                                .first
    
    if existing
      # Verificar se a versão existente é a mais recente
      if existing.training_version_id != version.id
        # Atualizar para a versão mais recente
        UserTrainingVersion.create(
          user: user,
          training_version: version,
          required: required,
          assigned_at: Time.current,
          status: :pending
        )
        return true
      else
        # Apenas atualiza a obrigatoriedade se necessário
        existing.update(required: required) if existing.required != required
        return false
      end
    else
      # Criar uma nova atribuição
      UserTrainingVersion.create(
        user: user,
        training_version: version,
        required: required,
        assigned_at: Time.current,
        status: :pending
      )
      return true
    end
  end
  
  # Atribui treinamento a um setor
  def assign_to_department(department, mandatory = true)
    department_trainings.create(
      department: department,
      mandatory: mandatory
    )
  end
end