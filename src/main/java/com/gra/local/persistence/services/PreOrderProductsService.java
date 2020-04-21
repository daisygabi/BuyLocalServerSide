package com.gra.local.persistence.services;

import com.gra.local.persistence.domain.PreOrders;
import com.gra.local.persistence.repositories.PreOrdersRepository;
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
    public boolean sendOrderDetailsBySmsToVendors(@Valid String customerPhoneNumber, @Valid @NonNull List<VendorProductDto> products, @Valid @NonNull Long preorderId) {
        Optional<PreOrders> preOrder = getPreOrdersRepository().findById(preorderId);
        if (preOrder.isPresent()) {
            String smsMessage = constructSmsMessage(products, preOrder.get());
            return getTwilioSmsApiWrapper().create(customerPhoneNumber, System.getenv("TWILIO_DEV_TEST_PHONE_NR"), smsMessage);
        }
        return false;
    }

    public PreOrders save(VendorsAndTheirProductsResponse data) throws NoSuchAlgorithmException {
        PreOrders preOrder = new PreOrders();
        preOrder.setVendorId(data.getVendorId());
        preOrder.setCustomerPhoneNumber(data.getCustomerPhoneNumber());
        preOrder.setProductIds(createProductIdArray(data.getProducts()));

        String uuid = CodeGenerator.generateUUID();
        preOrder.setAcceptLink(createAcceptLink(uuid));
        preOrder.setDenyLink(createDenyLink(uuid));

        return getPreOrdersRepository().save(preOrder);
    }

    private String[] createProductIdArray(List<VendorProductDto> preOrderProducts) {
        String[] productIds = new String[preOrderProducts.size()];
        for (int i = 0; i < preOrderProducts.size(); i++) {
            productIds[i] = preOrderProducts.get(i).getId().toString();
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
        return getPreOrdersRepository().findAcceptedUrl(getClientBaseUrlPath() + "accept/" + uuid);
    }

    public Optional<PreOrders> findDeniedLink(String uuid) {
        return getPreOrdersRepository().findDeniedUrl(getClientBaseUrlPath() + "deny/" + uuid);
    }

    public void updateStatusToAccepted(Long preOrderId) {
        Optional<PreOrders> preOrderOptional = getPreOrdersRepository().findById(preOrderId);
        getPreOrdersRepository().updatePreOrderStatus(preOrderOptional.get().getId(), Boolean.TRUE);
    }

    public void updateStatusToDenied(Long preOrderId) {
        Optional<PreOrders> preOrderOptional = getPreOrdersRepository().findById(preOrderId);
        getPreOrdersRepository().updatePreOrderStatus(preOrderOptional.get().getId(), Boolean.FALSE);
    }

    private String constructSmsMessage(@Valid @NonNull List<VendorProductDto> products, PreOrders preOrder) {
        StringBuffer messageBuffer = new StringBuffer();
        messageBuffer.append("You have an order for:");
        for (int i = 0; i < products.size(); i++) {
            messageBuffer.append(i).append(": ").append(products.get(i).getName());
            messageBuffer.append(products.get(i).getSelectedQuantity());
            messageBuffer.append("Accept link: " + preOrder.getAcceptLink());
            messageBuffer.append("Deny link: " + preOrder.getDenyLink());
        }
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
