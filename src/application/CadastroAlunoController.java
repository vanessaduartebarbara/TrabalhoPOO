package application;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Aluno;
import modelo.Curso;
import modelo.Unidade;
import modelo.Conexao;

public class CadastroAlunoController implements Initializable{
	//Colunas
	@FXML private TableColumn<Aluno,String> clmnNome;
	@FXML private TableColumn<Aluno,String> clmnSobrenome;
	@FXML private TableColumn<Aluno,Number> clmnIdade;
	@FXML private TableColumn<Aluno,String> clmnGenero;
	@FXML private TableColumn<Aluno,Date> clmnDataIngresso;
	@FXML private TableColumn<Aluno,Unidade> clmnUnidade;
	@FXML private TableColumn<Aluno,Curso> clmnCurso;

	//Componentes GUI
	@FXML private TextField txtCodigo;
	@FXML private TextField txtNome;
	@FXML private TextField txtSobrenome;
	@FXML private TextField txtIdade;
	@FXML private RadioButton rbtFeminino;
	@FXML private RadioButton rbtMasculino;
	@FXML private DatePicker dtpkrData;
	@FXML private Button btnSalvar;
	@FXML private Button btnApagar;
	@FXML private Button btnAtualizar;

	@FXML private ComboBox<Curso> cmbCurso;
	@FXML private ComboBox<Unidade> cmbUnidade;
	@FXML private TableView<Aluno> tblViewAlunos;

	//Classes
	private ObservableList<Curso> listaCursos;
	private ObservableList<Unidade> listaUnidades;
	private ObservableList<Aluno> listaAlunos;

	private Conexao conexao;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		conexao = new Conexao();
		conexao.estabelecerConexao();

		//Inicializar listas
		listaCursos = FXCollections.observableArrayList();
		listaUnidades = FXCollections.observableArrayList();
		listaAlunos = FXCollections.observableArrayList();

		//Preencher listas
		Curso.preencherInformacao(conexao.getConnection(), listaCursos);
		Unidade.preencherInformacao(conexao.getConnection(), listaUnidades);
		Aluno.preencherInformacaoAlunos(conexao.getConnection(), listaAlunos);

		//"Linkar" listas com ComboBox e TableView
		cmbCurso.setItems(listaCursos);
		cmbUnidade.setItems(listaUnidades);
		tblViewAlunos.setItems(listaAlunos);

