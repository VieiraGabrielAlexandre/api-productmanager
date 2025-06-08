package br.com.vieiragabriel.apiprodutoscasa.controllers.scope;

import br.com.vieiragabriel.apiprodutoscasa.model.entities.Product;
import br.com.vieiragabriel.apiprodutoscasa.repositories.IProductRepository;
import br.com.vieiragabriel.apiprodutoscasa.services.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/scope/products")
public class ScopeProductController {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private OpenAIService openAIService;

    @GetMapping("/{id}")
    public ResponseEntity<? extends Object> productScope(@PathVariable int id) {
        Map<String, String> response = new HashMap<>();
        Optional<Product> productOptional = productRepository.findById(id);

        if (!productOptional.isPresent()) {
            response.put("message", "Produto não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Product product = productOptional.get();
        String productName = product.getName();
        String description = product.getDescription();

        // Generate a summary based on the product description using OpenAI
        String summary = openAIService.generateProductSummary(productName, description);
        response.put("summary", summary);

        return ResponseEntity.ok(response);
    }
}
