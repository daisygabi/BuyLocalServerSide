package com.gra.local.persistence.services;

import com.gra.local.persistence.EntityHelper;
import com.gra.local.persistence.domain.QuantityType;
import com.gra.local.persistence.domain.VendorProduct;
import com.gra.local.persistence.repositories.VendorProductsRepository;
import com.gra.local.persistence.services.dtos.VendorProductDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("dev")
public class VendorProductsServiceTest {

    @InjectMocks
    private VendorProductsService subject;

    @Mock
    private VendorProductsRepository repository;

    private VendorProductDto vendorProductDto;
    private VendorProductDto vendorProductToUpdateDto;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        vendorProductDto = new VendorProductDto();
        vendorProductDto.setName("Letuce");
        vendorProductDto.setMinQuantityPerOrder(1.0);
        vendorProductDto.setMaxQuantityPerOrder(10.0);
        vendorProductDto.setQuantityTypeId(QuantityType.GRAMS.getIndex());

        vendorProductToUpdateDto = new VendorProductDto();
        vendorProductDto.setId(1L);
        vendorProductToUpdateDto.setName("Letuce is nicely edited");
        vendorProductToUpdateDto.setMinQuantityPerOrder(0.5);
        vendorProductToUpdateDto.setMaxQuantityPerOrder(12.0);
        vendorProductToUpdateDto.setQuantityTypeId(QuantityType.KG.getIndex());
    }

    @Test
    public void addProduct_always_saves_the_given_product_and_returns_it() {
        AtomicReference<VendorProduct> savedProduct = new AtomicReference<>();
        when(repository.save(argThat(productToSave -> {
            assertEquals("Letuce", productToSave.getName());
            assertEquals(java.util.Optional.of(1.0).get(), productToSave.getMinQuantityPerOrder());
            assertEquals(java.util.Optional.of(10.0).get(), productToSave.getMaxQuantityPerOrder());
            assertEquals(QuantityType.GRAMS.getIndex(), productToSave.getQuantityType());
            return false;
        }))).thenAnswer(invocationOnMock -> {
            VendorProduct productToSave = invocationOnMock.getArgument(0);
            productToSave.setId(2L);
            savedProduct.set(productToSave);
            return productToSave;
        });

        VendorProduct result = subject.save(vendorProductDto);

        assertSame(savedProduct.get(), result);
    }

    @Test
    public void updateProduct_always_updates_the_given_product_and_returns_it() {
        AtomicReference<VendorProduct> foundExistingProduct = new AtomicReference<>();
        when(repository.findAllById(argThat(idToFind -> {
            assertEquals(1L, idToFind);
            return false;
        }))).thenAnswer(invocationOnMock -> {
            Optional<VendorProduct> productToSaveOptional = invocationOnMock.getArgument(0);
            foundExistingProduct.set(productToSaveOptional.get());
            return productToSaveOptional.get();
        });

        subject.update(vendorProductToUpdateDto);

        verify(repository).findById(vendorProductToUpdateDto.getId());
    }

    @Test
    public void deleteProductById_always_deletes_product_and_returns_nothing() {
        long productId = 1L;
        subject.delete(productId);
        verify(repository).deleteById(productId);
    }

    @Test
    public void deleteProduct_always_deletes_product_and_returns_nothing() {
        subject.delete(vendorProductDto);
        verify(repository).delete(any(VendorProduct.class));
    }

    @Test
    public void findAllProducts_always_returns_all_existing_products() {
        List<VendorProduct> productList = new ArrayList<>();
        productList.add(EntityHelper.convertToAbstractEntity(vendorProductDto, VendorProduct.class));
        when(repository.findAll()).thenReturn(productList);

        List<VendorProduct> foundProducts = subject.findAllProducts();

        assertNotNull(foundProducts);
        assertEquals(foundProducts.size(), productList.size());
    }
}
