package com.gra.local.persistence.services;

import com.gra.local.configuration.JwtTokenProvider;
import com.gra.local.exceptions.CustomException;
import com.gra.local.persistence.EntityHelper;
import com.gra.local.persistence.domain.VendorAccount;
import com.gra.local.persistence.repositories.VendorAccountRepository;
import com.gra.local.persistence.services.dtos.VendorAccountDto;
import com.gra.local.persistence.services.dtos.VendorAccountLoginResponse;
import com.gra.local.utils.CodeGeneration;
import com.twilio.rest.lookups.v1.PhoneNumber;
import com.twilio.rest.lookups.v1.PhoneNumberFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorAccountService {

    private VendorAccountRepository vendorAccountRepository;
    private TwilioSmsApiWrapper twilioSmsApiWrapper;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

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
        String createVerificationCode = new CodeGeneration().createVerificationCode();
        boolean smsSent = sendValidationSMSCodeToVendor(phoneNumber, createVerificationCode);
        if (smsSent) {
            saveVerificationCode(createVerificationCode, phoneNumber);
        }
        return smsSent;
    }

    public String login(String phone, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
            return getJwtTokenProvider().createToken(phone, getVendorAccountRepository().findByPhone(phone).get().getRole());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid credentials", HttpStatus.UNPROCESSABLE_ENTITY, e);
        }
    }

    public VendorAccountLoginResponse signup(VendorAccount user) {
        Optional<VendorAccount> existingAccount = getVendorAccountRepository().findByPhone(user.getPhone());
        if (existingAccount.isPresent()) {
            existingAccount.get().setPassword(getPasswordEncoder().encode(user.getPassword()));
            updatePassword(existingAccount.get());
            VendorAccountLoginResponse vendorAccountLoginResponse = new VendorAccountLoginResponse();
            vendorAccountLoginResponse.setAccountId(existingAccount.get().getId());
            vendorAccountLoginResponse.setEmail(existingAccount.get().getEmail());
            vendorAccountLoginResponse.setPhone(existingAccount.get().getPhone());

            return vendorAccountLoginResponse;
        } else {
            throw new CustomException("Signup error because this email exists already.", HttpStatus.UNPROCESSABLE_ENTITY, new Exception("No account found with the provided information"));
        }
    }

    public VendorAccount updatePassword(VendorAccount vendorAccount) {
        return getVendorAccountRepository().updatePassword(vendorAccount).get();
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
}
