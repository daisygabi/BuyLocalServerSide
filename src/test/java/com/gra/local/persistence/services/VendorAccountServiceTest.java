package com.gra.local.persistence.services;

import com.gra.local.persistence.domain.VendorAccount;
import com.gra.local.persistence.repositories.VendorAccountRepository;
import com.gra.local.persistence.services.dtos.VendorAccountDto;
import com.twilio.rest.lookups.v1.PhoneNumber;
import com.twilio.rest.lookups.v1.PhoneNumberFetcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("dev")
public class VendorAccountServiceTest {

    @InjectMocks
    private VendorAccountService subject;

    @Mock
    private VendorAccountRepository repository;

    @Mock
    private TwilioSmsApiWrapper twilioSmsApiWrapper;

    @Mock
    private PhoneNumberFetcher mockedPhoneNumberFetcher;

    private VendorAccountDto vendorAccountDto;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        vendorAccountDto = new VendorAccountDto();
        vendorAccountDto.setName("Basil");
        vendorAccountDto.setCity("Sibiu");
        vendorAccountDto.setEmail("demo@email.com");
        vendorAccountDto.setPhone("+30009990999");

        subject.setTwilioSmsApiWrapper(twilioSmsApiWrapper);

        when(twilioSmsApiWrapper.createPhoneNumberFetcher(any())).thenReturn(mockedPhoneNumberFetcher);
    }

    @Test
    public void createNewVendorAccount_always_saves_the_given_account_and_returns_it() {
        AtomicReference<VendorAccount> savedVendorAccount = new AtomicReference<>();
        when(repository.save(argThat(vendorAccountToSave -> {
            assertEquals("Basil", vendorAccountToSave.getName());
            assertEquals("Sibiu", vendorAccountToSave.getCity());
            assertEquals("demo@email.com", vendorAccountToSave.getEmail());
            assertEquals("+30009990999", vendorAccountToSave.getPhone());
            return false;
        }))).thenAnswer(invocationOnMock -> {
            VendorAccount vendorAccountToSave = invocationOnMock.getArgument(0);
            vendorAccountToSave.setId(1L);
            savedVendorAccount.set(vendorAccountToSave);
            return vendorAccountToSave;
        });

        VendorAccount result = subject.save(vendorAccountDto);

        assertSame(savedVendorAccount.get(), result);
    }

    @Test
    public void validatePhoneNumber_always_creates_a_phone_number_fetcher_for_the_given_string() {
        String number = "+125435346";
        PhoneNumber mockedPhoneNumber = mock(PhoneNumber.class);

        when(twilioSmsApiWrapper.createPhoneNumberFetcher(number)).thenReturn(mockedPhoneNumberFetcher);
        when(mockedPhoneNumberFetcher.fetch()).thenReturn(mockedPhoneNumber);

        subject.validatePhoneNumber(number);

        verify(mockedPhoneNumberFetcher).fetch();
    }

    @Test
    public void saveVerificationCode_always_updates_code_and_verified_status_for_given_code_and_number() {
        String code = "asdad";
        String phone = "+1214234325";
        VendorAccount account = new VendorAccount();
        account.setPhone(phone);
        account.setEmail("asda@sadas.com");
        account.setVerifyingCode(code);

        when(repository.updateVerificationCodeAndStatus(code, phone)).thenReturn(java.util.Optional.of(account));
        VendorAccount updatedAccount = subject.saveVerificationCode(code, phone);

        assertEquals(updatedAccount.getVerifyingCode(), code);
    }

    @Test
    public void checkIfVendorPhoneNumberIsVerified_always_returns_true_if_verified_status_is_false() {
        String code = "asdad";
        String phone = "+1214234325";

        VendorAccount account = new VendorAccount();
        account.setPhone(phone);
        account.setEmail("asda@sadas.com");
        account.setVerifyingCode(code);
        account.setVerified(true);

        when(repository.checkIfVendorPhoneNumberIsVerified(phone)).thenReturn(java.util.Optional.of(account));
        boolean validPhoneNumber = subject.checkIfVendorPhoneNumberIsVerified(phone);

        assertEquals(validPhoneNumber, Boolean.TRUE);
    }

    @Test
    public void verifyCode_always_returns_true_if_verified_code_exists() {
        String code = "asdad";
        String phone = "+1214234325";

        VendorAccount account = new VendorAccount();
        account.setPhone(phone);
        account.setEmail("asda@sadas.com");
        account.setVerifyingCode(code);
        account.setVerified(true);

        when(repository.findByCode(code)).thenReturn(java.util.Optional.of(account));
        boolean validPhoneNumber = subject.verifyCode(code);

        assertEquals(validPhoneNumber, Boolean.TRUE);
    }

    @Test
    public void updatePassword_sets_password_to_vendor_account_always_returns_updated_object() {
        String password = "abracadabra";
        String phone = "+2343532646";
        Long id = 1L;

        VendorAccount account = new VendorAccount();
        account.setId(id);
        account.setPhone(phone);
        account.setEmail("asda@sadas.com");
        account.setVerified(true);
        account.setPassword(password);

        when(repository.updatePassword(account)).thenReturn(java.util.Optional.of(account));
        VendorAccount updatedAccount = subject.updatePassword(account);

        assertEquals(updatedAccount.getPassword(), password);
        assertEquals(updatedAccount.getPhone(), phone);
        assertEquals(updatedAccount.getId(), id);
    }
}
