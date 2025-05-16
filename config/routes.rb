Rails.application.routes.draw do
  # Treinamentos de usuário
  resources :user_training_versions, only: [:show, :destroy] do
    member do
      post 'start'
      post 'complete'
      patch 'toggle_required' 
      patch 'update_status'  
    end
  end

  # Relação Setor-Treinamento
  resources :department_trainings, only: [:create, :update, :destroy]

  resources :departments

  # Treinamentos e versões
  resources :trainings do
    resources :training_versions, path: 'versions'
  end

  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # Reveal health status on /up that returns 200 if the app boots with no exceptions, otherwise 500.
  # Can be used by load balancers and uptime monitors to verify that the app is live.
  get "up" => "rails/health#show", as: :rails_health_check

  # Render dynamic PWA files from app/views/pwa/* (remember to link manifest in application.html.erb)
  # get "manifest" => "rails/pwa#manifest", as: :pwa_manifest
  # get "service-worker" => "rails/pwa#service_worker", as: :pwa_service_worker

  # Defines the root path route ("/")
  # root "posts#index"

  # TODO: Verificar se o root está correto
  root "home#index"
  # root 'dashboard#index'


  get 'assign_trainings', to: 'training_assignments#index', as: 'assign_trainings'
  post 'assign_to_user', to: 'training_assignments#assign_to_user', as: 'assign_to_user'
  post 'assign_to_department', to: 'training_assignments#assign_to_department', as: 'assign_to_department'
  delete 'remove_department_training/:id', to: 'training_assignments#remove_department_training', as: 'remove_department_training'
  
  namespace :auth, as: "", path: "" do
    resource :session, only: %i[ new create destroy ]
    resources :passwords, param: :token, except: %i[ index show ]

    get "signup", to: "registrations#new", as: :new_registration
    post "signup", to: "registrations#create", as: :registration

    get "confirmation", to: "registrations#confirmation", as: :confirmation
    patch "confirm_account", to: "registrations#confirm_account"
    patch "resend_confirmation", to: "registrations#resend_confirmation"

    post 'passwords/admin_reset/:id', to: 'passwords#admin_reset', as: 'admin_reset_password'
    post 'passwords/admin_reset_email/:id', to: 'passwords#admin_reset_email', as: 'admin_reset_email'
  end

  namespace :users do
    root "home#dashboard"
    
    get 'list', to: 'users#index', as: 'list'

    get 'users/new', to: 'users#new', as: 'new_user'
    post 'users', to: 'users#create'
    get "profile/edit", to: "profile#edit", as: :edit_profile

    patch "profile/update", to: "profile#update", as: :update_profile
    patch "profile/update_avatar", to: "profile#update_avatar", as: :update_avatar
    delete "profile/delete_avatar", to: "profile#delete_avatar", as: :delete_avatar
    get "profile/edit_password", to: "profile#edit_password", as: :edit_password
    patch "profile/update_password", to: "profile#update_password", as: :update_password
    
    # Rotas para gerenciamento de usuários
    get 'users/:id', to: 'users#show', as: 'details'
    resources :users, only: [:new, :create, :edit, :update]
    
    # Rotas para ações específicas de usuários
    patch 'users/:id/toggle_active', to: 'users#toggle_active', as: 'toggle_active_user'
    post 'users/:id/add_department', to: 'users#add_department', as: 'add_department_user'
    delete 'users/:id/remove_department', to: 'users#remove_department', as: 'remove_department_user'

    post 'passwords/admin_reset/:id', to: 'auth/passwords#admin_reset', as: 'admin_reset_password'
  end

  get 'my_trainings', to: 'user_training_versions#my_trainings', as: 'my_trainings'
  get 'users/:user_id/trainings', to: 'user_training_versions#user_trainings', as: 'user_trainings'
end