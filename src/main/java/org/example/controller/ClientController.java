package org.example.controller;
import org.example.controller.request.ClientRQ;
import org.example.servise.ClientService;
import org.example.servise.response.ClientRS;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/clients")
    public List<ClientRS> getClients() {
        return clientService.findAll();
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<ClientRS> getClientById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @PostMapping(value = "/client", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ClientRS> createClient(@RequestBody ClientRQ clientRQ) {
        return ResponseEntity.ok(clientService.createClient(clientRQ));
    }

    @PutMapping(value = "/update-client/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ClientRS> updateClientByID(@RequestParam Long clientId, @RequestBody ClientRQ clientRQ) {
        return ResponseEntity.ok(clientService.updateClientById(clientId, clientRQ));
    }

    @DeleteMapping(path = "/delete-client/{id}")
    public ResponseEntity deleteClient(@PathVariable(value = "id") Long clientId) {
        clientService.deleteById(clientId);
        return ResponseEntity.created(URI.create("/client" + clientId)).body("Client was deleted");
    }
}
