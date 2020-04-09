package com.gra.local.persistence.services;

import com.gra.local.persistence.domain.VendorProduct;
import com.gra.local.persistence.repositories.VendorProductRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class VendorProductServiceTest {

    @TestConfiguration
    static class EventServiceConfiguration {

        @Bean
        public VendorProductService vendorProductService() {
            return new VendorProductService();
        }
    }

    @Autowired
    private VendorProductService vendorProductService;

    @MockBean
    private VendorProductRepository repository;
    private VendorProduct vendorProduct;

    @Before
    public void setUp() {
        vendorProduct = new VendorProduct();
        vendorProduct.setCreatedAt(new Date());
        vendorProduct.setId(1L);
        vendorProduct.setName("Basil");
        vendorProduct.setMinQuantityPerOrder(1D);
        vendorProduct.setMaxQuantityPerOrder(0D);

        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(vendorProduct));
    }
}