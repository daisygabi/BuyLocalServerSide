package com.gra.local.persistence.services;

import com.gra.local.persistence.domain.VendorAccount;
import com.gra.local.persistence.repositories.VendorAccountRepository;
import com.gra.local.persistence.services.dtos.VendorAccountDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class VendorAccountServiceTest {

    @InjectMocks
    private VendorAccountService subject;

    @Mock
    private VendorAccountRepository repository;

    private VendorAccountDto vendorAccountDto;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        vendorAccountDto = new VendorAccountDto();
        vendorAccountDto.setName("Basil");
        vendorAccountDto.setCity("Sibiu");
        vendorAccountDto.setEmail("demo@email.com");
        vendorAccountDto.setPhone("+30009990999");
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
}
