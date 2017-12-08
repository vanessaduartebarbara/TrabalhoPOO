package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	private Connection connection;
	private String url = "jdbc:mysql://localhost/db_alunos";
	private String usuario = "root";
	private String senha = "12345";
	
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public void estabelecerConexao(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, usuario, senha);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void fecharConexao(){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
