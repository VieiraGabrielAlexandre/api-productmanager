package br.com.vieiragabriel.apiprodutoscasa.controllers;

import br.com.vieiragabriel.apiprodutoscasa.dto.ProductResponse;
import br.com.vieiragabriel.apiprodutoscasa.model.entities.Product;
import br.com.vieiragabriel.apiprodutoscasa.repositories.IProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


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

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (!productOptional.isPresent()) {
            ProductResponse response = new ProductResponse("Produto não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ProductResponse response = new ProductResponse(productOptional.get());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable int id, @Valid @RequestBody Product product) {
        Optional<Product> existingProductOptional = productRepository.findById(id);

        if (!existingProductOptional.isPresent()) {
            ProductResponse response = new ProductResponse("Produto não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Product existingProduct = existingProductOptional.get();
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStatus(product.isStatus());
        existingProduct.setDate(product.getDate());

        Product updatedProduct = productRepository.save(existingProduct);
        ProductResponse response = new ProductResponse(updatedProduct);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> patchProduct(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        Optional<Product> existingProductOptional = productRepository.findById(id);

        if (!existingProductOptional.isPresent()) {
            ProductResponse response = new ProductResponse("Produto não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Product existingProduct = existingProductOptional.get();

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    existingProduct.setName((String) value);
                    break;
                case "description":
                    existingProduct.setDescription((String) value);
                    break;
                case "price":
                    existingProduct.setPrice(Double.valueOf(value.toString()));
                    break;
                case "status":
                    existingProduct.setStatus(Boolean.parseBoolean(value.toString()));
                    break;
                case "date":
                    existingProduct.setDate((java.util.Date) value);
                    break;
            }
        });

        Product updatedProduct = productRepository.save(existingProduct);
        ProductResponse response = new ProductResponse(updatedProduct);
        return ResponseEntity.ok(response);
    }
}
