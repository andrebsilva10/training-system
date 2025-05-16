# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# This file is the source Rails uses to define your schema when running `bin/rails
# db:schema:load`. When creating a new database, `bin/rails db:schema:load` tends to
# be faster and is potentially less error prone than running all of your
# migrations from scratch. Old migrations may fail to apply correctly if those
# migrations use external dependencies or application code.
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema[8.0].define(version: 2025_05_15_003213) do
  create_table "action_text_rich_texts", force: :cascade do |t|
    t.string "name", null: false
    t.text "body"
    t.string "record_type", null: false
    t.bigint "record_id", null: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["record_type", "record_id", "name"], name: "index_action_text_rich_texts_uniqueness", unique: true
  end

  create_table "active_storage_attachments", force: :cascade do |t|
    t.string "name", null: false
    t.string "record_type", null: false
    t.bigint "record_id", null: false
    t.bigint "blob_id", null: false
    t.datetime "created_at", null: false
    t.index ["blob_id"], name: "index_active_storage_attachments_on_blob_id"
    t.index ["record_type", "record_id", "name", "blob_id"], name: "index_active_storage_attachments_uniqueness", unique: true
  end

  create_table "active_storage_blobs", force: :cascade do |t|
    t.string "key", null: false
    t.string "filename", null: false
    t.string "content_type"
    t.text "metadata"
    t.string "service_name", null: false
    t.bigint "byte_size", null: false
    t.string "checksum"
    t.datetime "created_at", null: false
    t.index ["key"], name: "index_active_storage_blobs_on_key", unique: true
  end

  create_table "active_storage_variant_records", force: :cascade do |t|
    t.bigint "blob_id", null: false
    t.string "variation_digest", null: false
    t.index ["blob_id", "variation_digest"], name: "index_active_storage_variant_records_uniqueness", unique: true
  end

  create_table "department_trainings", force: :cascade do |t|
    t.integer "department_id", null: false
    t.integer "training_id", null: false
    t.boolean "mandatory", default: true
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["department_id", "training_id"], name: "index_department_trainings_on_department_id_and_training_id", unique: true
    t.index ["department_id"], name: "index_department_trainings_on_department_id"
    t.index ["training_id"], name: "index_department_trainings_on_training_id"
  end

  create_table "departments", force: :cascade do |t|
    t.string "name", null: false
    t.text "description"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["name"], name: "index_departments_on_name", unique: true
  end

  create_table "sessions", force: :cascade do |t|
    t.integer "user_id", null: false
    t.string "token"
    t.string "ip_address"
    t.string "user_agent"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["user_id"], name: "index_sessions_on_user_id"
  end

  create_table "training_versions", force: :cascade do |t|
    t.integer "training_id", null: false
    t.integer "version_number", null: false
    t.integer "valid_for", null: false
    t.integer "status", default: 0, null: false
    t.date "release_date", null: false
    t.text "content"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["training_id", "version_number"], name: "index_training_versions_on_training_id_and_version_number", unique: true
    t.index ["training_id"], name: "index_training_versions_on_training_id"
  end

  create_table "trainings", force: :cascade do |t|
    t.string "title", null: false
    t.text "description"
    t.integer "duration"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["title"], name: "index_trainings_on_title", unique: true
  end

  create_table "user_departments", force: :cascade do |t|
    t.integer "user_id", null: false
    t.integer "department_id", null: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["department_id"], name: "index_user_departments_on_department_id"
    t.index ["user_id", "department_id"], name: "index_user_departments_on_user_id_and_department_id", unique: true
    t.index ["user_id"], name: "index_user_departments_on_user_id"
  end

  create_table "user_training_versions", force: :cascade do |t|
    t.integer "user_id", null: false
    t.integer "training_version_id", null: false
    t.integer "status", default: 0, null: false
    t.datetime "assigned_at"
    t.datetime "started_at"
    t.datetime "completed_at"
    t.date "due_date"
    t.text "notes"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.boolean "required", default: false
    t.index ["training_version_id"], name: "index_user_training_versions_on_training_version_id"
    t.index ["user_id", "training_version_id"], name: "index_user_training_version", unique: true
    t.index ["user_id"], name: "index_user_training_versions_on_user_id"
  end

  create_table "users", force: :cascade do |t|
    t.string "name", null: false
    t.string "email_address", null: false
    t.string "password_digest", null: false
    t.string "confirmation_code"
    t.string "string"
    t.datetime "confirmed_at"
    t.datetime "confirmation_sent_at"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.boolean "is_admin", default: false
    t.boolean "active", default: true
    t.index ["confirmation_code"], name: "index_users_on_confirmation_code", unique: true
    t.index ["email_address"], name: "index_users_on_email_address", unique: true
  end

  add_foreign_key "active_storage_attachments", "active_storage_blobs", column: "blob_id"
  add_foreign_key "active_storage_variant_records", "active_storage_blobs", column: "blob_id"
  add_foreign_key "department_trainings", "departments"
  add_foreign_key "department_trainings", "trainings"
  add_foreign_key "sessions", "users"
  add_foreign_key "training_versions", "trainings"
  add_foreign_key "user_departments", "departments"
  add_foreign_key "user_departments", "users"
  add_foreign_key "user_training_versions", "training_versions"
  add_foreign_key "user_training_versions", "users"
end
