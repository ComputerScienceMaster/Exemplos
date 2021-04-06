@Echo off

set PGUSER=postgres

set PGPASSWORD=86111411



c:

cd \

cd C:\postgres\postgres\bin

@echo "Aguarde enquanto o banco de dados é atualizado..."

psql -U postgres -c "create database jushelp"