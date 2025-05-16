class Auth::RegistrationsController < Users::BaseController
  # layout "session"
  # allow_only_unauthenticated_access
  rate_limit to: 5, within: 3.minutes, only: :resend_confirmation,
             with: -> { redirect_to new_session_url, warning: t("sessions.flash.rate_limit") }
  before_action :authorize_admin

  def new
    @user = User.new
  end

  def create
    @user = User.new(user_params)
    send_confirmation = params[:send_confirmation_email] == "1"
    
    # Se vai enviar confirmação, não precisamos validar senha
    if send_confirmation
      @user.skip_password_validation = true
    elsif params[:user][:password].blank?
      @user.errors.add(:password, "não pode ficar em branco")
      return render :new, status: :unprocessable_entity
    else
      # Confirmar a conta automaticamente quando definir senha
      @user.confirmed_at = Time.current
    end
    
    if @user.save
      if send_confirmation
        # Enviar e-mail de confirmação
        UserMailer.confirmation_email(@user).deliver_later
        redirect_to users_list_path, notice: "Usuário criado com sucesso. Um e-mail de confirmação foi enviado para #{@user.email_address}."
      else
        # Não enviar e-mail, apenas redirecionar
        redirect_to users_list_path, notice: "Usuário criado com sucesso com a senha definida."
      end
    else
      render :new, status: :unprocessable_entity
    end
  end

  def confirmation
  end

  # Handles the form submission
  def confirm_account
    @user = User.find_by(email_address: params[:email_address])

    if @user&.confirm_account!(params[:confirmation_code])
      redirect_to new_session_path, notice: t("sessions.flash.account_confirmed")
    else
      flash.now[:alert] = t("sessions.flash.invalid_confirmation_code")
      render :confirmation, status: :unprocessable_entity
    end
  end

  def resend_confirmation
    @user = User.find_by(email_address: params[:email_address])

    if @user && !@user.confirmed?
      @user.generate_confirmation_code
      @user.save!
      UserMailer.confirmation_email(@user).deliver_later
      redirect_to confirmation_url(email_address: @user.email_address),
                  notice: t("sessions.flash.confirmation_token_resent")
    else
      flash[:alert] = "#{t('sessions.flash.account_already_confirmed')} E-mail: #{params[:email_address]}"
      redirect_to confirmation_url(email_address: @user&.email_address)
    end
  end

  private

  def user_params
    params.require(:user).permit(:name, :email_address, :password, :password_confirmation, :is_admin)
  end

    private
  
  def authorize_admin
    # Se já existir Current.user, verifique se é admin
    if Current.user
      unless Current.user.admin?
        redirect_to users_root_path, alert: "Acesso negado."
      end
    else
      # Se não estiver autenticado, redirecione para login
      store_location
      redirect_to new_session_path, alert: "Você precisa fazer login como administrador."
    end
  end

    def set_breadcrumbs
    add_breadcrumb t("breadcrumbs.create_user")
  end
end
