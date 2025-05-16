module Users
  class UsersController < BaseController
    before_action :authorize_admin, only: [:index, :new, :create, :edit, :update, :toggle_active, :add_department, :remove_department]
    before_action :set_user, only: [:show, :edit, :update, :toggle_active, :add_department, :remove_department]
    before_action :authorize_admin_or_self, only: [:show]

    # GET /users
    def index
      add_breadcrumb "Usuários", users_list_path
      @users = User.all.order(:name)
    end

    def new
      if Current.user.admin?
        redirect_to new_registration_path
      else
        redirect_to users_root_path, alert: "Acesso negado."
      end
    end

    def create
      @user = User.new(user_params)
      
      if @user.save
        redirect_to users_details_path(@user), notice: "Usuário criado com sucesso."
      else
        render :new, status: :unprocessable_entity
      end
    end

    # GET /users/1
    def show
      add_breadcrumb "Usuários", users_list_path
      add_breadcrumb @user.name, users_details_path(@user)
      
      # Dados para a aba de setores
      @user_training_versions = @user.user_training_versions
                                     .includes(training_version: :training)
                                     .order(assigned_at: :desc)
      
      # Dados para a aba de setores
      @user_departments = @user.departments.order(:name)
    end

    def edit
      redirect_to users_edit_profile_path(user_id: params[:id])
    end

    def update
      if @user.update(user_params)
        redirect_to users_details_path(@user), notice: "Usuário atualizado com sucesso."
      else
        redirect_to users_edit_profile_path(user_id: @user.id), alert: @user.errors.full_messages.join(", ")
      end
    end
    
    def toggle_active
      if @user.respond_to?(:active=)
        @user.update(active: !@user.active?)
        redirect_to users_details_path(@user), success: "Status do usuário alterado com sucesso."
      else
        redirect_to users_details_path(@user), alert: "Esta funcionalidade não está disponível."
      end
    end

    def add_department
      department = Department.find(params[:department_id])
      
      # Verifica se o usuário já tem este setor
      if @user.departments.include?(department)
        redirect_to users_details_path(@user, anchor: "tab-departments"), alert: "Usuário já está neste setor."
        return
      end
      
      # Adiciona o setor ao usuário
      @user.user_departments.create(department: department)
      
      redirect_to users_details_path(@user, anchor: "tab-departments"), success: "Setor adicionado com sucesso."
    end

    # DELETE /users/1/remove_department
    def remove_department
      department = Department.find(params[:department_id])
      
      # Remove a relação
      user_department = @user.user_departments.find_by(department_id: department.id)
      
      if user_department
        user_department.destroy
        redirect_to users_details_path(@user, anchor: "tab-departments"), success: "Setor removido com sucesso."
      else
        redirect_to users_details_path(@user, anchor: "tab-departments"), alert: "Setor não encontrado."
      end
    end

    private
    
    def set_user
      @user = User.find(params[:id])
    end
    
    def authorize_admin
      redirect_to users_root_path, alert: "Acesso negado." unless Current.user.admin?
    end
    
    def authorize_admin_or_self
      unless Current.user.admin? || Current.user.id == @user.id
        redirect_to users_root_path, alert: "Acesso negado."
      end
    end

    def user_params
      params.require(:user).permit(:name, :email_address, :password, :password_confirmation, :department_id, :is_admin)
    end
  end
end