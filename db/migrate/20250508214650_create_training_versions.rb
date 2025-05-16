class CreateTrainingVersions < ActiveRecord::Migration[8.0]
  def change
    create_table :training_versions do |t|
      t.references :training, null: false, foreign_key: true
      t.integer :version_number, null: false
      t.integer :valid_for, null: false
      t.integer :status, default: 0, null: false
      t.date :release_date, null: false
      t.text :content

      t.timestamps
    end
    
    add_index :training_versions, [:training_id, :version_number], unique: true
  end
end