		//"Linkar colunas com atributos
		clmnNome.setCellValueFactory(new PropertyValueFactory<Aluno,String>("nome"));
		clmnSobrenome.setCellValueFactory(new PropertyValueFactory<Aluno,String>("sobrenome"));
		clmnIdade.setCellValueFactory(new PropertyValueFactory<Aluno,Number>("idade"));
		clmnGenero.setCellValueFactory(new PropertyValueFactory<Aluno,String>("genero"));
		clmnDataIngresso.setCellValueFactory(new PropertyValueFactory<Aluno,Date>("dataIngresso"));
		clmnUnidade.setCellValueFactory(new PropertyValueFactory<Aluno,Unidade>("unidade"));
		clmnCurso.setCellValueFactory(new PropertyValueFactory<Aluno,Curso>("curso"));
		gerenciarEventos();
		conexao.fecharConexao();
	}

	public void gerenciarEventos(){
		tblViewAlunos.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Aluno>() {
					@Override
					public void changed(ObservableValue<? extends Aluno> arg0,
							Aluno valorAnterior, Aluno valorSeleccionado) {
							if (valorSeleccionado!=null){
								txtCodigo.setText(String.valueOf(valorSeleccionado.getCodigoAluno()));
								txtNome.setText(valorSeleccionado.getNome());
								txtSobrenome.setText(valorSeleccionado.getSobrenome());
								txtIdade.setText(String.valueOf(valorSeleccionado.getIdade()));
								if (valorSeleccionado.getGenero().equals("F")){
									rbtFeminino.setSelected(true);
									rbtMasculino.setSelected(false);
								}else if (valorSeleccionado.getGenero().equals("M")){
									rbtFeminino.setSelected(false);
									rbtMasculino.setSelected(true);
								}
								dtpkrData.setValue(valorSeleccionado.getDataIngresso().toLocalDate());
								cmbCurso.setValue(valorSeleccionado.getCurso());
								cmbUnidade.setValue(valorSeleccionado.getUnidade());

								btnSalvar.setDisable(true);
								btnApagar.setDisable(false);
								btnAtualizar.setDisable(false);
							}
					}

				}
		);
	}

	@FXML
	public void salvarRegistro(){
		//Criar uma nova instancia do tipo Aluno
		Aluno a = new Aluno(0,
					txtNome.getText(),
					txtSobrenome.getText(),
					Integer.valueOf(txtIdade.getText()),
					rbtFeminino.isSelected()?"F":"M",
					Date.valueOf(dtpkrData.getValue()),
					cmbUnidade.getSelectionModel().getSelectedItem(),
					cmbCurso.getSelectionModel().getSelectedItem());
		//Chamar metodo salvarRegistro da classe Aluno
		conexao.estabelecerConexao();
		int resultado = a.salvarRegistro(conexao.getConnection());
		conexao.fecharConexao();

		if (resultado == 1){
			listaAlunos.add(a);
			Alert mensagem = new Alert(AlertType.INFORMATION);
			mensagem.setTitle("Registro adicionado");
			mensagem.setContentText("O registro foi adicionado com sucesso");
			mensagem.setHeaderText("Resultado:");
			mensagem.show();
		}
	}

	@FXML
	public void atualizarRegistro(){
		Aluno a = new Aluno(
				Integer.valueOf(txtCodigo.getText()),
				txtNome.getText(),
				txtSobrenome.getText(),
				Integer.valueOf(txtIdade.getText()),
				rbtFeminino.isSelected()?"F":"M",
				Date.valueOf(dtpkrData.getValue()),
				cmbUnidade.getSelectionModel().getSelectedItem(),
				cmbCurso.getSelectionModel().getSelectedItem());
		conexao.estabelecerConexao();
		int resultado = a.atualizarRegistro(conexao.getConnection());
		conexao.fecharConexao();

		if (resultado == 1){
			listaAlunos.set(tblViewAlunos.getSelectionModel().getSelectedIndex(),a);
			Alert mensagem = new Alert(AlertType.INFORMATION);
			mensagem.setTitle("Registro atualizado");
			mensagem.setContentText("O registro foi atualizado com sucesso");
			mensagem.setHeaderText("Resultado:");
			mensagem.show();
		}
	}

	@FXML
	public void apagarRegistro(){
		conexao.estabelecerConexao();
		int resultado = tblViewAlunos.getSelectionModel().getSelectedItem().apagarRegistro(conexao.getConnection());
		conexao.fecharConexao();

		if (resultado == 1){
			listaAlunos.remove(tblViewAlunos.getSelectionModel().getSelectedIndex());
			Alert mensagem = new Alert(AlertType.INFORMATION);
			mensagem.setTitle("Registro apagado");
			mensagem.setContentText("O registro foi apagado com sucesso");
			mensagem.setHeaderText("Resultado:");
			mensagem.show();
		}
	}

	@FXML
	public void limparComponentes(){
		txtCodigo.setText(null);
		txtNome.setText(null);
		txtSobrenome.setText(null);
		txtIdade.setText(null);
		rbtFeminino.setSelected(false);
		rbtMasculino.setSelected(false);
		dtpkrData.setValue(null);
		cmbCurso.setValue(null);
		cmbUnidade.setValue(null);

		btnSalvar.setDisable(false);
		btnApagar.setDisable(true);
		btnAtualizar.setDisable(true);
	}
}
