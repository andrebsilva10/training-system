json.extract! user_training_version, :id, :user_id, :training_version_id, :status, :assigned_at, :started_at, :completed_at, :due_date, :notes, :created_at, :updated_at
json.url user_training_version_url(user_training_version, format: :json)
