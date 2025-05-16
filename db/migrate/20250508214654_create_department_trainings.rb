class CreateDepartmentTrainings < ActiveRecord::Migration[8.0]
  def change
    create_table :department_trainings do |t|
      t.references :department, null: false, foreign_key: true
      t.references :training, null: false, foreign_key: true
      t.boolean :mandatory, default: true

      t.timestamps
    end
    
    add_index :department_trainings, [:department_id, :training_id], unique: true
  end
end
