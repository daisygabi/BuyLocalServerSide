package com.gra.local.persistence.repositories;

import com.gra.local.persistence.domain.VendorAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorAccountRepository extends JpaRepository<VendorAccount, Long> {

}
