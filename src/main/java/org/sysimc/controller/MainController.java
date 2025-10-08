package org.sysimc.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.sysimc.model.Pessoa;

import java.text.DecimalFormat;

public class MainController {

    // --- Componentes de Entrada (TextFields) ---
    @FXML
    public TextField txtNome;

    // ** CAMPOS FALTANTES QUE CAUSAVAM ERRO **
    @FXML
    public TextField txtAltura;

    @FXML
    public TextField txtPeso;

    // --- Componentes de Saída (Labels) ---
    // ** CAMPO FALTANTE QUE CAUSAVA ERRO **
    @FXML
    public Label lblIMC;

    @FXML
    public Label lblClassificacao;

    // --- Componentes da Tabela ---
    @FXML
    public TableView<Pessoa> tableViewPessoas;

    @FXML
    public TableColumn<Pessoa, String> columnNome;

    // ** DECLARAÇÕES PARA AS OUTRAS COLUNAS DA TABELA **
    @FXML
    public TableColumn<Pessoa, Float> columnAltura;

    @FXML
    public TableColumn<Pessoa, Float> columnPeso;

    @FXML
    public TableColumn<Pessoa, Float> columnIMC;


    // --- Lógica de Dados ---
    // Note: 'pessoa' aqui não é estritamente necessário, mas o mantive.
    Pessoa pessoa = new Pessoa();

    private ObservableList<Pessoa> listaPessoas = FXCollections.observableArrayList();

    // --- Métodos de Inicialização e Lógica ---

    @FXML
    public void initialize() {
        // Inicializa todas as colunas da tabela, mapeando-as para os getters da classe Pessoa
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnAltura.setCellValueFactory(new PropertyValueFactory<>("altura"));
        columnPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        // Mapeia para o atributo 'imc'
        columnIMC.setCellValueFactory(new PropertyValueFactory<>("imc"));

        // Define a lista de dados para a tabela
        tableViewPessoas.setItems(listaPessoas);
    }

    // O método onCalcularIMCClick foi mantido do seu código original
    @FXML
    protected void onCalcularIMCClick() {
        DecimalFormat df = new DecimalFormat();
        // Não é ideal usar 'this.pessoa' aqui se você usa uma nova pessoa em onSalvarClick
        // mas mantive a lógica original
        this.pessoa.setNome(txtNome.getText() );

        try {
            this.pessoa.setAltura(Float.parseFloat(txtAltura.getText()) );
            this.pessoa.setPeso(Float.parseFloat(txtPeso.getText() ));

            df.applyPattern("#0.00");
            this.lblIMC.setText(df.format( this.pessoa.calcularIMC()) );
            this.lblClassificacao.setText( this.pessoa.classificacaoIMC() );
        } catch (NumberFormatException e) {
            this.lblIMC.setText("ERRO");
            this.lblClassificacao.setText("Altura/Peso inválidos.");
        }
    }

    @FXML
    protected void onSalvarClick() {
        // 10. Cria um NOVO objeto Pessoa com os dados atuais
        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setNome(txtNome.getText());

        try {
            float altura = Float.parseFloat(txtAltura.getText());
            float peso = Float.parseFloat(txtPeso.getText());

            novaPessoa.setAltura(altura);
            novaPessoa.setPeso(peso);

            // Calcula o IMC e salva no objeto
            novaPessoa.calcularIMC();

            // 11. Salva a nova pessoa na lista (e automaticamente na tabela)
            this.listaPessoas.add(novaPessoa);

            // 12. Opcional: Limpar os campos após salvar
            txtNome.clear();
            txtAltura.clear();
            txtPeso.clear();
            lblIMC.setText("00.00");
            lblClassificacao.setText("");

            System.out.println("Pessoa salva na lista: " + novaPessoa.getNome());

        } catch (NumberFormatException e) {
            // Trate o erro se o usuário não digitar um número
            System.err.println("Erro: Altura e Peso devem ser números válidos.");
            lblIMC.setText("ERRO");
            lblClassificacao.setText("Ajuste Altura/Peso para salvar.");
        }
    }

    @FXML
    protected void onCarregarClick() {
        // Simula dados carregados de algum lugar (ex: banco, arquivo, API, etc.)
        String nomeExemplo = "Danilo";
        float alturaExemplo = 179;
        float pesoExemplo = 69;

        // Preenche os campos da tela
        txtNome.setText(nomeExemplo);
        txtAltura.setText(String.valueOf(alturaExemplo));
        txtPeso.setText(String.valueOf(pesoExemplo));

        // Cria uma pessoa temporária para calcular e classificar
        Pessoa pessoaExemplo = new Pessoa();
        pessoaExemplo.setAltura(alturaExemplo);
        pessoaExemplo.setPeso(pesoExemplo);

        float imc = pessoaExemplo.calcularIMC();

        // Atualiza os cálculos na tela
        lblIMC.setText(String.format("%.2f", imc));
        lblClassificacao.setText(pessoaExemplo.classificacaoIMC());

        System.out.println("Dados carregados: " + nomeExemplo);
    }
}