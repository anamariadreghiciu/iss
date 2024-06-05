package biblioteca.biblioteca.controller;

import biblioteca.biblioteca.repository.RepositoryException;
import biblioteca.biblioteca.service.Service;
import biblioteca.biblioteca.service.ServiceException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Service service;
    private Stage stage;


    public void setService(Service service, Stage stage) {

        this.service = service;
        this.stage = stage;
    }


    public void setPrimaryStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleAdminLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            if (service.loginBibliotecar(username, password) > 0) {
                FXMLLoader bibliotecarLoader = new FXMLLoader(getClass().getResource("/views/BibliotecarView.fxml"));
                Scene bibliotecarScene = new Scene(bibliotecarLoader.load());
                BibliotecarController bibliotecarController = bibliotecarLoader.getController();
                bibliotecarController.setService(service, stage);
                stage.setScene(bibliotecarScene);
            } else {
                showError("Eroare la autentificare", "Nume de utilizator sau parolă incorectă.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            showError("Eroare la autentificare", e.getMessage());
        }
    }

    @FXML
    private void handleUserLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            Integer id = service.loginAbonat(username, password);
            if ( id > 0) {
                FXMLLoader abonatLoader = new FXMLLoader(getClass().getResource("/views/AbonatView.fxml"));
                Scene abonatScene = new Scene(abonatLoader.load());
                AbonatController abonatController = abonatLoader.getController();
                stage.setScene(abonatScene);
                abonatController.setService(service, id, stage);
            } else {
                showError("Eroare la autentificare", "Nume de utilizator sau parolă incorectă.");
            }
        } catch (Exception e) {
            showError("Eroare la autentificare", e.getMessage());
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
