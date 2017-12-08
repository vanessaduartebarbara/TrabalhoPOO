package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Aluno{
	private IntegerProperty codigoAluno;
	private StringProperty nome;
	private StringProperty sobrenome;
	private IntegerProperty idade;
	private StringProperty genero;
	private Date dataIngresso;
	private Unidade unidade;
	private Curso curso;

	public Aluno(Integer codigoAluno, String nome, String sobrenome, Integer idade, String genero, Date dataIngresso, Unidade unidade, Curso curso){
		this.codigoAluno = new SimpleIntegerProperty(codigoAluno);
		this.nome = new SimpleStringProperty(nome);
		this.sobrenome = new SimpleStringProperty(sobrenome);
		this.idade = new SimpleIntegerProperty(idade);
		this.genero = new SimpleStringProperty(genero);
		this.dataIngresso = dataIngresso;
		this.unidade = unidade;
		this.curso = curso;
	}

	public Integer getCodigoAluno(){
		return codigoAluno.get();
	}

	public void setCodigoAluno(Integer codigoAluno){
		this.codigoAluno = new SimpleIntegerProperty(codigoAluno);
	}

	public String getNome(){
		return nome.get();
	}

	public void setNome(String nome){
		this.nome = new SimpleStringProperty(nome);
	}

	public String getSobrenome(){
		return sobrenome.get();
	}

	public void setSobrenome(String sobrenome){
		this.sobrenome = new SimpleStringProperty(sobrenome);
	}

	public Integer getIdade(){
		return idade.get();
	}

	public void setIdade(Integer idade){
		this.idade = new SimpleIntegerProperty(idade);
	}

	public String getGenero(){
		return genero.get();
	}

	public void setGenero(String genero){
		this.genero = new SimpleStringProperty(genero);
	}

	public Date getDataIngresso(){
		return dataIngresso;
	}

	public void setDataIngresso(Date dataIngresso){
		this.dataIngresso = dataIngresso;
	}

	public Unidade getUnidade(){
		return unidade;
	}

	public void setUnidade(Unidade unidade){
		this.unidade = unidade;
	}

	public Curso getCurso(){
		return curso;
	}

	public void setCurso(Curso curso){
		this.curso = curso;
	}

	public IntegerProperty codigoAlunoProperty(){
		return codigoAluno;
	}

	public StringProperty nomeProperty(){
		return nome;
	}

	public StringProperty sobrenomeProperty(){
		return sobrenome;
	}

	public IntegerProperty idadeProperty(){
		return idade;
	}

	public StringProperty generoProperty(){
		return genero;
	}

	public int salvarRegistro(Connection connection){
		try {
			PreparedStatement instrucao =
					connection.prepareStatement("INSERT INTO tbl_alunos (nome, sobrenome, idade, genero, "
							+ "data_ingresso, codigo_curso, codigo_unidade) "+
								"VALUES (?, ?, ?, ?, ?, ?, ?)");
			instrucao.setString(1, nome.get());
			instrucao.setString(2, sobrenome.get());
			instrucao.setInt(3, idade.get());
			instrucao.setString(4, genero.get());
			instrucao.setDate(5, dataIngresso);
			instrucao.setInt(6, curso.getCodigoCurso());
			instrucao.setInt(7, unidade.getCodigoUnidade());
			return instrucao.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int atualizarRegistro(Connection connection){
		try {
			PreparedStatement instrucao =
					connection.prepareStatement(
								"UPDATE tbl_alunos "+
								" SET 	nome = ?,  "+
								" sobrenome = ?,  "+
								" idade = ?, "+
								" genero = ?,  "+
								" data_ingresso = ?, "+
								" codigo_curso = ?,  "+
								" codigo_unidade = ?  "+
								" WHERE codigo_aluno = ?"
					);
			instrucao.setString(1, nome.get());
			instrucao.setString(2, sobrenome.get());
			instrucao.setInt(3, idade.get());
			instrucao.setString(4, genero.get());
			instrucao.setDate(5, dataIngresso);
			instrucao.setInt(6, curso.getCodigoCurso());
			instrucao.setInt(7, unidade.getCodigoUnidade());
			instrucao.setInt(8, codigoAluno.get());
			return instrucao.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int apagarRegistro(Connection connection){
		try {
			PreparedStatement instrucao = connection.prepareStatement(
							"DELETE FROM tbl_alunos "+
							"WHERE codigo_aluno = ?"
			);
			instrucao.setInt(1, codigoAluno.get());
			return instrucao.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static void preencherInformacaoAlunos(Connection connection,
				ObservableList<Aluno> lista){
		try {
			Statement instrucao = connection.createStatement();
			ResultSet resultado = instrucao.executeQuery(
					"SELECT A.codigo_aluno, " +
						"A.nome, " +
					    "A.sobrenome, " +
					    "A.idade, " +
					    "A.genero, " +
					    "A.data_ingresso, " +
					    "A.codigo_unidade, " +
					    "A.codigo_curso, " +
					    "B.nome_curso, " +
					    "B.quantidade_matriculas, " +
					    "C.nome_unidade " +
					"FROM tbl_alunos A " +
					"INNER JOIN tbl_cursos B " +
					"ON (A.codigo_curso = B.codigo_curso) " +
					"INNER JOIN tbl_unidades C " +
					"ON (A.codigo_unidade = C.codigo_unidade)"
			);
			while(resultado.next()){
				lista.add(
						new Aluno(
								resultado.getInt("codigo_aluno"),
								resultado.getString("nome"),
								resultado.getString("sobrenome"),
								resultado.getInt("idade"),
								resultado.getString("genero"),
								resultado.getDate("data_ingresso"),
								new Unidade(resultado.getInt("codigo_unidade"),
										resultado.getString("nome_unidade")
								),
								new Curso(resultado.getInt("codigo_curso"),
										resultado.getString("nome_curso"),
										resultado.getInt("quantidade_matriculas"))
						)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}