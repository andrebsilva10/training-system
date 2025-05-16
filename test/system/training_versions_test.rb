require "application_system_test_case"

class TrainingVersionsTest < ApplicationSystemTestCase
  setup do
    @training_version = training_versions(:one)
  end

  test "visiting the index" do
    visit training_versions_url
    assert_selector "h1", text: "Training versions"
  end

  test "should create training version" do
    visit training_versions_url
    click_on "New training version"

    fill_in "Release date", with: @training_version.release_date
    fill_in "Status", with: @training_version.status
    fill_in "Training", with: @training_version.training_id
    fill_in "Valid for", with: @training_version.valid_for
    fill_in "Version number", with: @training_version.version_number
    click_on "Create Training version"

    assert_text "Training version was successfully created"
    click_on "Back"
  end

  test "should update Training version" do
    visit training_version_url(@training_version)
    click_on "Edit this training version", match: :first

    fill_in "Release date", with: @training_version.release_date
    fill_in "Status", with: @training_version.status
    fill_in "Training", with: @training_version.training_id
    fill_in "Valid for", with: @training_version.valid_for
    fill_in "Version number", with: @training_version.version_number
    click_on "Update Training version"

    assert_text "Training version was successfully updated"
    click_on "Back"
  end

  test "should destroy Training version" do
    visit training_version_url(@training_version)
    accept_confirm { click_on "Destroy this training version", match: :first }

    assert_text "Training version was successfully destroyed"
  end
end
