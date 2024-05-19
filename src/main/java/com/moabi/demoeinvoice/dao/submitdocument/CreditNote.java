package com.moabi.demoeinvoice.dao.submitdocument;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.*;

@Getter
//@Data
@EqualsAndHashCode(callSuper = false)
public class CreditNote extends Invoice {
    private DocumentReference invoiceDocumentReference;

    public CreditNote(
            String id,
            Date issueDateTime,
            String invoiceTypeCode,
            String documentCurrencyCode,
            Period invoicePeriod,
            DocumentReference billingReference,
            DocumentReference invoiceDocumentReference,
            DocumentReference[] additionalDocumentReference,
            AccountingParty accountingSupplierParty,
            AccountingParty accountingCustomerParty,
            Delivery delivery,
            PaymentMeans paymentMeans,
            PaymentTerms paymentTerms,
            Payment prepaidPayment,
            Charge[] allowanceCharge,
            TaxTotal taxTotal,
            LegalMonetaryTotal legalMonetaryTotal,
            InvoiceLine[] invoiceLine) {
        super(
                id,
                issueDateTime,
                invoiceTypeCode,
                documentCurrencyCode,
                invoicePeriod,
                billingReference,
                additionalDocumentReference,
                accountingSupplierParty,
                accountingCustomerParty,
                delivery,
                paymentMeans,
                paymentTerms,
                prepaidPayment,
                allowanceCharge,
                taxTotal,
                legalMonetaryTotal,
                invoiceLine);
        this.invoiceDocumentReference = invoiceDocumentReference;
    }

    public DocumentReference getInvoiceDocumentReference() {
        return invoiceDocumentReference;
    }

    @Override
//    public Map<Object, Object> toMap() {
//        Map<Object, Object> invoiceMap = super.toMap();
//        invoiceMap.put("BillingReference", new ArrayList<Object>() {{
//            add(new LinkedHashMap<Object, Object>() {{
//                put("InvoiceDocumentReference", new ArrayList<Object>() {{
//                    add(invoiceDocumentReference.toMap());
//                }});
//            }});
//        }});
////        invoiceMap.put("InvoiceDocumentReference", Arrays.stream(invoiceDocumentReference).map(DocumentReference::toMap).toArray());
//        return invoiceMap;
//    }

    public Map<Object, Object> toMap() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss'Z'");
        return new LinkedHashMap<Object, Object>() {{
            put("_D", "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2");
            put("_A", "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2");
            put("_B", "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2");
            put("Invoice", new ArrayList<Object>() {{
                add(
                        new LinkedHashMap<Object, Object>() {{
                            put("ID", new ArrayList<Object>() {{ add(new LinkedHashMap<Object, Object>() {{ put("_", getId()); }}); }});
                            put("IssueDate", new ArrayList<Object>() {{ add(new LinkedHashMap<Object, Object>() {{ put("_", dateFormatter.format(getIssueDateTime())); }}); }});
                            put("IssueTime", new ArrayList<Object>() {{ add(new LinkedHashMap<Object, Object>() {{ put("_", timeFormatter.format(getIssueDateTime())); }}); }});
                            put("InvoiceTypeCode", new ArrayList<Object>() {{ add(new LinkedHashMap<Object, Object>() {{ put("_", getInvoiceTypeCode()); put("listVersionID", "1.0"); }}); }});
                            put("DocumentCurrencyCode", new ArrayList<Object>() {{ add(new LinkedHashMap<Object, Object>() {{ put("_", getDocumentCurrencyCode()); }}); }});
                            put("InvoicePeriod", new ArrayList<Object>() {{ add(getInvoicePeriod().toMap()); }});
//                            put("BillingReference", new ArrayList<Object>() {{ add(new LinkedHashMap<Object, Object>() {{ put("AdditionalDocumentReference", new ArrayList<Object>() {{ add(getBillingReference().toMap()); }}); }}); }});
                            put("BillingReference", new ArrayList<Object>() {{
                                add(new LinkedHashMap<Object, Object>() {{
                                    put("AdditionalDocumentReference", new ArrayList<Object>() {{ add(getBillingReference().toMap()); }});
                                }});
                                add(new LinkedHashMap<Object, Object>() {{
                                    put("InvoiceDocumentReference", new ArrayList<Object>() {{ add(invoiceDocumentReference.toMap()); }});
                                }});
                            }});
                            put("AdditionalDocumentReference", Arrays.stream(getAdditionalDocumentReference()).map(DocumentReference::toMap).toArray());
                            put("AccountingSupplierParty", new ArrayList<Object>() {{ add(getAccountingSupplierParty().toMap()); }});
                            put("AccountingCustomerParty", new ArrayList<Object>() {{ add(getAccountingCustomerParty().toMap()); }});
                            put("Delivery", new ArrayList<Object>() {{ add(getDelivery().toMap()); }});
                            put("PaymentMeans", new ArrayList<Object>() {{ add(getPaymentMeans().toMap()); }});
                            put("PaymentTerms", new ArrayList<Object>() {{ add(getPaymentTerms().toMap()); }});
                            put("PrepaidPayment", new ArrayList<Object>() {{ add(getPrepaidPayment().toMap()); }});
                            if (getAllowanceCharge() != null && getAllowanceCharge().length > 0)
                                put("AllowanceCharge", Arrays.stream(getAllowanceCharge()).map(Charge::toMap).toArray());
                            put("TaxTotal", new ArrayList<Object>() {{ add(getTaxTotal().toMap()); }});
                            put("LegalMonetaryTotal", new ArrayList<Object>() {{ add(getLegalMonetaryTotal().toMap()); }});
                            put("InvoiceLine", Arrays.stream(getInvoiceLine()).map(InvoiceLine::toMap).toArray());
                        }}
                );
            }});
        }};
    }
}