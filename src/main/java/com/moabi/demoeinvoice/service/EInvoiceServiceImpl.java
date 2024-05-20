package com.moabi.demoeinvoice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.moabi.demoeinvoice.dao.EInvoiceAuthDAO;
import com.moabi.demoeinvoice.dao.InvoiceCancel;
import com.moabi.demoeinvoice.dao.submitdocument.CreditNote;
import com.moabi.demoeinvoice.dao.submitdocument.Document;
import com.moabi.demoeinvoice.dao.submitdocument.DocumentSubmission;
import com.moabi.demoeinvoice.dao.submitdocument.Invoice;
import com.moabi.demoeinvoice.feign.EInvoiceAuthFeign;
import com.moabi.demoeinvoice.feign.EInvoiceSystemFeign;
import com.moabi.demoeinvoice.helper.EInvoiceHelper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Service
public class EInvoiceServiceImpl {
    @Autowired
    private EInvoiceAuthFeign eInvoiceAuthFeign;

    @Autowired
    private EInvoiceSystemFeign eInvoiceSystemFeign;

    public String generateToken() {
        String clientId = <YOUR_CLIENT_ID>;
        String clientSecret = <YOUR_CLIENT_SECRET>;
        String grantType = "client_credentials";
        String scope = "InvoicingAPI";

        String result = eInvoiceAuthFeign.generateToken(new EInvoiceAuthDAO(clientId, clientSecret, grantType, scope));
        JSONObject jsonObject = new JSONObject(result);
        return "Bearer " + jsonObject.getString("access_token");
    }

    public boolean isTaxPayerIdentificationNumberValid(String tin, EInvoiceSystemFeign.ID_TYPE idType, String idValue) {
        try {
            System.out.println("isTaxPayerIdentificationNumberValid: request: tin: " + tin + " idType: " + idType + " idValue: " + idValue);
            String result = eInvoiceSystemFeign.validateTaxpayerTIN(generateToken(), tin, idType, idValue);
            System.out.println("isTaxPayerIdentificationNumberValid: response: " + result);
            return true;
        } catch (Exception e) {
        }

        return false;
    }

    public boolean cancelDocument(String uuid, InvoiceCancel invoiceCancel) {
        try {
            System.out.println("cancelDocument: request: uuid: " + uuid + " invoiceCancel: " + new Gson().toJson(invoiceCancel));
            String result = eInvoiceSystemFeign.cancelDocument(generateToken(), uuid, invoiceCancel);
            System.out.println("cancelDocument: response: " + result);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * If return null, means failed to call api from e-Invoice system
     */
    public String submitInvoice(Invoice[] invoices) throws IOException {
        Document[] documents = Arrays.stream(invoices).map(EInvoiceHelper::convertInvoice).toArray(Document[]::new);
        DocumentSubmission submission = new DocumentSubmission(documents);
        Map<Object, Object> requestBodyMap = submission.toMap();

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(requestBodyMap);

        try {
            System.out.println("submitInvoice: request: requestBody: " + new Gson().toJson(requestBody));
            String result = eInvoiceSystemFeign.submitDocuments(generateToken(), requestBody);
            System.out.println("submitInvoice: response: " + result);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String submitAnotherInvoice(CreditNote[] creditNotes) throws IOException {
        Document[] documents = Arrays.stream(creditNotes).map(EInvoiceHelper::convertAnotherInvoice).toArray(Document[]::new);
        DocumentSubmission submission = new DocumentSubmission(documents);
        Map<Object, Object> requestBodyMap = submission.toMap();

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(requestBodyMap);

        try {
            System.out.println("submitInvoice2: request: requestBody: " + new Gson().toJson(requestBody));
            String result = eInvoiceSystemFeign.submitDocuments(generateToken(), requestBody);
            System.out.println("submitInvoice2: response: " + result);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
