class TrainingsController < Users::BaseController

  before_action :set_training, only: %i[ show edit update destroy ]
  before_action :authorize_admin, except: [:index, :show]
  

  # GET /trainings
  def index
    @trainings = Training.all
  end

  # GET /trainings/1
  def show
    @active_versions = @training.active_versions.order(version_number: :desc)
    @inactive_versions = @training.training_versions.where.not(status: :active).order(version_number: :desc)
  end

  # GET /trainings/new
  def new
    @training = Training.new
  end

  # GET /trainings/1/edit
  def edit
  end

  # POST /trainings
  def create
    @training = Training.new(training_params)

    if @training.save
      redirect_to @training, notice: "Treinamento criado com sucesso."
    else
      render :new, status: :unprocessable_entity
    end
  end

  # PATCH/PUT /trainings/1
  def update
    if @training.update(training_params)
      redirect_to @training, notice: "Treinamento atualizado com sucesso."
    else
      render :edit, status: :unprocessable_entity
    end
  end

  # DELETE /trainings/1
  def destroy
    @training.destroy
    redirect_to trainings_url, notice: "Treinamento removido com sucesso."
  end

  private
    def set_training
      @training = Training.find(params[:id])
    end

    def training_params
      params.require(:training).permit(:title, :description, :required, :duration)
    end
    
    def authorize_admin
      redirect_to root_path, alert: "Acesso negado." unless Current.user.admin?
    end

    def set_breadcrumbs
      add_breadcrumb "Treinamentos", trainings_path
    end
end