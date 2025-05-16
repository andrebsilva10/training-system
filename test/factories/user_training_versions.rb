FactoryBot.define do
  factory :user_training_version do
    user { nil }
    training_version { nil }
    status { 1 }
    assigned_at { "2025-05-08 18:47:00" }
    started_at { "2025-05-08 18:47:00" }
    completed_at { "2025-05-08 18:47:00" }
    due_date { "2025-05-08" }
    notes { "MyText" }
  end
end
