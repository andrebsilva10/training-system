class User < ApplicationRecord
  include AccountConfirmable

  attr_accessor :skip_password_validation

  has_secure_password
  normalizes :email_address, with: ->(e) { e.strip.downcase }

  has_many :user_departments, dependent: :destroy
  has_many :departments, through: :user_departments

  has_many :user_training_versions, dependent: :destroy
  has_many :sessions, dependent: :destroy
  has_one_attached :avatar

  after_update :assign_department_trainings, if: :saved_change_to_department_id?

  validates :name, presence: true
  validates :email_address, presence: true, uniqueness: { case_sensitive: false }, format: { with: URI::MailTo::EMAIL_REGEXP }
  validates :password, confirmation: true, presence: true, length: { minimum: 6 }, if: :password_required?

  def avatar_url
    return ActionController::Base.helpers.asset_path("avatars/default_avatar.png") unless avatar.attached?

    Rails.application.routes.url_helpers.rails_blob_url(avatar, only_path: true)
  end

  def update_with_password(params)
    if authenticate(params[:current_password])
      update(params.except(:current_password))
    else
      errors.add(:current_password, I18n.t("activerecord.errors.messages.wrong_current_password"))
      assign_attributes(params.except(:current_password))
      false
    end
  end

    # Métodos para verificar permissões
  def admin?
    is_admin == true
  end
  
  # Obter treinamentos por status
  def pending_trainings
    user_training_versions.pending
  end
  
  def in_progress_trainings
    user_training_versions.in_progress
  end
  
  def completed_trainings
    user_training_versions.completed
  end
  
  def expired_trainings
    user_training_versions.expired
  end
  
  def expiring_soon_trainings(days = 30)
    user_training_versions.completed.select { |utv| utv.expiring_soon?(days) }
  end
  
  # Verifica e atualiza status de treinamentos expirados
  def update_expired_trainings
    user_training_versions.completed.each(&:update_expired_status)
  end

  private
  
  def assign_department_trainings
    return unless department.present?
    
    department.assign_trainings_to_user(self)
  end

  private

  def password_required?
    return false if skip_password_validation
    # Comportamento padrão: senha é necessária para novos registros ou quando senha está sendo alterada
    new_record? || !password.nil? || !password_confirmation.nil?
  end
end
