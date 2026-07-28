package com.template.controller;

import com.template.model.dao.AnimalDAO;
import com.template.model.dto.AnimalDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.template.util.DialogUtil;

public class AnimalController {

    @FXML private TextField txtNome;
    @FXML private TextField txtEspecie;
    @FXML private TextField txtRaca;

    @FXML private Spinner<Integer> txtIdade;
    @FXML private Spinner<Double> txtPeso;
    @FXML private ComboBox<String> cbSexo;

    @FXML private Button btnExcluir;
    @FXML private Label lblMensagem;

    @FXML private TableView<AnimalDTO> tabelaAnimais;
    @FXML private TableColumn<AnimalDTO, Integer> colId;
    @FXML private TableColumn<AnimalDTO, String> colNome;
    @FXML private TableColumn<AnimalDTO, String> colEspecie;
    @FXML private TableColumn<AnimalDTO, String> colRaca;
    @FXML private TableColumn<AnimalDTO, Integer> colIdade;
    @FXML private TableColumn<AnimalDTO, Double> colPeso;
    @FXML private TableColumn<AnimalDTO, String> colSexo;

    private AnimalDAO animalDAO = new AnimalDAO();
    private ObservableList<AnimalDTO> obsAnimais;
    private AnimalDTO animalSelecionado;

    @FXML
    public void initialize() {
        cbSexo.setItems(FXCollections.observableArrayList("M", "F"));

        txtIdade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        txtPeso.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 500.0, 0.0, 0.5));

        btnExcluir.setDisable(true);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        colRaca.setCellValueFactory(new PropertyValueFactory<>("raca"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));

        carregarTabela();

        tabelaAnimais.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTabela(newValue)
        );
    }

    @FXML
    public void salvar() {
        if (txtNome.getText().isEmpty() || cbSexo.getValue() == null) {
            // UX: Mensagem amigável de erro na tela
            mostrarMensagem("Preencha os campos obrigatórios (Nome e Sexo)!", "red");
            return;
        }

        AnimalDTO animal = new AnimalDTO();
        animal.setNome(txtNome.getText());
        animal.setEspecie(txtEspecie.getText());
        animal.setRaca(txtRaca.getText());

        // UX: Pegando valores direto do Spinner
        animal.setIdade(txtIdade.getValue());
        animal.setPeso(txtPeso.getValue());
        animal.setSexo(cbSexo.getValue().charAt(0));

        if (animalSelecionado != null) {
            animal.setId(animalSelecionado.getId());
            animalDAO.updateAnimal(animal);
            mostrarMensagem("Animal atualizado com sucesso!", "green");
        } else {
            animalDAO.cadastrarAnimal(animal);
            mostrarMensagem("Animal cadastrado com sucesso!", "green");
        }

        limpar();
        carregarTabela();
    }

    @FXML
    public void excluir() {
        if (animalSelecionado != null) {
            // Chama o diálogo customizado, passando 'true' para deixar o botão de confirmação vermelho
            boolean confirmou = DialogUtil.mostrarConfirmacao(
                    "Confirmar Exclusão",
                    "Tem certeza que deseja excluir o animal '" + animalSelecionado.getNome() + "' permanentemente?",
                    true
            );

            // Se o usuário clicou em Confirmar, prossegue com a exclusão
            if (confirmou) {
                animalDAO.deletarAnimal(animalSelecionado);
                mostrarMensagem("Animal excluído com sucesso!", "green");
                limpar();
                carregarTabela();
            }
        }
    }

    @FXML
    public void limpar() {
        animalSelecionado = null;
        txtNome.clear();
        txtEspecie.clear();
        txtRaca.clear();

        txtIdade.getValueFactory().setValue(0);
        txtPeso.getValueFactory().setValue(0.0);
        cbSexo.setValue(null);

        btnExcluir.setDisable(true);
    }

    private void carregarTabela() {
        obsAnimais = FXCollections.observableArrayList(animalDAO.selecionarAnimal());
        tabelaAnimais.setItems(obsAnimais);
    }

    private void selecionarItemTabela(AnimalDTO animal) {
        if (animal != null) {
            animalSelecionado = animal;
            txtNome.setText(animal.getNome());
            txtEspecie.setText(animal.getEspecie());
            txtRaca.setText(animal.getRaca());

            txtIdade.getValueFactory().setValue(animal.getIdade());
            txtPeso.getValueFactory().setValue(animal.getPeso());
            cbSexo.setValue(String.valueOf(animal.getSexo()));

            btnExcluir.setDisable(false);
            lblMensagem.setText("");
        }
    }

    // Método auxiliar para atualizar a Label de mensagem
    private void mostrarMensagem(String msg, String cor) {
        lblMensagem.setText(msg);
        lblMensagem.setStyle("-fx-text-fill: " + cor + "; -fx-font-weight: bold;");
    }
}