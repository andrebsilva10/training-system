class RemoveRequiredFromTrainings < ActiveRecord::Migration[8.0]
  def change
    remove_column :trainings, :required, :boolean
  end
end