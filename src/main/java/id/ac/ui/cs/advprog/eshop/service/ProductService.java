package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

public interface ProductService extends CrudReadService<Product, String>, CrudWriteService<Product, String> {
    Product edit(Product product);
}
