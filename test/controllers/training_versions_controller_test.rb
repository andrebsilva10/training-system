require "test_helper"

class TrainingVersionsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @training_version = training_versions(:one)
  end

  test "should get index" do
    get training_versions_url
    assert_response :success
  end

  test "should get new" do
    get new_training_version_url
    assert_response :success
  end

  test "should create training_version" do
    assert_difference("TrainingVersion.count") do
      post training_versions_url, params: { training_version: { release_date: @training_version.release_date, status: @training_version.status, training_id: @training_version.training_id, valid_for: @training_version.valid_for, version_number: @training_version.version_number } }
    end

    assert_redirected_to training_version_url(TrainingVersion.last)
  end

  test "should show training_version" do
    get training_version_url(@training_version)
    assert_response :success
  end

  test "should get edit" do
    get edit_training_version_url(@training_version)
    assert_response :success
  end

  test "should update training_version" do
    patch training_version_url(@training_version), params: { training_version: { release_date: @training_version.release_date, status: @training_version.status, training_id: @training_version.training_id, valid_for: @training_version.valid_for, version_number: @training_version.version_number } }
    assert_redirected_to training_version_url(@training_version)
  end

  test "should destroy training_version" do
    assert_difference("TrainingVersion.count", -1) do
      delete training_version_url(@training_version)
    end

    assert_redirected_to training_versions_url
  end
end
