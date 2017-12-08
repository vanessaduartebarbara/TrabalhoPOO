package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Curso{
	private IntegerProperty codigoCurso;
	private StringProperty nomeCurso;
	private IntegerProperty quantidadeMatriculas;

	public Curso(Integer codigoCurso, String nomeCurso, Integer quantidadeMatriculas){
		this.codigoCurso = new SimpleIntegerProperty(codigoCurso);
		this.nomeCurso = new SimpleStringProperty(nomeCurso);
		this.quantidadeMatriculas = new SimpleIntegerProperty(quantidadeMatriculas);
	}

	public Integer getCodigoCurso(){
		return codigoCurso.get();
	}

	public void setCodigoCurso(Integer codigoCurso){
		this.codigoCurso = new SimpleIntegerProperty(codigoCurso);
	}

	public String getNomeCurso(){
		return nomeCurso.get();
	}

	public void setNomeCurso(String nomeCurso){
		this.nomeCurso = new SimpleStringProperty(nomeCurso);
	}

	public Integer getQuantidadeMatriculas(){
		return quantidadeMatriculas.get();
	}

	public void setQuantidadeMatriculas(Integer quantidadeMatriculas){
		this.quantidadeMatriculas = new SimpleIntegerProperty(quantidadeMatriculas);
	}

	public IntegerProperty codigoCursoProperty(){
		return codigoCurso;
	}

	public StringProperty nomeCursoProperty(){
		return nomeCurso;
	}

	public IntegerProperty quantidadeMatriculasProperty(){
		return quantidadeMatriculas;
	}

	public static void preencherInformacao(Connection connection, ObservableList<Curso> lista){
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery(
					"SELECT codigo_curso, "
					+ "nome_curso, "
					+ "quantidade_matriculas "
					+ "FROM tbl_cursos"
			);
			while (resultado.next()){
				lista.add(
						new Curso(
								resultado.getInt("codigo_curso"),
								resultado.getString("nome_curso"),
								resultado.getInt("quantidade_matriculas")
						)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString(){
		return nomeCurso.get() + " ("+quantidadeMatriculas.get()+")";
	}
}