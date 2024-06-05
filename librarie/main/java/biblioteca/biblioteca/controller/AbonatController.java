package biblioteca.biblioteca.controller;

import biblioteca.biblioteca.model.Carte;
import biblioteca.biblioteca.service.Observer;
import biblioteca.biblioteca.service.Service;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AbonatController implements Observer {

    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<Carte> cartiTableView;
    @FXML
    private TableColumn<Carte, Integer> idColumn;
    @FXML
    private TableColumn<Carte, String> titluColumn;
    @FXML
    private TableColumn<Carte, String> autorColumn;
    @FXML
    private TableView<Carte> cosTableView;
    @FXML
    private TableColumn<Carte, Integer> cosIdColumn;
    @FXML
    private TableColumn<Carte, String> cosTitluColumn;
    @FXML
    private TableColumn<Carte, String> cosAutorColumn;
    @FXML
    private Button adaugaInCosButton;
    @FXML
    private Button inchiriazaButton;
    @FXML
    private Button eliminaButton;
    @FXML
    private Button logoutButton;

    private Service service;

    private Stage stage;

    private Integer id;

    private ObservableList<Carte> cartiDisponibile = FXCollections.observableArrayList();
    private ObservableList<Carte> cosCarti = FXCollections.observableArrayList();
    private Stage primaryStage;


    public void setService(Service service, Integer id, Stage stage) {
        this.service = service;
        this.id = id;
        this.stage = stage;
        populateCartiDisponibile();
        service.addObserver(this);

        cartiDisponibile.setAll(service.findAvailableBooks());
        cosTableView.setItems(cosCarti);
        cartiTableView.setItems(cartiDisponibile);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        titluColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitlu()));
        autorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor()));

        cosIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        cosTitluColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitlu()));
        cosAutorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor()));

    }

    private void populateCartiDisponibile() {
        try {
            List<Carte> carti = service.findAvailableBooks();
            cartiDisponibile.setAll(carti);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        try {
            String searchText = searchTextField.getText();
            List<Carte> carti = service.searchBooksByTitleOrAuthor(searchText);
            cartiDisponibile.setAll(carti);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdaugaInCos() {
        List<Carte> selectedCarti = cartiTableView.getSelectionModel().getSelectedItems();
        cosCarti.addAll(selectedCarti);
    }

    @FXML
    private void handleInchiriaza(){
        try {
            for (Carte carte : cosCarti) {
                service.addImprumut(id, carte.getId());
            }
            cosCarti.clear();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleElimina() {
        List<Carte> selectedCarti = cosTableView.getSelectionModel().getSelectedItems();
        cosCarti.removeAll(selectedCarti);
    }

    @FXML
    private void handleLogout() {
        stage.close();
        service.removeObserver(this);
    }

    @Override
    public void update() {
        cartiDisponibile.setAll(service.findAvailableBooks());
        cosTableView.setItems(cosCarti);
        cartiTableView.setItems(cartiDisponibile);
    }
}
