class DepartmentTrainingsController < Users::BaseController
  before_action :set_department_training, only: %i[ update destroy ]
  before_action :authorize_admin

  # POST /department_trainings
  def create
    @department_training = DepartmentTraining.new(department_training_params)

    if @department_training.save
      redirect_to training_path(@department_training.training), 
                  notice: "Treinamento atribuído ao setor com sucesso."
    else
      redirect_to training_path(@department_training.training), 
                  alert: "Erro ao atribuir treinamento ao setor."
    end
  end

  # PATCH/PUT /department_trainings/1
  def update
    if @department_training.update(department_training_params)
      respond_to do |format|
        format.html { redirect_to training_path(@department_training.training), notice: "Configuração atualizada com sucesso." }
        format.turbo_stream { head :ok }
      end
    else
      respond_to do |format|
        format.html { redirect_to training_path(@department_training.training), alert: "Erro ao atualizar configuração." }
        format.turbo_stream { head :unprocessable_entity }
      end
    end
  end

  # DELETE /department_trainings/1
  def destroy
    training = @department_training.training
    @department_training.destroy
    
    redirect_to training_path(training), 
                notice: "Treinamento removido do setor com sucesso."
  end

  private
    def set_department_training
      @department_training = DepartmentTraining.find(params[:id])
    end

    def department_training_params
      params.require(:department_training).permit(:department_id, :training_id, :mandatory)
    end
    
    def authorize_admin
      redirect_to root_path, alert: "Acesso negado." unless Current.user.admin?
    end
    
    def set_breadcrumbs
      add_breadcrumb "Treinamentos", trainings_path
      training = @department_training&.training || Training.find_by(id: params.dig(:department_training, :training_id))
      add_breadcrumb training.title, training_path(training) if training
    end
end