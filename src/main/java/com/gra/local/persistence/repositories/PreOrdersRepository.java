package com.gra.local.persistence.repositories;

import com.gra.local.persistence.domain.PreOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface PreOrdersRepository extends JpaRepository<PreOrders, Long> {

    @Query("SELECT order FROM PreOrders order where order.vendorId = ?1")
    List<PreOrders> findAllPreOrdersByVendorId(Long vendorId);

    @Query("SELECT preorder FROM PreOrders preorder where preorder.acceptLink = ?1")
    Optional<PreOrders> findAcceptedUrl(String uuid);

    @Query("SELECT preorder FROM PreOrders preorder where preorder.denyLink = ?1")
    Optional<PreOrders> findDeniedUrl(String uuid);

    @Transactional
    @Modifying
    @Query("UPDATE PreOrders preOrder SET preOrder.acceptedByVendor = ?2 WHERE preOrder.id= ?1")
    void updatePreOrderStatus(Long id, boolean acceptedStatus);
}
