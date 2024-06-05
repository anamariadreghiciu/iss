package biblioteca.biblioteca.repository;

import biblioteca.biblioteca.model.Carte;
import biblioteca.biblioteca.model.Imprumut;
import biblioteca.biblioteca.model.ImprumutDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
public class CarteRepository implements IRepository<Carte, Integer> {

    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger(CarteRepository.class);

    public CarteRepository(Properties props) {
        logger.info("Initializing CarteRepository");
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public Carte findById(Integer id) throws RepositoryException {
        logger.traceEntry("findById: id={}", id);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM Carte WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Carte carte = new Carte();
                    carte.setId(rs.getInt("id"));
                    carte.setTitlu(rs.getString("titlu"));
                    carte.setAutor(rs.getString("autor"));
                    // Set other fields as needed
                    logger.traceExit(carte);
                    return carte;
                } else {
                    throw new RepositoryException("Carte not found");
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding carte by id", e);
            throw new RepositoryException("Error finding carte by id", e);
        }
    }

    @Override
    public List<Carte> findAll() throws RepositoryException {
        logger.traceEntry("findAll");
        Connection con = dbUtils.getConnection();
        List<Carte> carti = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM Carte")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Carte carte = new Carte();
                    carte.setId(rs.getInt("id"));
                    carte.setTitlu(rs.getString("titlu"));
                    carte.setAutor(rs.getString("autor"));
                    // Set other fields as needed
                    carti.add(carte);
                }
                logger.traceExit(carti);
                return carti;
            }
        } catch (SQLException e) {
            logger.error("Error finding all carti", e);
            throw new RepositoryException("Error finding all carti", e);
        }
    }

    @Override
    public void save(Carte entity) throws RepositoryException {
        logger.traceEntry("save: entity={}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement stmt = con.prepareStatement("INSERT INTO Carte (titlu, autor, editura) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getTitlu());
            stmt.setString(2, entity.getAutor());
            // Set other fields as needed
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Saving carte failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Saving carte failed, no ID obtained.");
                }
            }
            logger.traceExit("Saved entity: {}", entity);
        } catch (SQLException e) {
            logger.error("Error saving entity", e);
            throw new RepositoryException("Error saving entity", e);
        }
    }

    @Override
    public void update(Carte entity) throws RepositoryException {
        logger.traceEntry("update: entity={}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement stmt = con.prepareStatement("UPDATE Carte SET titlu = ?, autor = ? WHERE id = ?")) {
            stmt.setString(1, entity.getTitlu());
            stmt.setString(2, entity.getAutor());;
            stmt.setInt(3, entity.getId());
            // Set other fields as needed
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating carte failed, no rows affected.");
            }
            logger.traceExit("Updated entity: {}", entity);
        } catch (SQLException e) {
            logger.error("Error updating entity", e);
            throw new RepositoryException("Error updating entity", e);
        }
    }

    @Override
    public void delete(Carte entity) throws RepositoryException {
        logger.traceEntry("delete: entity={}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement stmt = con.prepareStatement("DELETE FROM Carte WHERE id = ?")) {
            stmt.setInt(1, entity.getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting carte failed, no rows affected.");
            }
            logger.traceExit("Deleted entity: {}", entity);
        } catch (SQLException e) {
            logger.error("Error deleting entity", e);
            throw new RepositoryException("Error deleting entity", e);
        }
    }

    public List<Carte> findAvailableBooks() throws RepositoryException {
        logger.traceEntry("findAvailableBooks");
        Connection con = dbUtils.getConnection();
        List<Carte> availableBooks = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM Carte WHERE id NOT IN (SELECT id_carte FROM Imprumut)")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Carte carte = new Carte();
                    carte.setId(rs.getInt("id"));
                    carte.setTitlu(rs.getString("titlu"));
                    carte.setAutor(rs.getString("autor"));
                    // Set other fields as needed
                    availableBooks.add(carte);
                }
                logger.traceExit(availableBooks);
                return availableBooks;
            }
        } catch (SQLException e) {
            logger.error("Error finding available books", e);
            throw new RepositoryException("Error finding available books", e);
        }
    }

    public List<Carte> searchBooksByTitleOrAuthor(String name) throws RepositoryException {
        logger.traceEntry("searchBooksByTitleOrAuthor: name={}", name);
        Connection con = dbUtils.getConnection();
        List<Carte> matchingBooks = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM Carte WHERE titlu LIKE ? OR autor LIKE ?")) {
            String searchPattern = "%" + name + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Carte carte = new Carte();
                    carte.setId(rs.getInt("id"));
                    carte.setTitlu(rs.getString("titlu"));
                    carte.setAutor(rs.getString("autor"));
                    // Set other fields as needed
                    matchingBooks.add(carte);
                }
                logger.traceExit(matchingBooks);
                return matchingBooks;
            }
        } catch (SQLException e) {
            logger.error("Error searching books by title or author", e);
            throw new RepositoryException("Error searching books by title or author", e);
        }
    }

    public void addImprumut(int idAbonat, int idCarte) throws RepositoryException {
        logger.traceEntry("addImprumut: idAbonat={}, idCarte={}", idAbonat, idCarte);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(
                "INSERT INTO Imprumut (id_abonat, id_carte) VALUES (?, ?)")) {
            stmt.setInt(1, idAbonat);
            stmt.setInt(2, idCarte);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Adding imprumut failed, no rows affected.");
            }
            logger.trace("Imprumut added: idAbonat={}, idCarte={}", idAbonat, idCarte);
        } catch (SQLException e) {
            logger.error("Error adding imprumut", e);
            throw new RepositoryException("Error adding imprumut", e);
        }
    }

    public void deleteImprumut(int idAbonat, int idCarte) throws RepositoryException {
        logger.traceEntry("deleteImprumut: idAbonat={}, idCarte={}", idAbonat, idCarte);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(
                "DELETE FROM Imprumut WHERE id_abonat = ? AND id_carte = ?")) {
            stmt.setInt(1, idAbonat);
            stmt.setInt(2, idCarte);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting imprumut failed, no rows affected.");
            }
            logger.trace("Deleted imprumut: idAbonat={}, idCarte={}", idAbonat, idCarte);
        } catch (SQLException e) {
            logger.error("Error deleting imprumut", e);
            throw new RepositoryException("Error deleting imprumut", e);
        }
    }

    public List<ImprumutDetails> findAllImprumutDetails() throws RepositoryException {
        logger.traceEntry("findAllImprumutDetails");
        Connection con = dbUtils.getConnection();
        List<ImprumutDetails> imprumutDetailsList = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT c.id AS idCarte, c.titlu, c.autor, a.id AS idAbonat, a.nume " +
                        "FROM Imprumut i " +
                        "JOIN Carte c ON i.id_carte = c.id " +
                        "JOIN Abonat a ON i.id_abonat = a.id")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ImprumutDetails imprumutDetails = new ImprumutDetails(
                            rs.getInt("idCarte"),
                            rs.getString("titlu"),
                            rs.getString("autor"),
                            rs.getInt("idAbonat"),
                            rs.getString("nume")
                    );
                    imprumutDetailsList.add(imprumutDetails);
                }
                logger.traceExit(imprumutDetailsList);
                return imprumutDetailsList;
            }
        } catch (SQLException e) {
            logger.error("Error finding all imprumut details", e);
            throw new RepositoryException("Error finding all imprumut details", e);
        }
    }

    public List<ImprumutDetails> findImprumutDetailsByAbonatName(String abonatName) throws RepositoryException {
        logger.traceEntry("findImprumutDetailsByAbonatName: abonatName={}", abonatName);
        Connection con = dbUtils.getConnection();
        List<ImprumutDetails> imprumutDetailsList = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT c.id AS idCarte, c.titlu, c.autor, a.id AS idAbonat, a.nume " +
                        "FROM Imprumut i " +
                        "JOIN Carte c ON i.id_carte = c.id " +
                        "JOIN Abonat a ON i.id_abonat = a.id " +
                        "WHERE a.nume LIKE ?")) {
            stmt.setString(1, "%" + abonatName + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ImprumutDetails imprumutDetails = new ImprumutDetails(
                            rs.getInt("idCarte"),
                            rs.getString("titlu"),
                            rs.getString("autor"),
                            rs.getInt("idAbonat"),
                            rs.getString("nume")
                    );
                    imprumutDetailsList.add(imprumutDetails);
                }
                logger.traceExit(imprumutDetailsList);
                return imprumutDetailsList;
            }
        } catch (SQLException e) {
            logger.error("Error finding imprumut details by abonat name", e);
            throw new RepositoryException("Error finding imprumut details by abonat name", e);
        }
    }

}
