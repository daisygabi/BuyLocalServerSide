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
}
