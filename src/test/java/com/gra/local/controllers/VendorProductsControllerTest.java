package com.gra.local.controllers;

import com.gra.local.AuthorizedRequestTest;
import com.gra.local.persistence.domain.QuantityType;
import com.gra.local.persistence.domain.VendorProduct;
import com.gra.local.persistence.services.dtos.VendorProductDto;
import com.gra.local.persistence.services.dtos.VendorsAndTheirProductsResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VendorProductsControllerTest extends AuthorizedRequestTest {

    @Before
    public void setUp() throws Exception {
        VendorProductDto vendorProductDto = new VendorProductDto();
        vendorProductDto.setId(1L);
        vendorProductDto.setName("Mint Plants");
        vendorProductDto.setMinQuantityPerOrder(0.3d);
        vendorProductDto.setMaxQuantityPerOrder(0d);
        vendorProductDto.setQuantityTypeId(QuantityType.ITEM.getIndex());

        ResponseEntity<VendorProduct> postResponse = getRestTemplate().postForEntity(getRootUrl() + "/products/", vendorProductDto, VendorProduct.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testAddProduct_shouldReturn_StatusOK() {
        VendorProductDto vendorProductDto = new VendorProductDto();

        vendorProductDto.setName("Bay Leaf");
        vendorProductDto.setMinQuantityPerOrder(1d);
        vendorProductDto.setMaxQuantityPerOrder(100d);
        vendorProductDto.setQuantityTypeId(QuantityType.KG.getIndex());
        vendorProductDto.setVendorId(1L);
        vendorProductDto.setInStock(true);
        vendorProductDto.setPrice(34.3);
        vendorProductDto.setCurrency("Eur");

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
        vendorProductDto.setVendorId(1L);
        vendorProductDto.setInStock(true);
        vendorProductDto.setPrice(34.3);
        vendorProductDto.setCurrency("Eur");

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

    @Test
    public void testGetAllProducts_shouldReturn_list_of_existing_products() {
        VendorProduct vendorProduct = new VendorProduct();
        vendorProduct.setId(1L);
        vendorProduct.setName("Mint Plants will be deleted");
        vendorProduct.setMinQuantityPerOrder(3d);
        vendorProduct.setMaxQuantityPerOrder(100d);
        vendorProduct.setQuantityType(QuantityType.ITEM.getIndex());

        List<VendorsAndTheirProductsResponse> aggregatedData = new ArrayList<>();
        List<VendorProduct> products = new ArrayList<>();
        products.add(vendorProduct);
        aggregatedData.add(new VendorsAndTheirProductsResponse(1L, "Nobody", products));

        ResponseEntity<List> getResponse = getRestTemplate().getForEntity(getRootUrl() + "/products/", List.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(aggregatedData);
    }
}
