package com.example.democlient.controller;

import java.util.List;
import java.util.Optional;

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

import com.example.democlient.model.Cart;
import com.example.democlient.model.CartItem;
import com.example.democlient.model.Product;
import com.example.democlient.service.CartService;
import com.example.democlient.service.ProductService;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.listAll();
        return ResponseEntity.ok(carts);
    }

    @Autowired
    private ProductService productService;

    @PostMapping("/{cartId}/items")
public ResponseEntity<?> addProductToCart(@PathVariable Long cartId, @RequestBody CartItem newItem) {
    if (newItem.getQuantity() <= 0) {
        return ResponseEntity.badRequest().body("La cantidad de productos debe ser mayor a 0");
    }
    
    Optional<Cart> optionalCart = cartService.getCartById(cartId);
    if (!optionalCart.isPresent()) {
        return ResponseEntity.notFound().build();
    }

    Cart cart = optionalCart.get();

    Product product = productService.getProductById(newItem.getProduct().getId());
    if (product == null) {
        return ResponseEntity.badRequest().body("Producto no encontrado");
    }

    cart.addProduct(product, newItem.getQuantity());

    Cart updatedCart = cartService.updateCart(cart);
    return ResponseEntity.ok(updatedCart);
}

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Cart> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        Optional<Cart> optionalCart = cartService.getCartById(cartId);
        if (!optionalCart.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Cart cart = optionalCart.get();
        cart.removeProduct(productId);
        Cart updatedCart = cartService.updateCart(cart);
        return ResponseEntity.ok(updatedCart);
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        Cart createdCart = cartService.createCart(cart);
        return ResponseEntity.ok(createdCart);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Optional<Cart> cart = cartService.getCartById(id);
        return cart.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody Cart cartDetails) {
        Optional<Cart> optionalCart = cartService.getCartById(id);
        if (!optionalCart.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Cart cart = optionalCart.get();
        cart.setItems(cartDetails.getItems());
        cart.setClient(cartDetails.getClient());
        Cart updatedCart = cartService.updateCart(cart);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

}
