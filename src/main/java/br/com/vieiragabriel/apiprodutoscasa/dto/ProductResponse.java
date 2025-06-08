package br.com.vieiragabriel.apiprodutoscasa.dto;

import br.com.vieiragabriel.apiprodutoscasa.model.entities.Product;

public class ProductResponse {
    private Product data;
    private String message;

    public ProductResponse() {
    }

    public ProductResponse(Product data) {
        this.data = data;
    }

    public ProductResponse(String message) {
        this.message = message;
    }

    public Product getData() {
        return data;
    }

    public void setData(Product data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}