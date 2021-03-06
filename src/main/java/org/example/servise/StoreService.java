package org.example.servise;

import org.example.controller.request.StoreRQ;
import org.example.exceptions.ResourceNotFound;
import org.example.model.Store;
import org.example.repository.StoreRepository;
import org.example.servise.response.StoreRS;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<StoreRS> findAll() {
        List<Store> storeList = storeRepository.findAll();
        List<StoreRS> storeRSList = new ArrayList<>();
        for (Store store : storeList) {
            Long numClients = Long.valueOf(store.getClients().size());
            Long numProducts = Long.valueOf(store.getProducts().size());
            Long numOrders = Long.valueOf(store.getOrders().size());
            final StoreRS storeRS = new StoreRS(
                    store.getId(),
                    store.getName(),
                    store.getProfit(),
                    numClients,
                    numProducts,
                    numOrders
            );
            storeRSList.add(storeRS);
        }
        return storeRSList;
    }

    public StoreRS findById(Long id) {
        try {
            Store store = storeRepository.getById(id);
            return new StoreRS(
                    store.getId(),
                    store.getName(),
                    store.getProfit(),
                    (long) store.getClients().size(),
                    (long) store.getProducts().size(),
                    (long) store.getOrders().size()
            );
        }catch (Exception e){
            throw new ResourceNotFound("Store Not Found");
        }
    }
    public StoreRS createStore(StoreRQ storeRQ) {
        Store store = Store
                .builder()
                .name(storeRQ.getName())
                .build();
        storeRepository.save(store);
        return new StoreRS(
                store.getId(),
                store.getName(),
                store.getProfit(),
                0L,
                0L,
                0L);
    }
    public void deleteById(Long storeId) {
        try {
          storeRepository.deleteById(storeId);
        }catch(Exception e) {
            throw new ResourceNotFound("You need to Add an Existing Store");
        }
    }
}
