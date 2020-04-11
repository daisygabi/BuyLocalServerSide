package com.gra.local;

import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BuyLocalApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public TwilioRestClient twilioRestClient(@Value("${TWILIO_ACCOUNT_SID}") String accountSid, @Value("${TWILIO_AUTH_TOKEN}") String authToken) {
        Twilio.init(accountSid, authToken);
        return new TwilioRestClient.Builder(accountSid, authToken).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(BuyLocalApplication.class, args);
    }
}
