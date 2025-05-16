# Gestão de Treinamentos

## 1. Print da Tela

> *(Adicione aqui um print da tela de listagem ou cadastro de treinamentos)*

## 2. Regra de Negócio

- Apenas administradores podem criar, editar ou excluir treinamentos.
- Cada treinamento possui título único, descrição opcional e duração em minutos.
- Treinamentos podem ter múltiplas versões, sendo apenas algumas ativas.
- Treinamentos podem ser atribuídos a usuários ou departamentos (obrigatórios ou opcionais).
- Ao criar uma nova versão, usuários atribuídos recebem automaticamente a nova versão.
- Usuários podem visualizar seus treinamentos atribuídos e o progresso.

## 3. Como Utilizar

### Listar Treinamentos

- Acesse o menu **Treinamentos**.
- Visualize todos os treinamentos cadastrados em uma tabela com título, descrição e duração.
- Utilize as ações disponíveis: **Detalhes**, **Editar** e **Excluir**.

### Criar Novo Treinamento

1. Clique em **Novo Treinamento**.
2. Preencha os campos:
   - **Título** (obrigatório e único)
   - **Descrição** (opcional)
   - **Duração (minutos)** (opcional)
3. Clique em **Salvar**.

### Editar Treinamento

- Clique em **Editar** ao lado do treinamento desejado.
- Altere os campos necessários e salve.

### Visualizar Detalhes

- Clique em **Detalhes** para ver informações completas do treinamento.
- Veja as versões ativas e inativas do treinamento.
- Administradores podem criar nova versão ou gerenciar atribuições.

### Excluir Treinamento

- Clique em **Excluir** e confirme a exclusão.

---

## Exemplo de Campos do Treinamento

- **Título:** Nome do treinamento (ex: "Integração de Novos Colaboradores")
- **Descrição:** Texto livre explicando o objetivo do treinamento.
- **Duração:** Tempo estimado em minutos.

---

## Fluxo de Versões

- Cada treinamento pode ter várias versões.
- Nova versão é criada pelo botão **Nova Versão** nos detalhes do treinamento.
- Usuários atribuídos a versões antigas recebem automaticamente a nova versão.
- Versões podem ser **Ativas**, **Inativas** ou **Expiradas**.

---

## Atribuição de Treinamentos

- Treinamentos podem ser atribuídos a usuários ou departamentos.
- A atribuição pode ser obrigatória ou opcional.
- Usuários visualizam seus treinamentos em "Meus Treinamentos".

---

## Observações Técnicas

- O modelo `Training` possui validação de unicidade para o título.
- O controller `TrainingsController` restringe criação, edição e exclusão a administradores.
- As views utilizam TailwindCSS para estilização.
- As rotas principais estão em `/trainings` e `/trainings/:id`.

---

> Para mais detalhes técnicos, consulte o código-fonte nos arquivos:
> - `app/models/training.rb`
> - `app/controllers/trainings_controller.rb`
> - `app/views/trainings/`
