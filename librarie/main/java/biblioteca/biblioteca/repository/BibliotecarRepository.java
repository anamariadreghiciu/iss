package biblioteca.biblioteca.repository;

import biblioteca.biblioteca.model.Bibliotecar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


public class BibliotecarRepository implements IRepository<Bibliotecar, Integer> {

    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public BibliotecarRepository(Properties props) {
        logger.info("Initializing BibliotecarRepository with SessionFactory");
        this.dbUtils = new JdbcUtils(props);
    }


    @Override
    public Bibliotecar findById(Integer id) throws RepositoryException {
        return null;
    }

    @Override
    public List<Bibliotecar> findAll() throws RepositoryException {
        return null;
    }

    @Override
    public void save(Bibliotecar entity) throws RepositoryException {

    }

    @Override
    public void update(Bibliotecar entity) throws RepositoryException {

    }

    @Override
    public void delete(Bibliotecar entity) throws RepositoryException {

    }

    public Integer login(String nume, String cnp) {
        logger.traceEntry("login: nume={}, cnp={}", nume, cnp);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement prepStmt =
                     con.prepareStatement("SELECT id FROM bibliotecar WHERE nume = ? AND parola = ?")) {
            prepStmt.setString(1, nume);
            prepStmt.setString(2, cnp);
            ResultSet resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                logger.traceExit("Login successful, librarian id={}", id);
                return id;
            } else {
                logger.traceExit("Login failed for nume={}", nume);
                return null;
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
            return null;
        }
    }
}
