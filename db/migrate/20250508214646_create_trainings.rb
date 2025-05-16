class CreateTrainings < ActiveRecord::Migration[8.0]
  def change
    create_table :trainings do |t|
      t.string :title, null: false
      t.text :description
      t.boolean :required, default: false
      t.integer :duration

      t.timestamps
    end
    
    add_index :trainings, :title, unique: true
  end
end