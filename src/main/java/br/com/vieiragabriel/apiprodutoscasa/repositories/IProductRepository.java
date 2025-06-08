package br.com.vieiragabriel.apiprodutoscasa.repositories;

import br.com.vieiragabriel.apiprodutoscasa.model.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface IProductRepository extends CrudRepository<Product, Integer> {

}
