package com.gra.local.persistence.services;

import com.gra.local.exceptions.CustomException;
import com.gra.local.persistence.EntityHelper;
import com.gra.local.persistence.domain.VendorAccount;
import com.gra.local.persistence.repositories.VendorAccountRepository;
import com.gra.local.persistence.services.dtos.VendorAccountDto;
import com.twilio.rest.lookups.v1.PhoneNumber;
import com.twilio.rest.lookups.v1.PhoneNumberFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class VendorAccountService {

    private VendorAccountRepository vendorAccountRepository;
    private TwilioSmsApiWrapper twilioSmsApiWrapper;

    public VendorAccount save(VendorAccountDto dto) {
        return getVendorAccountRepository().save(EntityHelper.convertToAbstractEntity(dto, VendorAccount.class));
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        try {
            PhoneNumberFetcher fetcher = getTwilioSmsApiWrapper().createPhoneNumberFetcher(phoneNumber);
            PhoneNumber number = fetcher.fetch();
            return number.getCarrier() != null;
        } catch (com.twilio.exception.ApiException e) {
            throw new CustomException("Couldn't validate phone number", HttpStatus.NOT_FOUND, e);
        }
    }

    public boolean sendValidationSMSCodeToVendor(String toNumber, String message) {
        try {
            return getTwilioSmsApiWrapper().create(toNumber, System.getenv("TWILIO_DEV_TEST_PHONE_NR"), message);
        } catch (com.twilio.exception.ApiException e) {
            throw new CustomException("Couldn't send validation SMS to Vendor", HttpStatus.BAD_REQUEST, e);
        }
    }

    public boolean checkIfVendorPhoneNumberIsVerified(String toNumber) {
        return getVendorAccountRepository().checkIfVendorPhoneNumberIsVerified(toNumber).get().isVerified();
    }

    @Autowired
    public void setVendorAccountService(VendorAccountRepository vendorAccountRepository) {
        this.vendorAccountRepository = vendorAccountRepository;
    }

    public VendorAccountRepository getVendorAccountRepository() {
        return vendorAccountRepository;
    }

    public void setTwilioSmsApiWrapper(TwilioSmsApiWrapper twilioSmsApiWrapper) {
        this.twilioSmsApiWrapper = twilioSmsApiWrapper;
    }

    public TwilioSmsApiWrapper getTwilioSmsApiWrapper() {
        return twilioSmsApiWrapper != null ? twilioSmsApiWrapper : new TwilioSmsApiWrapper();
    }
}
