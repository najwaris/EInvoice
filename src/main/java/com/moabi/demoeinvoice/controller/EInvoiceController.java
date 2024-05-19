package com.moabi.demoeinvoice.controller;

import com.moabi.demoeinvoice.dao.*;
import com.moabi.demoeinvoice.dao.submitdocument.*;
import com.moabi.demoeinvoice.feign.EInvoiceSystemFeign;
import com.moabi.demoeinvoice.helper.TestHelper;
import com.moabi.demoeinvoice.service.EInvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@RequestMapping("/eInvoice")
public class EInvoiceController {
    @Autowired
    private EInvoiceServiceImpl eInvoiceService;

    // 200
    @GetMapping("/validate-tin")
    public boolean validateTin(
            @RequestParam("tin") String tin,
            @RequestParam("idType") EInvoiceSystemFeign.ID_TYPE idType,
            @RequestParam("idValue") String idValue
    ) {
        return eInvoiceService.isTaxPayerIdentificationNumberValid(tin, idType, idValue);
    }

    // 200 | Invoice
    @PostMapping("/submit-document")
    public String submitDocument(
    ) throws IOException {
        String submissionresponse = eInvoiceService.submitInvoice(
                new Invoice[]{
                        TestHelper.generateTestInvoiceObject(),   // invoice
                });
        return ("####Submission Response: " + submissionresponse);
    }

    // 200 | CreditNote and other 3 | being rejected instead due to the originalInvoiceRefNum
    @PostMapping("/submit-another-document")
    public String submitDocument2(
    ) throws IOException {
        String submissionresponse = eInvoiceService.submitAnotherInvoice(
                new CreditNote[]{
                        TestHelper.generateTestInvoiceObject2(),    // creditNote
//                        TestHelper.generateTestInvoiceObject3(),    // debitNote
                });
        return ("####Submission Response: " + submissionresponse);
    }

    //200
    @PutMapping("/cancel-document")
    public boolean cancelDocument(
            @RequestParam("uuid") String uuid,
            @RequestBody InvoiceCancel invoiceCancel
    ) {
        return eInvoiceService.cancelDocument(uuid, invoiceCancel);
    }
}
