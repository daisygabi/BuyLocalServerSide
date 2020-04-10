package com.gra.local.persistence.repositories;

import com.gra.local.persistence.domain.VendorProduct;
import com.gra.local.persistence.services.dtos.VendorProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface VendorProductsRepository extends JpaRepository<VendorProduct, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE VendorProduct product SET product.name =:#{#product.name}, product.minQuantityPerOrder =:#{#product.minQuantityPerOrder}, product.maxQuantityPerOrder =:#{#product.maxQuantityPerOrder} WHERE product.id =:#{#product.id}")
    int update(@Param("product") VendorProductDto product);
}
