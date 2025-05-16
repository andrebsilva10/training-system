class Auth::PasswordsController < ApplicationController
  layout "session"
  allow_only_unauthenticated_access except: [:admin_reset, :admin_reset_email]
  before_action :set_user_by_token, only: %i[ edit update ]
  before_action :authorize_admin, only: [:admin_reset]

  def new
  end

  def create
    if (user = User.find_by(email_address: params[:email_address]))
      PasswordsMailer.reset(user).deliver_later
    end

    redirect_to new_session_path, notice: t("sessions.flash.send_reset_password_instructions")
  end

  def edit
  end

  def update
    if @user.update(params.require(:user).permit(:password, :password_confirmation))
      redirect_to new_session_path, notice: t("sessions.flash.password_updated")
    else
      render :edit, status: :unprocessable_entity
    end
  end

  # Método para redefinição administrativa
  def admin_reset
    @user = User.find(params[:id])
    
    # Verificar se o usuário tem permissão
    unless Current.user&.admin?
      redirect_to users_root_path, alert: "Acesso negado."
      return
    end
    
    # Atualizar a senha diretamente
    if @user.update(password: params[:password])
      redirect_to users_details_path(@user), success: "Senha redefinida com sucesso."
    else
      redirect_to users_details_path(@user), alert: "Erro ao redefinir senha: #{@user.errors.full_messages.join(', ')}"
    end
  end

  def admin_reset_email
    @user = User.find(params[:id])
    
    # Verificar se o usuário tem permissão
    unless Current.user&.admin?
      redirect_to users_root_path, alert: "Acesso negado."
      return
    end
    
    # Enviar o e-mail de redefinição de senha
    PasswordsMailer.reset(@user).deliver_later
    
    redirect_to users_details_path(@user), success: "E-mail de redefinição de senha enviado com sucesso para #{@user.email_address}."
  end

  private

  def set_user_by_token
    @user = User.find_by_password_reset_token!(params[:token])
  rescue ActiveSupport::MessageVerifier::InvalidSignature
    redirect_to new_password_path, alert: t("sessions.flash.token_invalid")
  end

  private
    
  def authorize_admin
    unless Current.user&.admin?
      flash[:alert] = "Acesso negado."
      redirect_to users_root_path
    end
  end
end
