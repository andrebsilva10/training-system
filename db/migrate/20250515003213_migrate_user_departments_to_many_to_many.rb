class MigrateUserDepartmentsToManyToMany < ActiveRecord::Migration[8.0]
  def up
    # Transferir setores atuais dos usuários para a tabela de junção
    User.where.not(department_id: nil).find_each do |user|
      # Verificar se a relação já existe para evitar duplicação
      unless UserDepartment.exists?(user_id: user.id, department_id: user.department_id)
        UserDepartment.create!(user_id: user.id, department_id: user.department_id)
      end
    end
    
    # remover a coluna department_id da tabela users
    remove_reference :users, :department
  end
  
  def down
    raise ActiveRecord::IrreversibleMigration
  end
end