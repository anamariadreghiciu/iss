package biblioteca.biblioteca;

import biblioteca.biblioteca.controller.AbonatController;
import biblioteca.biblioteca.controller.BibliotecarController;
import biblioteca.biblioteca.controller.LoginController;
import biblioteca.biblioteca.repository.AbonatRepository;
import biblioteca.biblioteca.repository.BibliotecarRepository;
import biblioteca.biblioteca.repository.CarteRepository;
import biblioteca.biblioteca.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static javafx.application.Application.launch;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
            return;
        }
        // Crearea repository-urilor
        CarteRepository carteRepository = new CarteRepository(props);
        AbonatRepository abonatRepository = new AbonatRepository(props);
        BibliotecarRepository bibliotecarRepository = new BibliotecarRepository(props);
        try {
            System.out.println(carteRepository.findAll());
        }catch (Exception e){
            e.printStackTrace();
        }

        // Crearea serviciului
        Service service = new Service(abonatRepository, carteRepository, bibliotecarRepository);

        // Încărcarea și inițializarea LoginView
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
        Scene loginScene = new Scene(loginLoader.load());
        LoginController loginController = loginLoader.getController();
        loginController.setService(service, primaryStage);

        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Autentificare");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
