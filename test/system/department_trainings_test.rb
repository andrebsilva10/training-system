require "application_system_test_case"

class DepartmentTrainingsTest < ApplicationSystemTestCase
  setup do
    @department_training = department_trainings(:one)
  end

  test "visiting the index" do
    visit department_trainings_url
    assert_selector "h1", text: "Department trainings"
  end

  test "should create department training" do
    visit department_trainings_url
    click_on "New department training"

    fill_in "Department", with: @department_training.department_id
    fill_in "Training", with: @department_training.training_id
    click_on "Create Department training"

    assert_text "Department training was successfully created"
    click_on "Back"
  end

  test "should update Department training" do
    visit department_training_url(@department_training)
    click_on "Edit this department training", match: :first

    fill_in "Department", with: @department_training.department_id
    fill_in "Training", with: @department_training.training_id
    click_on "Update Department training"

    assert_text "Department training was successfully updated"
    click_on "Back"
  end

  test "should destroy Department training" do
    visit department_training_url(@department_training)
    accept_confirm { click_on "Destroy this department training", match: :first }

    assert_text "Department training was successfully destroyed"
  end
end
