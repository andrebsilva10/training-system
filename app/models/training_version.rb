class TrainingVersion < ApplicationRecord
  belongs_to :training
  has_many :user_training_versions, dependent: :destroy
  has_many :users, through: :user_training_versions
  has_many_attached :documents
  has_rich_text :content
  
  validates :version_number, presence: true, uniqueness: { scope: :training_id }
  validates :valid_for, presence: true, numericality: { greater_than: 0 }
  validates :release_date, presence: true
  
  enum :status, {
    active: 0,
    inactive: 1,
    expired: 2
  }
  
  def display_name
    "#{training.title} - Versão #{version_number}"
  end
  
  # Verificar se a versão está expirada
  def expired?
    self[:status] == "expired" || (release_date + valid_for.days) < Date.current
  end
  
  # Atualizar status para expirado se necessário
  def update_status
    update(status: :expired) if expired? && !expired?
  end
end