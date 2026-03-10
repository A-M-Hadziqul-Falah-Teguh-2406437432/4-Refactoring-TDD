package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

public interface CrudReadService<T, ID> {
    List<T> findAll();

    T findById(ID id);
}
