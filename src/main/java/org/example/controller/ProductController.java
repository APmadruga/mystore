package org.example.controller;
import org.example.controller.request.ProductRQ;
import org.example.servise.ProductService;
import org.example.servise.response.ProductRS;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@Validated
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductRS>> getProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductRS> getProductById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PutMapping(value = "/update-product/{id}/price", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductRS> updateProductPrice(@RequestParam Long id, @RequestBody Long newPrice) {
        return ResponseEntity.ok(productService.updateProductPrice(id, newPrice));
    }

    @PostMapping(value = "/product" , consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductRS> createProduct(ProductRQ productRQ){
        return ResponseEntity.ok(productService.createProduct(productRQ));
    }

    @DeleteMapping(path = "/delete-product/{id}")
    public ResponseEntity deleteProduct(@PathVariable(value = "id") Long id) {
        productService.deleteById(id);
        return ResponseEntity.created(URI.create("/product" + id)).body("Product was deleted");
    }
}

