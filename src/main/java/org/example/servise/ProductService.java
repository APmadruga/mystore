package org.example.servise;

import org.example.controller.request.ProductRQ;
import org.example.exceptions.ResourceNotFound;
import org.example.model.Product;
import org.example.model.Store;
import org.example.repository.ProductRepository;
import org.example.repository.StoreRepository;
import org.example.servise.response.ProductRS;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    public ProductService(ProductRepository productRepository, StoreRepository storeRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    public List<ProductRS> getProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductRS> productRSList = new ArrayList<>();
        for (Product product : productList) {
            final ProductRS productRS = new ProductRS(
                    product.getId(),
                    product.getPrice(),
                    product.getName(),
                    product.getProductType(),
                    product.getOrders().stream().map(x -> (Long) x.getId()).collect(Collectors.toList()),
                    product.getStore().getId()
            );
            productRSList.add(productRS);
        }
        return productRSList;
    }

    public ProductRS findById(Long id) {
        try {
            Product product = productRepository.findById(id).get();
            return  new ProductRS(
                    product.getId(),
                    product.getPrice(),
                    product.getName(),
                    product.getProductType(),
                    product.getOrders().stream().map(x -> (Long) x.getId()).collect(Collectors.toList()),
                    product.getStore().getId()
            );
        }catch (Exception e){
            throw new ResourceNotFound("Product Not Found");
        }
    }

    public ProductRS createProduct(ProductRQ productRQ) {
        if(!storeRepository.existsById(productRQ.getStoreId()))
            throw new ResourceNotFound("Need to an Existing Store");
        Store store = storeRepository.getById(productRQ.getStoreId());
        Product product = Product
                .builder()
                .price(productRQ.getPrice())
                .name(productRQ.getName())
                .productType(productRQ.getProductType())
                .store(store)
                .build();
        productRepository.save(product);
        return  new ProductRS(
                product.getId(),
                product.getPrice(),
                product.getName(),
                product.getProductType(),
                product.getOrders().stream().map(x -> (Long) x.getId()).collect(Collectors.toList()),
                product.getStore().getId()
        );
    }

    public ProductRS updateProductPrice(Long id, Long newPrice) {
        if(newPrice<=0)
            throw new IllegalArgumentException("Minimum price is 0");
        try {
            Product product = productRepository.getById(id);
            product.setPrice(newPrice);
            productRepository.save(product);
            return  new ProductRS(
                    product.getId(),
                    product.getPrice(),
                    product.getName(),
                    product.getProductType(),
                    product.getOrders().stream().map(x -> (Long) x.getId()).collect(Collectors.toList()),
                    product.getStore().getId()
            );
        }
        catch (Exception e){
            throw new ResourceNotFound("Product not Found!");
        }
    }

    public void deleteById(Long id) {
        try {
            productRepository.deleteById(id);
        }catch (Exception e){
            throw new ResourceNotFound("Product Not Found");
        }
    }
}
