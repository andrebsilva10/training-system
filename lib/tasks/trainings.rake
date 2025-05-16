namespace :trainings do
  desc "Verificar e atualizar status de treinamentos vencidos"
  task check_expired: :environment do
    # Verificar versões de treinamento expiradas
    TrainingVersion.where(status: :active).find_each do |version|
      version.update_status
    end
    
    # Verificar treinamentos de usuários expirados
    UserTrainingVersion.where(status: :completed).find_each do |utv|
      utv.update_expired_status
    end
    
    puts "#{Time.now} - Verificação de treinamentos vencidos concluída"
  end
end