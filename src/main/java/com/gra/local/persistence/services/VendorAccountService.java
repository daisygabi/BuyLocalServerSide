package com.gra.local.persistence.services;

import com.gra.local.configuration.JwtTokenProvider;
import com.gra.local.exceptions.CustomException;
import com.gra.local.persistence.EntityHelper;
import com.gra.local.persistence.domain.VendorAccount;
import com.gra.local.persistence.repositories.VendorAccountRepository;
import com.gra.local.persistence.domain.Role;
import com.gra.local.persistence.services.dtos.VendorAccountDto;
import com.gra.local.utils.CodeGenerator;
import com.twilio.rest.lookups.v1.PhoneNumber;
import com.twilio.rest.lookups.v1.PhoneNumberFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class VendorAccountService {

    private VendorAccountRepository vendorAccountRepository;
    private TwilioSmsApiWrapper twilioSmsApiWrapper;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private CodeGenerator verificationCodeGenerator;

    public VendorAccount save(VendorAccountDto dto) {
        VendorAccount castVendorDetails = EntityHelper.convertToAbstractEntity(dto, VendorAccount.class);
        castVendorDetails.setRole(Role.VENDOR);
        return getVendorAccountRepository().save(castVendorDetails);
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

    public boolean sendValidationSMSCodeToVendor(String toNumber, String fromNumber, String message) {
        try {
            return getTwilioSmsApiWrapper().create(toNumber, fromNumber, message);
        } catch (com.twilio.exception.ApiException e) {
            throw new CustomException("Couldn't send validation SMS to Vendor", HttpStatus.BAD_REQUEST, e);
        }
    }

    public VendorAccount saveVerificationCode(final String code, final String accountPhoneNumber) {
        return getVendorAccountRepository().updateVerificationCodeAndStatus(code, accountPhoneNumber).get();
    }

    public boolean checkIfVendorPhoneNumberIsVerified(String toNumber) {
        return getVendorAccountRepository().checkIfVendorPhoneNumberIsVerified(toNumber).get().isVerified();
    }

    public boolean verifyCode(String code) {
        return getVendorAccountRepository().findByCode(code).isPresent();
    }

    public boolean verifyAccount(final String phoneNumber) {
        String createVerificationCode = getVerificationCodeGenerator().createVerificationCode();
        boolean smsSent = sendValidationSMSCodeToVendor(phoneNumber, System.getenv("TWILIO_DEV_TEST_PHONE_NR"), createVerificationCode);
        if (smsSent) {
            saveVerificationCode(createVerificationCode, phoneNumber);
        }
        return smsSent;
    }

    public VendorAccount findByPhone(String phone) {
        return getVendorAccountRepository().findByPhone(phone).get();
    }

    public String login(String phone, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
            return getJwtTokenProvider().createToken(phone, getVendorAccountRepository().findByPhone(phone).get().getRole());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid credentials", HttpStatus.UNPROCESSABLE_ENTITY, e);
        }
    }

    public VendorAccount updatePassword(VendorAccount vendorAccount, String password) {
        vendorAccount.setPassword(getPasswordEncoder().encode(password));
        getVendorAccountRepository().updatePassword(vendorAccount);
        return vendorAccount;
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

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public JwtTokenProvider getJwtTokenProvider() {
        return jwtTokenProvider;
    }

    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public CodeGenerator getVerificationCodeGenerator() {
        return verificationCodeGenerator;
    }

    public void setVerificationCodeGenerator(CodeGenerator verificationCodeGenerator) {
        this.verificationCodeGenerator = verificationCodeGenerator;
    }
}
