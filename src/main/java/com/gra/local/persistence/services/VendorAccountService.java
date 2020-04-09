package com.gra.local.persistence.services;

import com.gra.local.persistence.EntityHelper;
import com.gra.local.persistence.domain.VendorAccount;
import com.gra.local.persistence.repositories.VendorAccountRepository;
import com.gra.local.persistence.services.dtos.VendorAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorAccountService {

    private VendorAccountRepository vendorAccountRepository;

    @Autowired
    public void setUserRepository(VendorAccountRepository vendorAccountRepository) {
        this.vendorAccountRepository = vendorAccountRepository;
    }

    public VendorAccount save(VendorAccountDto dto) {
        return getVendorAccountRepository().save(EntityHelper.convertToAbstractEntity(dto, VendorAccount.class));
    }

    public VendorAccountRepository getVendorAccountRepository() {
        return vendorAccountRepository;
    }
}
