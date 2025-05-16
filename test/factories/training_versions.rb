FactoryBot.define do
  factory :training_version do
    training { nil }
    version_number { 1 }
    valid_for { 1 }
    status { 1 }
    release_date { "2025-05-08" }
  end
end
