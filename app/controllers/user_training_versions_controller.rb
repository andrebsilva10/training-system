class UserTrainingVersionsController < Users::BaseController
  before_action :set_user_training_version, only: %i[show start complete update_status toggle_required destroy]
  before_action :authorize_access, except: %i[my_trainings user_trainings]
  
  # GET /my_trainings
  # Exibe os treinamentos do usuário atual agrupados por status
  def my_trainings
    add_breadcrumb "Meus Treinamentos", my_trainings_path
    
    @pending_trainings = Current.user.pending_trainings
    @in_progress_trainings = Current.user.in_progress_trainings
    @completed_trainings = Current.user.completed_trainings
    @expired_trainings = Current.user.expired_trainings
    @expiring_soon_trainings = Current.user.expiring_soon_trainings
  end

  # GET /users/1/trainings
  # Exibe os treinamentos de um usuário específico
  def user_trainings
    @user = User.find(params[:user_id])
    authorize_admin_or_self(@user)
    
    add_breadcrumb "Usuários", users_list_path if Current.user.admin?
    add_breadcrumb @user.name, user_trainings_path(@user)
    
    @user_training_versions = @user.user_training_versions
                                   .includes(training_version: :training)
                                   .order(assigned_at: :desc)
  end

  # GET /user_training_versions/1
  # Exibe detalhes de um treinamento específico
  def show
    @training = @user_training_version.training_version.training
    
    add_breadcrumb "Meus Treinamentos", my_trainings_path if Current.user == @user_training_version.user
    add_breadcrumb "Treinamentos de #{@user_training_version.user.name}", user_trainings_path(@user_training_version.user) if Current.user.admin? && Current.user != @user_training_version.user
    add_breadcrumb @training.title, user_training_version_path(@user_training_version)
  end

  # POST /user_training_versions/1/start
  # Inicia um treinamento
  def start
    if @user_training_version.pending?
      @user_training_version.update(status: :in_progress, started_at: Time.current)
      redirect_to user_training_version_path(@user_training_version), success: "Treinamento iniciado com sucesso!"
    else
      redirect_to user_training_version_path(@user_training_version), alert: "Este treinamento não pode ser iniciado."
    end
  end

  # POST /user_training_versions/1/complete
  # Marca um treinamento como concluído
  def complete
    if @user_training_version.in_progress?
      valid_for = @user_training_version.training_version.valid_for
      due_date = valid_for.present? ? (Date.current + valid_for.days) : nil
      
      @user_training_version.update(
        status: :completed, 
        completed_at: Time.current,
        due_date: due_date
      )
      
      redirect_to user_training_version_path(@user_training_version), success: "Parabéns! Treinamento concluído com sucesso!"
    else
      redirect_to user_training_version_path(@user_training_version), alert: "Este treinamento não pode ser concluído."
    end
  end

  # PATCH /user_training_versions/1/update_status
  # Atualiza o status de um treinamento
  def update_status
    authorize_admin
    
    previous_status = @user_training_version.status
    new_status = params[:status]
    
    # Se o status não mudou, não faz nada
    if previous_status == new_status
      redirect_to user_training_version_path(@user_training_version), notice: "Status não foi alterado."
      return
    end
    
    # Atualizar campos dependendo do status
    case new_status
    when "pending"
      @user_training_version.update(
        status: new_status,
        started_at: nil,
        completed_at: nil,
        due_date: nil
      )
    when "in_progress"
      @user_training_version.update(
        status: new_status,
        started_at: Time.current,
        completed_at: nil,
        due_date: nil
      )
    when "completed"
      valid_for = @user_training_version.training_version.valid_for
      due_date = valid_for.present? ? (Date.current + valid_for.days) : nil
      
      @user_training_version.update(
        status: new_status,
        started_at: @user_training_version.started_at || Time.current,
        completed_at: Time.current,
        due_date: due_date
      )
    when "expired"
      @user_training_version.update(
        status: new_status,
        due_date: Date.yesterday
      )
    end
    
    redirect_to user_training_version_path(@user_training_version), success: "Status do treinamento alterado com sucesso para #{t("user_training_version.status.#{new_status}")}."
  end
  
  # PATCH /user_training_versions/1/toggle_required
  # Alterna se um treinamento é obrigatório para um usuário
  def toggle_required
    authorize_admin
    
    current_value = @user_training_version.required
    @user_training_version.update(required: !current_value)
    
    message = @user_training_version.required? ? "obrigatório" : "opcional"
    redirect_back(
      fallback_location: user_training_version_path(@user_training_version), 
      success: "Treinamento agora é #{message} para este usuário."
    )
  end
  
  # DELETE /user_training_versions/1
  # Remove uma atribuição de treinamento
  def destroy
    authorize_admin
    user = @user_training_version.user
    @user_training_version.destroy
    
    redirect_to user_trainings_path(user), success: "Atribuição de treinamento removida com sucesso."
  end

  private
  
  # Configura o treinamento a ser usado nas ações
  def set_user_training_version
    @user_training_version = UserTrainingVersion.find(params[:id])
  end
  
  # Verifica se o usuário atual pode acessar o treinamento
  def authorize_access
    if @user_training_version&.user_id == Current.user.id || Current.user.admin?
      return true
    else
      redirect_to root_path, alert: "Acesso negado."
      return false
    end
  end
  
  # Verifica se o usuário é admin ou é o próprio usuário
  def authorize_admin_or_self(user)
    unless Current.user.admin? || Current.user.id == user.id
      redirect_to root_path, alert: "Acesso negado."
    end
  end
  
  # Verifica se o usuário é admin
  def authorize_admin
    redirect_to users_root_path, alert: "Acesso negado." unless Current.user.admin?
  end
end