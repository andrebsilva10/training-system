class CreateUserTrainingVersions < ActiveRecord::Migration[8.0]
  def change
    create_table :user_training_versions do |t|
      t.references :user, null: false, foreign_key: true
      t.references :training_version, null: false, foreign_key: true
      t.integer :status, default: 0, null: false
      t.datetime :assigned_at
      t.datetime :started_at
      t.datetime :completed_at
      t.date :due_date
      t.text :notes

      t.timestamps
    end
    
    add_index :user_training_versions, [:user_id, :training_version_id], unique: true, name: 'index_user_training_version'
  end
end