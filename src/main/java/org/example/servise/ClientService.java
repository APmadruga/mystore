package org.example.servise;

import org.example.controller.request.ClientRQ;
import org.example.exceptions.ResourceNotFound;
import org.example.model.Client;
import org.example.model.Store;
import org.example.repository.ClientRepository;
import org.example.repository.StoreRepository;
import org.example.servise.response.ClientRS;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final StoreRepository storeRepository;

    public ClientService(ClientRepository clientRepository, StoreRepository storeRepository) {
        this.clientRepository = clientRepository;
        this.storeRepository = storeRepository;
    }

    public List<ClientRS> findAll() {
        List<Client> clientList = clientRepository.findAll();
        List<ClientRS> clientRSList = new ArrayList<>();
        for (Client client: clientList) {
            final ClientRS clientRS = new ClientRS(
                client.getId(),
                client.getName(),
                client.getAddress(), client.getOrders_submitted().stream().map(x -> x.getId()).collect(Collectors.toList()),
                client.getStore().getId()
           );
           clientRSList.add(clientRS);
        }
        return clientRSList;
    }

    public ClientRS findById(Long id) {
        try{
            Client client = clientRepository.getById(id);
            return new ClientRS(
                    client.getId(),
                    client.getName(),
                    client.getAddress(),
                    client.getOrders_submitted().stream().map(x -> x.getId()).collect(Collectors.toList()),
                    client.getStore().getId()
            );
        }catch (Exception e){
            throw new ResourceNotFound("Client not Found!");
        }
    }

    public ClientRS createClient(ClientRQ clientRQ) {
        if(!storeRepository.existsById(clientRQ.getStoreId())){
            throw new ResourceNotFound("You need to Add an Existing Store");
        }
        Store store = storeRepository.getById(clientRQ.getStoreId());
        String address = clientRQ.getAddress();
        String name = clientRQ.getName();
        Client client = Client
                .builder()
                .address(address)
                .name(name)
                .store(store)
                .build();
        clientRepository.save(client);
        return new ClientRS(
                client.getId(),
                client.getName(),
                client.getAddress(),
                client.getOrders_submitted().stream().map(x -> x.getId()).collect(Collectors.toList()),
                client.getStore().getId()
        );
    }

    public ClientRS updateClientById(Long clientId, ClientRQ clientRQ) {
        if(!storeRepository.existsById(clientRQ.getStoreId())){
            throw new ResourceNotFound("You need to Add an Existing Store");
        }
        if(!clientRepository.existsById(clientId)){
            throw new ResourceNotFound("You need to Add an Existing Client");
        }
        Store store = storeRepository.getById(clientRQ.getStoreId());
        Client client = clientRepository.getById(clientId);
        String address = clientRQ.getAddress();
        String name = clientRQ.getName();
        client.setAddress(address);
        client.setName(name);
        client.setStore(store);
        clientRepository.save(client);
        return new ClientRS(
                client.getId(),
                client.getName(),
                client.getAddress(),
                client.getOrders_submitted().stream().map(x -> x.getId()).collect(Collectors.toList()),
                client.getStore().getId()
        );
    }

    public void deleteById(Long clientId) {
        try{
            clientRepository.deleteById(clientId);
        }catch (Exception e){
            throw new ResourceNotFound("You need to Add an Existing Client");
        }
    }
}
