require "test_helper"

class DepartmentTrainingsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @department_training = department_trainings(:one)
  end

  test "should get index" do
    get department_trainings_url
    assert_response :success
  end

  test "should get new" do
    get new_department_training_url
    assert_response :success
  end

  test "should create department_training" do
    assert_difference("DepartmentTraining.count") do
      post department_trainings_url, params: { department_training: { department_id: @department_training.department_id, training_id: @department_training.training_id } }
    end

    assert_redirected_to department_training_url(DepartmentTraining.last)
  end

  test "should show department_training" do
    get department_training_url(@department_training)
    assert_response :success
  end

  test "should get edit" do
    get edit_department_training_url(@department_training)
    assert_response :success
  end

  test "should update department_training" do
    patch department_training_url(@department_training), params: { department_training: { department_id: @department_training.department_id, training_id: @department_training.training_id } }
    assert_redirected_to department_training_url(@department_training)
  end

  test "should destroy department_training" do
    assert_difference("DepartmentTraining.count", -1) do
      delete department_training_url(@department_training)
    end

    assert_redirected_to department_trainings_url
  end
end
