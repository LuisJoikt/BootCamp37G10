package com.nttdata.bootcamp.msaccounts.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig extends RestTemplate {

    // @GetMapping(value = "/byNroDoc/{nroDoc}", consumes =
    // MediaType.APPLICATION_JSON_VALUE)
    // public Optional<CustomerDTO> findCustomerByNroDoc(@PathVariable String
    // nroDoc);
}
