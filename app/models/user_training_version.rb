class UserTrainingVersion < ApplicationRecord
  belongs_to :user
  belongs_to :training_version
  
  enum :status, {
    pending: 0,
    in_progress: 1,
    completed: 2,
    expired: 3
  }
  
  validates :user_id, uniqueness: { scope: :training_version_id }
  
  before_create :set_assigned_at
  after_save :update_due_date, if: -> { saved_change_to_completed_at? && completed_at.present? }
  
  scope :active, -> { where.not(status: :expired) }
  scope :pending_or_in_progress, -> { where(status: [:pending, :in_progress]) }
  
  def mark_as_started
    update(status: :in_progress, started_at: Time.current) if pending?
  end
  
  def mark_as_completed
    if pending? || in_progress?
      update(status: :completed, completed_at: Time.current)
    end
  end
  
  def mark_as_expired
    update(status: :expired) if completed?
  end
  
  def expired?
    completed? && due_date.present? && due_date < Date.current
  end
  
  def expiring_soon?(days = 30)
    completed? && due_date.present? && due_date.between?(Date.current, days.days.from_now)
  end
  
  # Verifica e atualiza o status para expirado se necessário
  def update_expired_status
    mark_as_expired if expired? && !expired?
  end
  
  # Verifica se há uma versão mais recente disponível
  def newer_version_available?
    latest_version = training_version.training.active_versions.order(version_number: :desc).first
    latest_version && latest_version.id != training_version.id
  end
  
  private
  
  def set_assigned_at
    self.assigned_at ||= Time.current
  end
  
  def update_due_date
    return unless completed? && training_version.valid_for.present?
    
    # Atualiza a data de vencimento com base na data de conclusão
    update_column(:due_date, completed_at.to_date + training_version.valid_for.days)
  end
end