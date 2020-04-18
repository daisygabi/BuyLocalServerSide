package com.gra.local.persistence.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Table(name = "preorders")
@Entity
public class PreOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "vendor_id")
    private Long vendorId;

    @Column(columnDefinition = "customer_phone_number")
    private String customerPhoneNumber;

    /**
     * Keep array of ids as string and because PostgreSql does not support this type default i added a new dependency
     * <dependency>
     * <groupId>com.vladmihalcea</groupId>
     * <artifactId>hibernate-types-52</artifactId>
     * <version>2.6.1</version>
     * </dependency>
     */
    @org.hibernate.annotations.Type(type = "string-array")
    @Column(columnDefinition = "product_ids")
    private String[] productIds;

    @Column(columnDefinition = "accepted_by_vendor")
    private boolean acceptedByVendor;

    @Column(columnDefinition = "accept_link")
    private String acceptLink;

    @Column(columnDefinition = "deny_link")
    private String denyLink;
}

