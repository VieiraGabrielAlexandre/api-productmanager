package br.com.vieiragabriel.apiprodutoscasa.controllers;

import br.com.vieiragabriel.apiprodutoscasa.model.entities.Product;
import br.com.vieiragabriel.apiprodutoscasa.repositories.IProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private IProductRepository productRepository;

    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable int id) {
        productRepository.deleteById(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Produto deletado com sucesso");
        return ResponseEntity.ok(response);
    }
}
