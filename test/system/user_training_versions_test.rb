require "application_system_test_case"

class UserTrainingVersionsTest < ApplicationSystemTestCase
  setup do
    @user_training_version = user_training_versions(:one)
  end

  test "visiting the index" do
    visit user_training_versions_url
    assert_selector "h1", text: "User training versions"
  end

  test "should create user training version" do
    visit user_training_versions_url
    click_on "New user training version"

    fill_in "Assigned at", with: @user_training_version.assigned_at
    fill_in "Completed at", with: @user_training_version.completed_at
    fill_in "Due date", with: @user_training_version.due_date
    fill_in "Notes", with: @user_training_version.notes
    fill_in "Started at", with: @user_training_version.started_at
    fill_in "Status", with: @user_training_version.status
    fill_in "Training version", with: @user_training_version.training_version_id
    fill_in "User", with: @user_training_version.user_id
    click_on "Create User training version"

    assert_text "User training version was successfully created"
    click_on "Back"
  end

  test "should update User training version" do
    visit user_training_version_url(@user_training_version)
    click_on "Edit this user training version", match: :first

    fill_in "Assigned at", with: @user_training_version.assigned_at.to_s
    fill_in "Completed at", with: @user_training_version.completed_at.to_s
    fill_in "Due date", with: @user_training_version.due_date
    fill_in "Notes", with: @user_training_version.notes
    fill_in "Started at", with: @user_training_version.started_at.to_s
    fill_in "Status", with: @user_training_version.status
    fill_in "Training version", with: @user_training_version.training_version_id
    fill_in "User", with: @user_training_version.user_id
    click_on "Update User training version"

    assert_text "User training version was successfully updated"
    click_on "Back"
  end

  test "should destroy User training version" do
    visit user_training_version_url(@user_training_version)
    accept_confirm { click_on "Destroy this user training version", match: :first }

    assert_text "User training version was successfully destroyed"
  end
end
