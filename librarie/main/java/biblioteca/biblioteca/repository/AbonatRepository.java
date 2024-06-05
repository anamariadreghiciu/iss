package biblioteca.biblioteca.repository;

import biblioteca.biblioteca.model.Abonat;
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

public class AbonatRepository implements IRepository<Abonat, Integer>{

    private final JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public AbonatRepository(Properties props) {
        logger.info("Initializing AbonatRepository with SessionFactory");
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public Abonat findById(Integer id) throws RepositoryException {
        return null;
    }

    @Override
    public List<Abonat> findAll() throws RepositoryException {
        return null;
    }

    @Override
    public void save(Abonat entity) throws RepositoryException {

    }

    @Override
    public void update(Abonat entity) throws RepositoryException {

    }

    @Override
    public void delete(Abonat entity) throws RepositoryException {

    }

    public Integer login(String nume, String parola) {
        logger.traceEntry("login: nume={}, parola={}", nume, parola);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement prepStmt =
                     con.prepareStatement("SELECT id FROM abonat WHERE nume = ? AND cnp = ?")) {
            prepStmt.setString(1, nume);
            prepStmt.setString(2, parola);
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
