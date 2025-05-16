require "application_system_test_case"

class UserLoginTest < ApplicationSystemTestCase
  setup do
    @user = create(:user)
    visit new_session_path
  end

  test "user can log in" do
    fill_in :email_address, with: @user.email_address
    fill_in :password, with: "password"
    click_on I18n.t("sessions.actions.sign_in")

    assert_current_path users_root_path
  end

  test "admin cannot log in with invalid credentials" do
    fill_in :email_address, with: @user.email_address
    fill_in :password, with: "wrongpassword"
    click_on I18n.t("sessions.actions.sign_in")

    assert_alert I18n.t("sessions.flash.error")

    assert_current_path new_session_path
  end

  test "unconfirmed user cannot log in" do
    unconfirmed_user = create(:unconfirmed_user)
    fill_in :email_address, with: unconfirmed_user.email_address
    fill_in :password, with: "password"
    click_on I18n.t("sessions.actions.sign_in")

    assert_alert I18n.t("sessions.flash.unconfirmed_account")
    assert_current_path confirmation_path(email_address: unconfirmed_user.email_address)
  end

  test "user can navigate to forgot password page" do
    click_on I18n.t("sessions.actions.forget_password")

    assert_current_path new_password_path
  end

end
