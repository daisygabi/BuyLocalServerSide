package com.gra.local.persistence.repositories;

import com.gra.local.persistence.domain.VendorProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorProductRepository extends JpaRepository<VendorProduct, Long> {

}
