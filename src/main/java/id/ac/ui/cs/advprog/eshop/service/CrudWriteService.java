package id.ac.ui.cs.advprog.eshop.service;

public interface CrudWriteService<T, ID> {
    T create(T entity);

    void delete(ID id);
}
