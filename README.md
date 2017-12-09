# TrabalhoPOO
  O Projeto é um Cadastro de alunos, onde é possível cadastrar um novo aluno, salvar, alterar e apagar dados como: nome, sobrenome, 
idade, sexo, data de ingresso, curso e unidade. Nesse projeto utilizei Eclipse, JavaFX, Scene Builder e MVC.
  Para utilizar a aplicação você deve realizar os passos a seguir:
1. A pasta "Projeto_Cadastro" é o que você deve importar para o Eclipse;
2. A pasta "Video Sistema" contém um vídeo explicativo sobre o código e como utilizar a aplicação desenvolvida;
3. A pasta "Executável" possui o arquivo executável da aplicação desenvolvida;
4. A pasta "Banco_Dados" contém o arquivo da modelagem dos dados "modelo_alunos.mwb" onde está o "db_alunos" que é o banco de dados
MySQL que criei para utilizar na aplicação.
Obs.: É necessário que você tenha o MySQL instalado na sua máquina, caso ainda não tenha, esse é o link para a instalação: 
https://dev.mysql.com/downloads/mysql/, a opção que instalei foi a MySQL Community Server 5.7.20 - Windows (X86, 32 & 64-bit), 
MySQL Installer MSI, porém isso fica a seu critério.
4. Como fiz tudo "localhost" o código da classe "Conexão" ficou dessa forma: 

public class Conexao {

	private Connection connection;
	private String url = "jdbc:mysql://localhost/db_alunos";
	private String usuario = "root";
	private String senha = "12345";
  
 ... }
  
Porém você deverá substituir para o usuário e senha do MySQL da sua máquina para utilizar a aplicação de forma satisfatória.



