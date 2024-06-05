package biblioteca.biblioteca.repository;

import java.util.List;

public interface IRepository<T, K> {
    T findById(K id) throws RepositoryException;
    List<T> findAll() throws RepositoryException;
    void save(T entity) throws RepositoryException;
    void update(T entity) throws RepositoryException;
    void delete(T entity) throws RepositoryException;
}
