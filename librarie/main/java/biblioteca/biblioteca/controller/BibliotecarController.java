package biblioteca.biblioteca.controller;

import biblioteca.biblioteca.model.ImprumutDetails;
import biblioteca.biblioteca.service.Observer;
import biblioteca.biblioteca.service.Service;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class BibliotecarController implements Observer {

    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<ImprumutDetails> cartiTableView;
    @FXML
    private TableColumn<ImprumutDetails, Integer> idColumn;
    @FXML
    private TableColumn<ImprumutDetails, String> titluColumn;
    @FXML
    private TableColumn<ImprumutDetails, String> autorColumn;
    @FXML
    private TableColumn<ImprumutDetails, String> numeColumn;
    @FXML
    private Button restituieButton;
    @FXML
    private Button logoutButton;

    private Service service;

    private Stage stage;

    private ObservableList<ImprumutDetails> carti = FXCollections.observableArrayList();

    public void setService(Service service, Stage stage) {
            this.service = service;
            this.stage = stage;
            service.addObserver(this);
            carti.setAll(service.findAllImprumutDetails());
            cartiTableView.setItems(carti);
    }

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdAbonat()).asObject());
        titluColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitlu()));
        autorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor()));
        numeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNume()));
    }




    @FXML
    private void handleSearch() {
        try {
            String searchText = searchTextField.getText();
            carti.setAll(service.findImprumutDetailsByAbonatName(searchText));
        } catch (Exception e) {
            showError("Eroare", "Nu s-a putut efectua căutarea: " + e.getMessage());
        }
    }

    @FXML
    private void handleRestituie() {
        ObservableList<ImprumutDetails> selectedImprumutList = cartiTableView.getSelectionModel().getSelectedItems();
        if (!selectedImprumutList.isEmpty()) {
            try {
                for (ImprumutDetails selectedImprumut : selectedImprumutList) {
                    service.deleteImprumut(selectedImprumut.getIdAbonat(), selectedImprumut.getIdCarte());
                    carti.remove(selectedImprumut);
                }
                showInfo("Restituire reușită", "Împrumuturile au fost restituite cu succes.");
            } catch (Exception e) {
                showError("Eroare", "Nu s-au putut restitui împrumuturile: " + e.getMessage());
            }
        } else {
            showError("Selectare necesară", "Selectați cel puțin un împrumut pentru a fi restituit.");
        }
    }


    @FXML
    private void handleLogout() {
        //System.out.println(stage);
        stage.close();
        service.removeObserver(this);
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void update() {
        carti.setAll(service.findAllImprumutDetails());
        cartiTableView.setItems(carti);
    }
}
