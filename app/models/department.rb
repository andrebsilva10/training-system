class Department < ApplicationRecord
  has_many :department_trainings, dependent: :destroy
  has_many :trainings, through: :department_trainings

  has_many :user_departments, dependent: :destroy
  has_many :users, through: :user_departments
  
  validates :name, presence: true, uniqueness: true
  
  def assign_trainings_to_user(user)
    return unless user.department_id == id
    
    department_trainings.where(mandatory: true).each do |dept_training|
      # Encontrar a versão ativa mais recente
      latest_version = dept_training.training.active_versions.order(version_number: :desc).first
      next unless latest_version
      
      UserTrainingVersion.create(
        user: user,
        training_version: latest_version,
        assigned_at: Time.current,
        status: :pending
      )
    end
  end
end