package com.moabi.demoeinvoice.feign;

//import com.moabi.demoeinvoice.config.FeignConfig;

import com.moabi.demoeinvoice.config.FormFeignEncoderConfig;
import com.moabi.demoeinvoice.dao.EInvoiceAuthDAO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component

@FeignClient(name = "e-invoice-auth-feign", url = "https://preprod-api.myinvois.hasil.gov.my", configuration = FormFeignEncoderConfig.class) //set FeignConfig later
public interface EInvoiceAuthFeign {
    @PostMapping(value = "/connect/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String generateToken(
        @RequestBody EInvoiceAuthDAO eInvoiceAuthDAO
    );

}
