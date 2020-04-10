package com.gra.local.persistence.repositories;

import com.gra.local.persistence.domain.VendorProduct;
import com.gra.local.persistence.services.dtos.VendorProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorProductsRepository extends JpaRepository<VendorProduct, Long> {

    @Query("SELECT product FROM VendorProduct product where product.name = ?1 and product.quantityType = ?2")
    Optional<VendorProduct> findByNameAnd(String name, int quantityTypeId);

    @Query("UPDATE VendorProduct product SET product.name= :#{product.name}, product.minQuantityPerOrder= :#{product.minQuantityPerOrder}, product.maxQuantityPerOrder=  :#{product.maxQuantityPerOrder}, product.quantityType =  :#{product.quantityType} WHERE product.id = :#{product.id}")
    VendorProduct update(@Param("product") VendorProductDto product);
}
