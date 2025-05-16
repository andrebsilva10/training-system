class DepartmentsController < Users::BaseController
  before_action :set_department, only: %i[ show edit update destroy ]
  before_action :authorize_admin

  # GET /departments
  def index
    @departments = Department.all
  end

  # GET /departments/1
  def show
    @users = @department.users
    @department_trainings = @department.department_trainings.includes(:training)
  end

  # GET /departments/new
  def new
    @department = Department.new
  end

  # GET /departments/1/edit
  def edit
  end
  

  # POST /departments
  def create
    @department = Department.new(department_params)

    if @department.save
      redirect_to @department, notice: "Setor criado com sucesso."
    else
      render :new, status: :unprocessable_entity
    end
  end

    def set_breadcrumbs
      add_breadcrumb "Setores", departments_path
    end

  # PATCH/PUT /departments/1
  def update
    if @department.update(department_params)
      redirect_to @department, notice: "Setor atualizado com sucesso."
    else
      render :edit, status: :unprocessable_entity
    end
  end

  # DELETE /departments/1
  def destroy
    @department.destroy
    redirect_to departments_url, notice: "Setor removido com sucesso."
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_department
      @department = Department.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def department_params
      params.require(:department).permit(:name, :description)
    end
    
    def authorize_admin
      redirect_to root_path, alert: "Acesso negado." unless Current.user.admin?
    end
end