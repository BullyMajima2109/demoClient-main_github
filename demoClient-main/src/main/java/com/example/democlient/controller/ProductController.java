package com.example.democlient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.democlient.model.Product;
import com.example.democlient.service.ProductService;

@RestController
@RequestMapping("/api/products")

public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> listar() {
        return productService.listAll();
    }

    @PostMapping
    public Product crear(@RequestBody Product product) {
        return productService.create(product);
    }
    
    @PutMapping("/{id}")
       public ResponseEntity<Product> actualizar(@PathVariable Long id, @RequestBody Product product) {
        Product existing = productService.getProductById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        existing.setNombre(product.getNombre());
        existing.setDescripcion(product.getDescripcion());
        existing.setPrecio(product.getPrecio());
        existing.setStock(product.getStock());
        Product updated = productService.update(existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
