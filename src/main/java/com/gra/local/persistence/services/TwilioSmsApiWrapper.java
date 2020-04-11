package com.gra.local.persistence.services;

import com.gra.local.exceptions.CustomException;
import com.twilio.Twilio;
import com.twilio.exception.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.rest.lookups.v1.PhoneNumber;
import com.twilio.rest.lookups.v1.PhoneNumberFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/**
 * Wrapper for Twilio Api
 */
public class TwilioSmsApiWrapper {

    @Autowired
    private TwilioRestClientWrapper twilioRestClientWrapper;

    public PhoneNumberFetcher createPhoneNumberFetcher(String phoneNumber) {
        return PhoneNumber.fetcher(new com.twilio.type.PhoneNumber(phoneNumber)).setType("carrier");
    }

    public boolean create(String to, String from, String bodyMessage) {
        try {
            MessageCreator messageCreator = new MessageCreator(new com.twilio.type.PhoneNumber(to), new com.twilio.type.PhoneNumber(from), bodyMessage);
            Message messageSent = messageCreator.create(twilioRestClientWrapper.getRestClient());
            return messageSent.getSid() != null;
        } catch (Exception e) {
            throw new CustomException("An exception occurred trying to send a message to: " + to + " from: " + from, HttpStatus.UNAUTHORIZED, e);
        }
    }


}
