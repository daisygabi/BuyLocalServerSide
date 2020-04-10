package com.gra.local.controllers;

import com.gra.local.persistence.domain.QuantityType;
import com.gra.local.persistence.domain.VendorProduct;
import com.gra.local.persistence.services.dtos.VendorProductDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VendorProductsControllerTest extends BasicRequestTest {

    @Before
    public void setUp() throws Exception {
        VendorProductDto vendorProductDto = new VendorProductDto();
        vendorProductDto.setId(1L);
        vendorProductDto.setName("Mint Plants");
        vendorProductDto.setMinQuantityPerOrder(0.3d);
        vendorProductDto.setMaxQuantityPerOrder(0d);
        vendorProductDto.setQuantityTypeId(QuantityType.ITEM.getIndex());

        ResponseEntity<VendorProduct> putResponse = getRestTemplate().postForEntity(getRootUrl() + "/products/", vendorProductDto, VendorProduct.class);
        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
        assertNotNull(putResponse.getBody());
    }

    @Test
    public void testAddProduct_shouldReturn_StatusOK() {
        VendorProductDto vendorProductDto = new VendorProductDto();

        vendorProductDto.setName("Bay Leaf");
        vendorProductDto.setMinQuantityPerOrder(1d);
        vendorProductDto.setMaxQuantityPerOrder(100d);
        vendorProductDto.setQuantityTypeId(QuantityType.KG.getIndex());

        ResponseEntity<VendorProduct> putResponse = getRestTemplate().postForEntity(getRootUrl() + "/products/", vendorProductDto, VendorProduct.class);
        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
        assertNotNull(putResponse.getBody());
    }

    @Test
    public void testUpdateProduct_shouldReturn_StatusOK() {
        VendorProductDto vendorProductDto = new VendorProductDto();
        vendorProductDto.setId(1L);
        vendorProductDto.setName("Mint Plants are edited");
        vendorProductDto.setMinQuantityPerOrder(3d);
        vendorProductDto.setMaxQuantityPerOrder(100d);
        vendorProductDto.setQuantityTypeId(QuantityType.ITEM.getIndex());

        ResponseEntity<VendorProduct> putResponse = getRestTemplate().exchange(getRootUrl() + "/products/", HttpMethod.PUT, buildHTTPEntity(vendorProductDto), VendorProduct.class);
        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
        assertNotNull(putResponse.getBody());
    }

    @Test
    public void testDeleteProduct_shouldReturn_StatusOK() {
        VendorProductDto vendorProductDto = new VendorProductDto();
        vendorProductDto.setId(1L);
        vendorProductDto.setName("Mint Plants will be deleted");
        vendorProductDto.setMinQuantityPerOrder(3d);
        vendorProductDto.setMaxQuantityPerOrder(100d);
        vendorProductDto.setQuantityTypeId(QuantityType.ITEM.getIndex());

        ResponseEntity<VendorProduct> putResponse = getRestTemplate().exchange(getRootUrl() + "/products/", HttpMethod.DELETE, buildHTTPEntity(vendorProductDto), VendorProduct.class);
        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
    }
}