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

public class Unidade{
	private IntegerProperty codigoUnidade;
	private StringProperty nomeUnidade;

	public Unidade(Integer codigoUnidade, String nomeUnidade){
		this.codigoUnidade = new SimpleIntegerProperty(codigoUnidade);
		this.nomeUnidade = new SimpleStringProperty(nomeUnidade);
	}

	public Integer getCodigoUnidade(){
		return codigoUnidade.get();
	}

	public void setCodigoUnidade(Integer codigoUnidade){
		this.codigoUnidade = new SimpleIntegerProperty(codigoUnidade);
	}

	public String getNomeUnidade(){
		return nomeUnidade.get();
	}

	public void setNomeUnidade(String nomeUnidade){
		this.nomeUnidade = new SimpleStringProperty(nomeUnidade);
	}

	public IntegerProperty codigoUnidadeProperty(){
		return codigoUnidade;
	}

	public StringProperty nombreUnidadeProperty(){
		return nomeUnidade;
	}
	
	public static void preencherInformacao(Connection connection, ObservableList<Unidade> lista){
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery(
					"SELECT codigo_unidade, nome_unidade FROM tbl_unidades"
			);
			while (resultado.next()){
				lista.add(
						new Unidade(
								resultado.getInt("codigo_unidade"),
								resultado.getString("nome_unidade")
						)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString(){
		return nomeUnidade.get();
	}
}