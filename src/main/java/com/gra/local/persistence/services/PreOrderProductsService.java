package com.gra.local.persistence.services;

import com.gra.local.persistence.domain.VendorProduct;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class PreOrderProductsService {

    private TwilioSmsApiWrapper twilioSmsApiWrapper;

    /**
     * When a customer wants to make an order, and sends request with products needed, they will have to wait for a confirmation from vendors
     * Each vendor will receive an SMS with what products are requested
     *
     * @param customerPhoneNumber
     * @param products
     * @return
     */
    public boolean sendOrderDetailsBySmsToVendors(@Valid String customerPhoneNumber, @Valid @NonNull List<VendorProduct> products) {
        String smsMessage = constructSmsMessage(products);
        return getTwilioSmsApiWrapper().create(customerPhoneNumber, System.getenv("TWILIO_DEV_TEST_PHONE_NR"), smsMessage);
    }

    private String constructSmsMessage(@Valid @NonNull List<VendorProduct> products) {
        StringBuffer messageBuffer = new StringBuffer();
        messageBuffer.append("You have an order for:");
        for (int i = 0; i < products.size(); i++) {
            messageBuffer.append(i).append(": ").append(products.get(i).getName());
            messageBuffer.append(products.get(i).getSelectedQuantity());
            //TODO create accept and deny links
            messageBuffer.append("Accept link:" );
            messageBuffer.append("Deny link:" );
        }
        return messageBuffer.toString();
    }

    public void setTwilioSmsApiWrapper(TwilioSmsApiWrapper twilioSmsApiWrapper) {
        this.twilioSmsApiWrapper = twilioSmsApiWrapper;
    }

    public TwilioSmsApiWrapper getTwilioSmsApiWrapper() {
        return twilioSmsApiWrapper != null ? twilioSmsApiWrapper : new TwilioSmsApiWrapper();
    }
}
