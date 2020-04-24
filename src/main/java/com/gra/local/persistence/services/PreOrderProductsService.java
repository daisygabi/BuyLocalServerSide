package com.gra.local.persistence.services;

import com.gra.local.persistence.domain.PreOrders;
import com.gra.local.persistence.repositories.PreOrdersRepository;
import com.gra.local.persistence.services.dtos.Order;
import com.gra.local.persistence.services.dtos.ProductAndSelectedQuantity;
import com.gra.local.persistence.services.dtos.VendorProductDto;
import com.gra.local.persistence.services.dtos.VendorsAndTheirProductsResponse;
import com.gra.local.utils.CodeGenerator;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class PreOrderProductsService {

    private TwilioSmsApiWrapper twilioSmsApiWrapper;
    private PreOrdersRepository preOrdersRepository;

    @Value("${CLIENT_BASIC_URL_PATH}")
    private String clientBaseUrlPath;

    /**
     * When a customer wants to make an order, and sends request with products needed, they will have to wait for a confirmation from vendors
     * Each vendor will receive an SMS with what products are requested
     *
     * @param customerPhoneNumber
     * @param products
     * @return
     */
    public boolean sendOrderDetailsBySmsToVendors(@Valid String customerPhoneNumber, @Valid @NonNull ProductAndSelectedQuantity[] products, @Valid @NonNull Long preorderId) {
        Optional<PreOrders> preOrder = getPreOrdersRepository().findById(preorderId);
        if (preOrder.isPresent()) {
            String smsMessage = constructSmsMessage(products, preOrder.get());
            return getTwilioSmsApiWrapper().create(customerPhoneNumber, System.getenv("TWILIO_DEV_TEST_PHONE_NR"), smsMessage);
        }
        return false;
    }

    public PreOrders save(Order data, String customerPhoneNumber) throws NoSuchAlgorithmException {
        PreOrders preOrder = new PreOrders();
        preOrder.setVendorId(data.getVendorId());
        preOrder.setCustomerPhoneNumber(customerPhoneNumber);
        preOrder.setProductIds(createProductIdArray(data.getProducts()));

        String uuid = CodeGenerator.generateUUID();
        preOrder.setAcceptLink(createAcceptLink(uuid));
        preOrder.setDenyLink(createDenyLink(uuid));

        return getPreOrdersRepository().save(preOrder);
    }

    public boolean notifyCustomerAboutVendorsResponse(@Valid String customerPhoneNumber, String message) {
        return getTwilioSmsApiWrapper().create(customerPhoneNumber, System.getenv("TWILIO_DEV_TEST_PHONE_NR"), message);
    }

    private String[] createProductIdArray(ProductAndSelectedQuantity[] preOrderProducts) {
        String[] productIds = new String[preOrderProducts.length];
        for (int i = 0; i < preOrderProducts.length; i++) {
            productIds[i] = preOrderProducts[i].getProductId().toString();
        }
        return productIds;
    }

    private String createAcceptLink(String uuid) {
        return getClientBaseUrlPath() + "pre-order/accept/" + uuid;
    }

    private String createDenyLink(String uuid) {
        return getClientBaseUrlPath() + "pre-order/deny/" + uuid;
    }

    public Optional<PreOrders> findAcceptedUrl(String uuid) {
        return getPreOrdersRepository().findAcceptedUrl(getClientBaseUrlPath() + "pre-order/accept/" + uuid);
    }

    public Optional<PreOrders> findDeniedLink(String uuid) {
        return getPreOrdersRepository().findDeniedUrl(getClientBaseUrlPath() + "pre-order/deny/" + uuid);
    }

    public void updateStatusToAccepted(Long preOrderId) {
        Optional<PreOrders> preOrderOptional = getPreOrdersRepository().findById(preOrderId);
        getPreOrdersRepository().updatePreOrderStatus(preOrderOptional.get().getId(), Boolean.TRUE);
    }

    public void updateStatusToDenied(Long preOrderId) {
        Optional<PreOrders> preOrderOptional = getPreOrdersRepository().findById(preOrderId);
        getPreOrdersRepository().updatePreOrderStatus(preOrderOptional.get().getId(), Boolean.FALSE);
    }

    private String constructSmsMessage(@Valid @NonNull ProductAndSelectedQuantity[] products, PreOrders preOrder) {
        StringBuffer messageBuffer = new StringBuffer();
        messageBuffer.append("BuyFoodLocally! You have a new order:\n");
        for (int i = 0; i < products.length; i++) {
            messageBuffer.append("\n"+products[i].getProductName() + ": ");
            messageBuffer.append(products[i].getSelectedQuantity() + " " + products[i].getQuantityType());
        }
        messageBuffer.append("\nAccept link: " + preOrder.getAcceptLink());
        messageBuffer.append("\nDeny link: " + preOrder.getDenyLink());
        return messageBuffer.toString();
    }

    public void setTwilioSmsApiWrapper(TwilioSmsApiWrapper twilioSmsApiWrapper) {
        this.twilioSmsApiWrapper = twilioSmsApiWrapper;
    }

    public TwilioSmsApiWrapper getTwilioSmsApiWrapper() {
        return twilioSmsApiWrapper != null ? twilioSmsApiWrapper : new TwilioSmsApiWrapper();
    }

    public PreOrdersRepository getPreOrdersRepository() {
        return preOrdersRepository;
    }

    @Autowired
    public void setPreOrdersRepository(PreOrdersRepository preOrdersRepository) {
        this.preOrdersRepository = preOrdersRepository;
    }

    public String getClientBaseUrlPath() {
        return clientBaseUrlPath;
    }

    public void setClientBaseUrlPath(String clientBaseUrlPath) {
        this.clientBaseUrlPath = clientBaseUrlPath;
    }
}
