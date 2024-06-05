package biblioteca.biblioteca.service;

import biblioteca.biblioteca.model.Abonat;
import biblioteca.biblioteca.model.Carte;
import biblioteca.biblioteca.model.ImprumutDetails;
import biblioteca.biblioteca.repository.AbonatRepository;
import biblioteca.biblioteca.repository.BibliotecarRepository;
import biblioteca.biblioteca.repository.CarteRepository;
import biblioteca.biblioteca.repository.RepositoryException;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Service extends Observable{
    private final AbonatRepository abonatRepo;
    private final BibliotecarRepository bibliotecarRepo;
    private final CarteRepository carteRepo;
    private static final Logger logger = LogManager.getLogger();

    public Service(AbonatRepository abonatRepository, CarteRepository carteRepository, BibliotecarRepository bibliotecarRepository) {
        this.abonatRepo = abonatRepository;
        this.carteRepo = carteRepository;
        this.bibliotecarRepo = bibliotecarRepository;
    }

    public Service(AbonatRepository abonatRepo, BibliotecarRepository bibliotecarRepo, CarteRepository carteRepo) {
        this.abonatRepo = abonatRepo;
        this.bibliotecarRepo = bibliotecarRepo;
        this.carteRepo = carteRepo;
    }

    public Integer loginAbonat(String nume, String parola) throws RepositoryException {
        return abonatRepo.login(nume, parola);
    }

    public Integer loginBibliotecar(String nume, String cnp) throws RepositoryException {
        return bibliotecarRepo.login(nume, cnp);
    }

    public List<Carte> findAvailableBooks(){
        try {
            return carteRepo.findAvailableBooks();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<Carte>();
    }

    public List<Carte> searchBooksByTitleOrAuthor(String name) throws RepositoryException {
        return carteRepo.searchBooksByTitleOrAuthor(name);
    }

    public void addImprumut(int idAbonat, int idCarte) throws RepositoryException {
        carteRepo.addImprumut(idAbonat, idCarte);
        notifyObservers();
    }

    public void deleteImprumut(int idAbonat, int idCarte) throws RepositoryException {
        carteRepo.deleteImprumut(idAbonat, idCarte);
        notifyObservers();
    }

    public List<ImprumutDetails> findAllImprumutDetails(){
        try {
            return carteRepo.findAllImprumutDetails();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<ImprumutDetails>();
    }

    public List<ImprumutDetails> findImprumutDetailsByAbonatName(String abonatName) throws RepositoryException {
        return carteRepo.findImprumutDetailsByAbonatName(abonatName);
    }
}
