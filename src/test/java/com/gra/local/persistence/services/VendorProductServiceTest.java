package com.gra.local.persistence.services;


import com.gra.local.persistence.domain.QuantityType;
import com.gra.local.persistence.domain.VendorProduct;
import com.gra.local.persistence.repositories.VendorProductRepository;
import com.gra.local.persistence.services.dtos.VendorProductDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class VendorProductServiceTest {

    @InjectMocks
    private VendorProductService subject;

    @Mock
    private VendorProductRepository repository;

    private VendorProductDto vendorProductDto;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        vendorProductDto = new VendorProductDto();
        vendorProductDto.setName("Letuce");
        vendorProductDto.setMinQuantityPerOrder(1.0);
        vendorProductDto.setMaxQuantityPerOrder(10.0);
        vendorProductDto.setQuantityTypeId(QuantityType.GRAMS.getIndex());
    }

    @Test
    public void createNewVendorAccount_always_saves_the_given_account_and_returns_it() {
        AtomicReference<VendorProduct> savedProduct = new AtomicReference<>();
        when(repository.save(argThat(vendorAccountToSave -> {
            assertEquals("Letuce", vendorAccountToSave.getName());
            assertEquals(java.util.Optional.of(1.0).get(), vendorAccountToSave.getMinQuantityPerOrder());
            assertEquals(java.util.Optional.of(10.0).get(), vendorAccountToSave.getMaxQuantityPerOrder());
            assertEquals(QuantityType.GRAMS.getIndex(), vendorAccountToSave.getQuantityType());
            return false;
        }))).thenAnswer(invocationOnMock -> {
            VendorProduct vendorProductToSave = invocationOnMock.getArgument(0);
            vendorProductToSave.setId(2L);
            savedProduct.set(vendorProductToSave);
            return vendorProductToSave;
        });

        VendorProduct result = subject.save(vendorProductDto);

        assertSame(savedProduct.get(), result);
    }
}
