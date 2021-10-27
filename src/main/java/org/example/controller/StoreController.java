package org.example.controller;
import org.example.controller.request.StoreRQ;
import org.example.servise.StoreService;
import org.example.servise.response.StoreRS;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
public class StoreController {

    StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }


    @GetMapping("/stores")
    public List<StoreRS> getStores() {
        return storeService.findAll();
    }

    @GetMapping("/store/{id}")
    public ResponseEntity<StoreRS> getStoreById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(storeService.findById(id));
    }

    @PostMapping(value = "/store", consumes = "application/json", produces = "application/json")
    public ResponseEntity<StoreRS> createStore(@RequestBody StoreRQ storeRQ) {
        return ResponseEntity.ok(storeService.createStore(storeRQ));
    }

    @DeleteMapping(path = "/delete-store/{id}")
    public ResponseEntity deleteStore(@PathVariable(value = "id") Long storeId) {
        storeService.deleteById(storeId);
        return ResponseEntity.created(URI.create("/store" + storeId)).body("Product was deleted");
    }
}
