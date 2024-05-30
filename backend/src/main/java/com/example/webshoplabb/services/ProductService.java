package com.example.webshoplabb.services;

import com.example.webshoplabb.dto.ProductDTO;
import com.example.webshoplabb.models.Category;
import com.example.webshoplabb.models.Product;
import com.example.webshoplabb.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements CommandLineRunner {

    @Autowired
    private final ProductRepository productRepository;

    public void createProduct(ProductDTO productDTO) {
        Product product = new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getPrice(),
                productDTO.getCategory()
        );
        productRepository.save(product);

    }

    public void createDefaultProducts(List<ProductDTO> productDTOList) {
        List<Product> products = productDTOList.stream()
                .map(dto -> Product.builder()
                        .name(dto.getName())
                        .price(dto.getPrice())
                        .category(dto.getCategory())
                        .build())
                .collect(Collectors.toList());

        productRepository.saveAll(products);
        products.forEach(product -> System.out.println("Created product: " + product));
    }


    public List<ProductDTO> getProducts() {
        List<Product> productList = productRepository.findAll();

        System.out.printf("message: " + productList);
        return productList.stream()
                .map(product -> {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setId(product.getId());
                    productDTO.setName(product.getName());
                    productDTO.setPrice(product.getPrice());
                    productDTO.setCategory(product.getCategory());

                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }

    public Long editProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findProductById(id);

        if (existingProduct == null) {
            throw new EntityNotFoundException("News with id " + id + " not found");
        }

        existingProduct.setId(productDTO.getId());
        existingProduct.setCategory(productDTO.getCategory());
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());

        Product updatedProduct = productRepository.save(existingProduct);

        return updatedProduct.getId();

    }

    public void deleteProductById(Long id) {
        Product product = productRepository.findProductById(id);
        if(product != null) {
            productRepository.delete(product);
        } else {
            throw new IllegalArgumentException("Product not found");
        }
    }

    private ProductDTO mapProductToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategory(product.getCategory());
        return productDTO;
    }

    @Override
    public void run(String... args) throws Exception {
        List<ProductDTO> defaultProducts = Arrays.asList(
                ProductDTO.builder().name("Banana").price(10).category(Category.FRUIT).build(),
                ProductDTO.builder().name("Apple").price(10).category(Category.FRUIT).build(),
                ProductDTO.builder().name("Orange").price(15).category(Category.FRUIT).build(),
                ProductDTO.builder().name("Grapes").price(20).category(Category.FRUIT).build(),
                ProductDTO.builder().name("Tomato").price(10).category(Category.VEGETABLE).build(),
                ProductDTO.builder().name("Potato").price(5).category(Category.VEGETABLE).build(),
                ProductDTO.builder().name("Cabbage").price(10).category(Category.VEGETABLE).build(),
                ProductDTO.builder().name("Cucumber").price(10).category(Category.VEGETABLE).build(),
                ProductDTO.builder().name("Onion").price(5).category(Category.VEGETABLE).build(),
                ProductDTO.builder().name("Onion").price(5).category(Category.VEGETABLE).build(),
                ProductDTO.builder().name("Lamb Chop").price(50).category(Category.MEAT).build(),
                ProductDTO.builder().name("Prime Rib").price(50).category(Category.MEAT).build(),
                ProductDTO.builder().name("T-bone").price(50).category(Category.MEAT).build(),
                ProductDTO.builder().name("Salmon").price(60).category(Category.FISH).build(),
                ProductDTO.builder().name("Lobster").price(70).category(Category.FISH).build(),
                ProductDTO.builder().name("Water").price(10).category(Category.BEVERAGE).build(),
                ProductDTO.builder().name("Fanta").price(15).category(Category.BEVERAGE).build()
        );
        createDefaultProducts(defaultProducts);
    }


}
