class TrainingVersionsController < Users::BaseController
  before_action :set_training
  before_action :set_training_version, only: %i[ show edit update destroy ]
  before_action :authorize_admin, except: [:show]

  # GET /trainings/1/versions
  def index
    @training_versions = @training.training_versions.order(version_number: :desc)
  end

  # GET /trainings/1/versions/1
  def show
    @users_completed = @training_version.user_training_versions.completed.includes(:user)
    @users_in_progress = @training_version.user_training_versions.in_progress.includes(:user)
    @users_pending = @training_version.user_training_versions.pending.includes(:user)
  end

  # GET /trainings/1/versions/new
  def new
    @training_version = @training.training_versions.build
    @last_version = @training.training_versions.order(version_number: :desc).first
    @next_version_number = (@last_version&.version_number || 0) + 1
    
    # Valores padrão para novo registro
    @training_version.version_number = @next_version_number
    @training_version.release_date = Date.current
    @training_version.status = :active
  end

  # GET /trainings/1/versions/1/edit
  def edit
    @next_version_number = @training_version.version_number
  end

  # POST /trainings/1/versions
  def create
    @training_version = @training.training_versions.build(training_version_params)

    if @training_version.save
      if params[:deactivate_previous] == "1"
        @training.deactivate_previous_versions(@training_version)
      end
      
      # Atribuir automaticamente esta nova versão aos usuários existentes
      @training.update_users_to_new_version(@training_version)
      
      redirect_to training_training_version_path(@training, @training_version), 
                  notice: "Versão de treinamento criada com sucesso. Usuários existentes foram atualizados para esta versão."
    else
      @last_version = @training.training_versions.order(version_number: :desc).first
      @next_version_number = (@last_version&.version_number || 0) + 1
      render :new, status: :unprocessable_entity
    end
  end

  # PATCH/PUT /trainings/1/versions/1
  def update
    if @training_version.update(training_version_params)
      redirect_to training_training_version_path(@training, @training_version), 
                  notice: "Versão de treinamento atualizada com sucesso."
    else
      render :edit, status: :unprocessable_entity
    end
  end

  # DELETE /trainings/1/versions/1
  def destroy
    @training_version.destroy
    redirect_to training_path(@training), notice: "Versão de treinamento removida com sucesso."
  end

  private
    def set_training
      @training = Training.find(params[:training_id])
    end
    
    def set_training_version
      @training_version = @training.training_versions.find(params[:id])
    end

    def training_version_params
      params.require(:training_version).permit(:version_number, :valid_for, :status, :release_date, :content, documents: [])
    end
    
    def authorize_admin
      redirect_to root_path, alert: "Acesso negado." unless Current.user.admin?
    end
    
    def set_breadcrumbs
      add_breadcrumb "Treinamentos", trainings_path
      add_breadcrumb @training.title, training_path(@training)
      
      if @training_version&.persisted?
        add_breadcrumb "Versão #{@training_version.version_number}", training_training_version_path(@training, @training_version)
      else
        add_breadcrumb "Nova Versão"
      end
    end
end