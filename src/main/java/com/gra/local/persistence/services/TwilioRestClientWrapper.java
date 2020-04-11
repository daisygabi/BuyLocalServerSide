package com.gra.local.persistence.services;

import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;

/**
 * Wrapper for Twilio REST Client
 */
public class TwilioRestClientWrapper {

    public TwilioRestClient getRestClient() {
        return Twilio.getRestClient();
    }
}
