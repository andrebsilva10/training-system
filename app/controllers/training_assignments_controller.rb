class TrainingAssignmentsController < Users::BaseController
  before_action :authorize_admin
  
  def index
    @users = User.all.order(:name)
    @departments = Department.all.order(:name)
    @trainings = Training.includes(:training_versions).where(training_versions: { status: :active }).order(:title)
    
    # Se um treinamento específico foi passado na URL
    if params[:training_id].present?
      @selected_training = Training.find_by(id: params[:training_id])
      if @selected_training
        # Carregamos setores e usuários associados a este treinamento
        @training_departments = @selected_training.department_trainings.includes(:department)
        @training_users = @selected_training.assigned_users
      end
    end
    
    if params[:user_id].present?
      @selected_user = User.find_by(id: params[:user_id])
      @user_trainings = @selected_user.user_training_versions.includes(training_version: :training) if @selected_user
    end
    
    if params[:department_id].present?
      @selected_department = Department.find_by(id: params[:department_id])
      @department_users = @selected_department.users if @selected_department
      @department_trainings = @selected_department.department_trainings.includes(:training) if @selected_department
    end
  end
    
  def assign_to_user
    @user = User.find(params[:user_id])
    @training = Training.find(params[:training_id])
    required = params[:required] == "1"
    
    # Usar o método aprimorado de atribuição no modelo Training
    if @training.assign_to_user(@user, required)
      redirect_back fallback_location: assign_trainings_path, success: "Treinamento atribuído com sucesso."
    else
      redirect_back fallback_location: assign_trainings_path, success: "Atribuição atualizada com sucesso."
    end
  end

  def assign_to_department
    @department = Department.find(params[:department_id])
    @training = Training.find(params[:training_id])
    required = params[:required] == "1"
    
    # Verificar se já existe associação com setor
    existing = DepartmentTraining.find_by(department: @department, training: @training)
    
    if existing
      existing.update(mandatory: required)
      message = "Associação atualizada com sucesso."
    else
      DepartmentTraining.create(department: @department, training: @training, mandatory: required)
      message = "Treinamento associado ao setor com sucesso."
    end
    
    # Opcionalmente, atribuir a todos os usuários do setor
    if params[:assign_to_users] == "1"
      assigned_count = 0
      
      @department.users.each do |user|
        # Usar o método aprimorado no modelo Training
        assigned_count += 1 if @training.assign_to_user(user, required)
      end
      
      if assigned_count > 0
        message += " Treinamento também foi atribuído a #{assigned_count} usuários do setor."
      end
    end
    
    redirect_back fallback_location: assign_trainings_path, success: message
  end

  def remove_department_training
    dept_training = DepartmentTraining.find(params[:id])
    department_id = dept_training.department_id
    
    dept_training.destroy
    
    redirect_back fallback_location: assign_trainings_path, 
                notice: "Treinamento removido do setor com sucesso."
  end
  
  private
  
  def authorize_admin
    redirect_to users_root_path, alert: "Acesso negado." unless Current.user.admin?
  end
  
  def set_breadcrumbs
    add_breadcrumb "Atribuir Treinamentos", assign_trainings_path
  end
end