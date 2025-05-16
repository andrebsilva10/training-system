require "test_helper"

class UserTrainingVersionsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user_training_version = user_training_versions(:one)
  end

  test "should get index" do
    get user_training_versions_url
    assert_response :success
  end

  test "should get new" do
    get new_user_training_version_url
    assert_response :success
  end

  test "should create user_training_version" do
    assert_difference("UserTrainingVersion.count") do
      post user_training_versions_url, params: { user_training_version: { assigned_at: @user_training_version.assigned_at, completed_at: @user_training_version.completed_at, due_date: @user_training_version.due_date, notes: @user_training_version.notes, started_at: @user_training_version.started_at, status: @user_training_version.status, training_version_id: @user_training_version.training_version_id, user_id: @user_training_version.user_id } }
    end

    assert_redirected_to user_training_version_url(UserTrainingVersion.last)
  end

  test "should show user_training_version" do
    get user_training_version_url(@user_training_version)
    assert_response :success
  end

  test "should get edit" do
    get edit_user_training_version_url(@user_training_version)
    assert_response :success
  end

  test "should update user_training_version" do
    patch user_training_version_url(@user_training_version), params: { user_training_version: { assigned_at: @user_training_version.assigned_at, completed_at: @user_training_version.completed_at, due_date: @user_training_version.due_date, notes: @user_training_version.notes, started_at: @user_training_version.started_at, status: @user_training_version.status, training_version_id: @user_training_version.training_version_id, user_id: @user_training_version.user_id } }
    assert_redirected_to user_training_version_url(@user_training_version)
  end

  test "should destroy user_training_version" do
    assert_difference("UserTrainingVersion.count", -1) do
      delete user_training_version_url(@user_training_version)
    end

    assert_redirected_to user_training_versions_url
  end
end
