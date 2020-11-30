# Projeto Célula Financeiro e Controladoria

Projeto para controlar as receitas e as despesas de vários clientes e a empresa.
O projeto foi usado as seguintes tecnologias.

  - **Swagger** (Usado para documentar os end-points da API)
  - **Docker** (Usando como banco de desenvolvimento)
  - **Oracle Database 12c Enterprise Edition** (Banco de dados utilizado)
  - **Java 11**
  - **Spring Boot 2.3.4.RELEASE - Maven**
  - **Lombok** (O Lombok é uma biblioteca Java focada em produtividade e redução de código)
  - **Spring Security** (O framework de autenticação e autorização para a aplicação web)
  - **JWT** (O JSON Web Token é um padrão (RFC-7519) de mercado que define como transmitir e armazenar objetos JSON de forma compacta e segura entre diferentes aplicações)
  - **SonarLint** (É utilizada para apontar erros no código durante o desenvolvimento)
  - **GIT** (Sistema distribuído de controle de versões)
  - **Angular 10.1.7** (Front-end desenvolvido em Angular)

# Tratamento de codigos dos end-points.

- 200 - Ação executado com sucesso.
- 401 - Você não tem permissão para acessar este recurso.
- 404 - URL do serviço não encontrada.
- 409 - Exeção de validação de regra de negócio.
- 500 - Ocorreu um erro inesperado, contate o adiministrador.

# Preparação do ambiente de banco

##### Passo 01
Informe os dados de conexão do banco no arquivo **bootstrap.yml** no diretório abaixo da aplicação.

Caso não tenha uma infraestrutura de banco Oracle, utilize a imagem oficial **Oracle Database 12c Enterprise Edition** do Docker hub seguindo o Passo 02, caso já tenha a infraestrutura pule para o Passo 03.

Diretório: **mv-fin-app/src/main/resources/bootstrap.yml**.
 
##### Passo 02 
Passos para criar banco com o docker.

Para desenvolvimento foi usando a imagem do Docker, segue os passos para criar o banco via terminal.

```sh
$ docker pull store/oracle/database-enterprise:12.2.0.1
```
```sh
$ docker run -d -it --name db-mv-fin -p 1521:1521 store/oracle/database-enterprise:12.2.0.1
```
```sh
$ docker exec -it db-mv-fin bash -c "source /home/oracle/.bashrc; sqlplus /nolog"
```
```sh
$ docker pull store/oracle/database-enterprise:12.2.0.1
```
```sh
$ connect sys as sysdba;
```
```sh
$ alter session set "_ORACLE_SCRIPT"=true;
```
```sh
$ create user mvfin identified by mvfin;
```
```sh
$ GRANT ALL PRIVILEGES TO mvfin;
```


###### Dados do banco
String de conexão com o banco de dados no docker.

```sh
url: jdbc:oracle:thin:@localhost:1521/ORCLCDB.localdomain?currentSchema=mvfin
username: mvfin
password: mvfin
```

##### Passo 03 
###### Scrips DDL
Os scrips de criação de objetos de banco de dados está localizado no diretório abaixo.

Diretório:
 
**mv-fin-app/src/main/resources/script/V001_Create_Table.sql**.
**mv-fin-app/src/main/resources/script/V003_PROCEDURE_CONSULTA_QUANTIDADE_CLIENTE.sql.sql**.
**mv-fin-app/src/main/resources/script/V004_PROCEDURE_CONSULTA_QUANTIDADE_MOVIMENTACAO.sql.sql**.
**mv-fin-app/src/main/resources/script/V005_PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO.sql.sql**.
**mv-fin-app/src/main/resources/script/V006_PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_ANO.sql.sql**.
**mv-fin-app/src/main/resources/script/V007_PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_MENSAL.sql.sql**.
**mv-fin-app/src/main/resources/script/V008_PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_SEMANA.sql.sql**.

######  Scrips DML
Os scrips para carga de dados está localizado no diretório abaixo.

Diretório: **mv-fin-app/src/main/resources/script/V002_Carga.sql**.

- Carga com 1 usuário.
- Carga com 10 clientes.
- Carga de endereços dos clientes.
- Carga de contas dos clientes.
- Carga de movimento dos clientes.

 
##### Passo 04

Execute o projeto na IDE de desenvolvimento e acesse o endereço do Swagger.

http://localhost:8080/mv-fin-app/swagger-ui/index.html

![alt tag](https://github.com/kasppertech/mv-fin-app/blob/master/src/main/resources/img/swagger.png)

##### Passo 05

Acessar o projeto do front-end e executar os comandos dentro do diretório via terminal.

```sh
npm i
```

```sh
ng serve
```
Acessar o endereço.
http://localhost:4200/login

###### Tela de Login

Entrar com usuario e senha de acesso:

usuário: admin

senha: 1234

![alt tag](https://github.com/kasppertech/mv-fin-app/blob/master/src/main/resources/img/login.png)

###### Tela de Home

Tela Home, um painel geral com as informações dos clientes e movimentações.
- Total de clientes cadastrado.
- Total Movimentação.
- Total Movimento Semanal.
- Total Movimento Mensal.
- Total Movimento Anual.
- Total Movimento Último Ano.
- Extração do relatório em excel de saldo de todos os clientes.
- Extração do relatório em excel de receita da empresa por período.

![alt tag](https://github.com/kasppertech/mv-fin-app/blob/master/src/main/resources/img/home.png)


###### Tela de consulta cliente

Tela de consulta de clientes, com opção de filtro para pesquisa dos dados.
- Lista de clientes.
- Ações de detalhe do cliente.
- Ações de exclusão do cliente.
- Ações de edição do cliente.
- Ações de relatório do cliente.

![alt tag](https://github.com/kasppertech/mv-fin-app/blob/master/src/main/resources/img/consulta_usuario.png)

###### Tela de cadastro de cliente

Tela de cadastro de cliente, endereços e contas.
- Seção dos dados do cliente.
- Seção dos dados dos endereços.
- Seção dos dados das contas.
  - É obrigatorio o cadastro de uma movimentação inicial.


![alt tag](https://github.com/kasppertech/mv-fin-app/blob/master/src/main/resources/img/cadastrar.png)

###### Tela de detalhe das informações

Tela de detalhe, modal com os dados do cliente em seções.
- Seção dos dados do cliente.
- Seção dos dados dos endereços.
- Seção dos dados das contas.

![alt tag](https://github.com/kasppertech/mv-fin-app/blob/master/src/main/resources/img/detalhe.png)
 
 
###### Tela de relatório por cliente

Tela de relatório por cliente, extraçao das informações dos clientes e movimentações.
- Extração do relatório em excel de saldo do cliente.
- Extração do relatório em excel de saldo do cliente e período.

![alt tag](https://github.com/kasppertech/mv-fin-app/blob/master/src/main/resources/img/relatorios.png)
 
 




 

