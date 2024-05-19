package com.moabi.demoeinvoice.feign;

//import com.moabi.demoeinvoice.config.FeignConfig;

import com.moabi.demoeinvoice.dao.InvoiceCancel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component

@FeignClient(name = "e-invoice-feign", url = "https://preprod-api.myinvois.hasil.gov.my") //set FeignConfig later
public interface EInvoiceSystemFeign {
    enum ID_TYPE {
        NRIC,
        PASSPORT,
        BRN,
        ARMY
    }

//    enum DOC_TYPE {
//        INVOICE,
//        CREDIT_NOTE,
//        DEBIT_NOTE,
//        REFUND_NOTE,
//        SELF_BILLED_INVOICE,
//        SELF_BILLED_CREDIT_NOTE,
//        SELF_BILLED_DEBIT_NOTE,
//        SELF_BILLED_REFUND_NOTE
//    }

    @GetMapping("/api/v1.0/taxpayer/validate/{tin}")
    String validateTaxpayerTIN(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("tin") String tin,
            @RequestParam("idType") ID_TYPE idType,
            @RequestParam("idValue") String idValue
    );

    @PostMapping(value = "/api/v1.0/documentsubmissions/", consumes = MediaType.APPLICATION_JSON_VALUE)
    String submitDocuments(
            @RequestHeader("Authorization") String authorization,
            @RequestBody String val
    );

    @PutMapping("/api/v1.0/documents/state/{UUID}/state")
    String cancelDocument(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("UUID") String uuid,
            @RequestBody InvoiceCancel invoiceCancel
    );
}
