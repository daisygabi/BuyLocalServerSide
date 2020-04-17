package com.gra.local.persistence.repositories;

import com.gra.local.persistence.domain.VendorAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface VendorAccountRepository extends JpaRepository<VendorAccount, Long> {

    @Query("SELECT account FROM VendorAccount account where account.phone = ?1")
    Optional<VendorAccount> checkIfVendorPhoneNumberIsVerified(String phoneNumber);

    @Transactional
    @Modifying
    @Query("UPDATE VendorAccount account SET account.verified = true, account.verifyingCode= :code WHERE account.phone = :phoneNumber")
    Optional<VendorAccount> updateVerificationCodeAndStatus(@Param("code") String code, @Param("phoneNumber") String phoneNumber);

    @Query("SELECT account FROM VendorAccount account where account.verifyingCode = ?1 and account.verified = true")
    Optional<VendorAccount> findByCode(String code);

    @Query("SELECT account FROM VendorAccount account where account.phone = ?1")
    Optional<VendorAccount> findByPhone(final String phone);

    @Transactional
    @Modifying
    @Query("UPDATE VendorAccount  account SET account.password =:#{#account.password} WHERE account.id =:#{#account.id}")
    void updatePassword(VendorAccount account);

    @Query("SELECT account FROM VendorAccount account where account.verified = true and account.role = 1")
    List<VendorAccount> findAllVendorsThatAreVerified();
}
