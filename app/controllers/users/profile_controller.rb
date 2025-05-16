class Users::ProfileController < Users::BaseController
  before_action :set_user

  def edit; end

  def update
    if @user.update_with_password(user_params)
      redirect_to users_edit_profile_path, success: I18n.t("users.profile.update.success")
    else
      render :edit, status: :unprocessable_entity
    end
  end

  def update_avatar
    if @user.update(user_params.slice(:avatar))
      redirect_to users_edit_profile_path
    else
      render :edit, status: :unprocessable_entity
    end
  end

  def delete_avatar
    @user.avatar.purge
    redirect_to users_edit_profile_path, notice: t(".success")
  end

  def edit_password; end

  def update_password
    pwd_params = params.require(:user)
                       .permit(:current_password, :password, :password_confirmation)

    if @user.update_with_password(pwd_params)
      redirect_to users_edit_password_path, notice: t(".success")
    else
      render :edit_password, status: :unprocessable_entity
    end
  end

  private

  def set_user
    if params[:user_id] && Current.user.admin?
      @user = User.find(params[:user_id])
    else
      @user = User.find(Current.user.id)
    end
  end

  def user_params
    params.require(:user).permit(:name, :email_address, :current_password, :avatar)
  end

  def set_breadcrumbs
    add_breadcrumb t("breadcrumbs.profile")
  end
end
