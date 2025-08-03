package com.waqar.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    public void registerCustomer(CustomerRegistrationRequest customerRegistration) {

        Customer customer = Customer.builder()
                .firstName(customerRegistration.firstName())
                .lastName(customerRegistration.lastName())
                .email(customerRegistration.email())
                .build();

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse =  restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

        if(fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }
        NotificationCheckResponse notificationCheckResponse =  restTemplate.postForObject(
                "http://NOTIFICATION/api/v1/sendNotification",
                fraudCheckResponse,
                NotificationCheckResponse.class
        );

    }
}
