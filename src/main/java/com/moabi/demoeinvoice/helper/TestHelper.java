package com.moabi.demoeinvoice.helper;

import com.moabi.demoeinvoice.dao.submitdocument.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public class TestHelper {
    public static Invoice generateTestInvoiceObject() {
        /*
         * 1. Description
         * 2. Any pattern or max length need follow
         * 3. Required (R) / Optional (O)
         */
        Invoice invoice =

                // 01: Invoice v 1.0
                new Invoice(
                        "INV12345", // invoice id | N | R (**must be unique, otherwise will return null, false)
                        // issue date of the invoice (must be current date and time) | yyyy-mm-dd | R, hh:mm:ss
                        new Date(),
                        // invoiceTypeCode: identifies document type | 01 | R
                        "01",
                        // documentCurrencyCode: specify currency code | N | R
                        "MYR",
                        new Period(new Date(), new Date(), "Monthly"), // frequency of invoice | N | O
                        new DocumentReference("E12345678912", Optional.empty(), Optional.empty()),  // id: bill reference number: supplier's internal billing |N | O
                        new DocumentReference[]{
                                // for export | N | O / Required where applicable
                                new DocumentReference("E12345678912", Optional.of("CustomsImportForm"), Optional.empty()), // id: reference number of customs forms: declare goods exported | N | O
                                new DocumentReference("ASEAN-Australia-New Zealand FTA (AANZFTA)", Optional.of("FreeTradeAgreement"), Optional.of("Sample Description")),
                                new DocumentReference("E12345678912", Optional.of("K2"), Optional.empty()),
                                new DocumentReference("CIF", Optional.empty(), Optional.empty())
                        },
                        new AccountingParty(
                                //supplier information | N | R
                                new Party(
                                        new IndustryClassificationCode[]{
                                                // MSIC code and name of the supplier's activity | N | R
                                                // MSIC Code: 5-digit numeric code represent supplier's business activity
                                                new IndustryClassificationCode("01111", "Growing of maize")
                                        },
                                        new PartyIdentification[]{
                                                // supplier's identification number | NRIC (12 CHAR) / BRN (20 CHAR) / PASSPORT (12 CHAR) / ARMY  (12 CHAR)| R
                                                // TIN | 14 char | R
                                                new PartyIdentification("PT26881662100", "TIN"),
                                                new PartyIdentification("202004000864", "BRN")
                                        },
                                        new Address(
                                                // address of the supplier | N | R
                                                "Kuala Lumpur",
                                                "50480",
                                                "14",
                                                new String[]{
                                                        "Lot 66", // 1st line | N | R
                                                        "Bangunan Merdeka", // 2nd line | N | O
                                                        "Persiaran Jaya" // 3rd line | N | O
                                                },
                                                new Country("MYS", "ISO3166-1", "6")
                                                // code to identify country | format as in the 'country' | R
                                        ),
                                        new PartyLegalEntity("AmaSeng Software Sdn. Bhd."), // name of supplier | N | R
                                        new Contact("+60-123456789", "general.ams@supplier.com") // contact information of supplier | N | R
                                ),
                                new AccountID[]{
                                        // for export only to validate certified exporter | only dash as special character is allowed | O
                                        new AccountID("CPT-CCN-W-211111-KL-000002", "CertEX")
                                }
                        ),
                        new AccountingParty(
                                //customer's information | N | R
                                new Party(
                                        new IndustryClassificationCode[]{}, // TODO: Skip when empty
                                        new PartyIdentification[]{
                                                // customer's identification number | NRIC / BRN / PASSPORT / ARMY | R
                                                // businesses: Business registration number / malaysian / non-malaysian
                                                new PartyIdentification("C2584563200", "TIN"),
                                                new PartyIdentification("201901234567", "BRN")
                                        },
                                        // buyer's address | follow supplier format | R
                                        // cityname, postalzone, statecode, countrycode (MYS) | N | R
                                        new Address(
                                                "Kuala Lumpur",
                                                "50480",
                                                "14",
                                                new String[]{
                                                        "Lot 66",   // required
                                                        "Bangunan Merdeka", // optional
                                                        "Persiaran Jaya"    // optional
                                                },
                                                new Country("MYS", "ISO3166-1", "6")
                                        ),
                                        // name of recipient | N | R
                                        new PartyLegalEntity("AMS Setia Jaya Sdn. Bhd."),
                                        // contact number | N | R
                                        // email | N | O
                                        new Contact("+60-123456789", "name@buyer.com")
                                ),
                                new AccountID[]{}
                        ),
                        new Delivery(
                                // shipping information of the buyer | N | O / Required where applicable
                                new Party(
                                        new IndustryClassificationCode[]{}, // TODO: Skip when empty
                                        new PartyIdentification[]{
                                                // Shipping recipient TIN | 14 CHAR | O / Required where applicable
                                                new PartyIdentification("C2584563200", "TIN"),
                                                // Shipping recipient registration number | same as other ID | O / Required where applicable
                                                new PartyIdentification("201901234567", "BRN")
                                        },
                                        // address of the recipient | N (standard format; 3 lines) | O / Required where applicable
                                        // cityname, postalzone, statecode, countrycode (MYS) | N | R
                                        new Address(
                                                "Kuala Lumpur",
                                                "50480",
                                                "14",
                                                new String[]{
                                                        "Lot 66",   // required
                                                        "Bangunan Merdeka", // optional
                                                        "Persiaran Jaya"    // optional
                                                },
                                                new Country("MYS", "ISO3166-1", "6")
                                        ),
                                        new PartyLegalEntity("Greenz Sdn. Bhd."),
                                        // contact number | N | R
                                        // email | N | O
                                        new Contact("+60-123456789", "name@buyer.com")
                                ),
                                // details of shipping charge | N | O
                                new Shipment(
                                        "1234",
                                        new FreightAllowanceCharge(
                                                true,
                                                "Service charge",
                                                new BigDecimal("100"),
                                                "MYR"
                                        )
                                )
                        ),
                        // paymentMode; mechanism funds transferred from buyer to supplier | N | O
                        new PaymentMeans(
                                "01",
                                // 01: cash, 02: cheque, 03: transfer, 04: creditCard, 05: debitCard, 06: eWallet, 07: digitalBank, 08: others
                                new FinancialAccount("1234567890123")
                                // id of the supplier's bank account number | N | R
                        ),
                        // payment terms and conditions (timing or method) | N | O
                        new PaymentTerms("Payment method is cash"),
                        // prepayment: prepaid by buyer | N | O
                        new Payment(
                                "E12345678912",// id: billingReference: AdditonalDocumentReference (prepaid)
                                // prepayment reference number: unique identifier to trace payment | N | O
                                new MonetaryAmount(new BigDecimal("1.00"), "MYR"),
                                // prepaid amount | N | 0
                                new Date()
                                // date of prepayment | yyyy-mm-dd & hh:mm:ss | O
                        ),
                        new Charge[]{
                                // charge associated with product | % or specified rate | O
                                // indicator, reason (description) , amount and currency
                                new Charge(
                                        false,
                                        "Sample Description",
                                        Optional.empty(),
                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                ),
                                new Charge(
                                        true,
                                        "Service charge",
                                        Optional.empty(),
                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                )
                        },
                        new TaxTotal(
                                // amount tax | N | R
                                new MonetaryAmount(new BigDecimal("87.63"), "MYR"),
                                new TaxSubTotal[]{
                                        // details of tax | N | O / Required where applicable
                                        new TaxSubTotal(
                                                // details of tax exemption | N | O / Required where applicable
                                                new MonetaryAmount(new BigDecimal("87.63"), "MYR"),
                                                new MonetaryAmount(new BigDecimal("87.63"), "MYR"),
                                                new TaxCategory(
                                                        "E",
                                                        new BigDecimal("6.00"),
                                                        new TaxScheme(
                                                                "OTH",
                                                                "UN/ECE 5153",
                                                                "6"
                                                        )
                                                )
                                        )
                                }
                        ),
                        new LegalMonetaryTotal(
                                // lineExtensionAmount: sum of total payable amount (exc taxes) | N | O
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // taxExclusiveAmount: sum exclude tax | N | R
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // taxInclusiveAmount: sum exclude tax | N | R
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // allowanceTotalAmount: total amount deduct from original price | N | O
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // chargeTotalAmount: total charge associated with product | N | O
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // payableRoundingAmount: rounding amount add to amount | decimal | O
                                new MonetaryAmount(new BigDecimal("0.30"), "MYR"),
                                // payableAmount: total amount incl tax, charge, and rounding amount | N | R
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR")
                        ),
                        new InvoiceLine[]{
                                new InvoiceLine(
                                        "1234",
                                        new QuantityUnit(new BigDecimal("1"), "C62"),
                                        new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                        new Charge[]{
                                                // charge: discount rate/anything | N | O
                                                new Charge(
                                                        false,
                                                        "Sample Description",
                                                        Optional.of(new BigDecimal("0.15")),
                                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                                ),
                                                new Charge(
                                                        true,
                                                        "Sample Description",
                                                        Optional.of(new BigDecimal("0.15")),
                                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                                )
                                        },
                                        new TaxTotal(
                                                new MonetaryAmount(new BigDecimal("1460.50"), "MYR"),
                                                new TaxSubTotal[]{
                                                        // amount exempted from tax | N | O / required where applicable (tax exemption is applicable)
                                                        new TaxSubTotal(
                                                                new MonetaryAmount(new BigDecimal("1460.50"), "MYR"),
                                                                new MonetaryAmount(new BigDecimal("0.00"), "MYR"),
                                                                new TaxCategory(
                                                                        "E",
                                                                        new BigDecimal("6.00"),
                                                                        new TaxScheme(
                                                                                "OTH",
                                                                                "UN/ECE 5153",
                                                                                "6"
                                                                        )
                                                                        // should have TaxExemptReason ("Exempt New Means of Transport")
                                                                )
                                                        )
                                                }
                                        ),
                                        new Item(
                                                // description of product and country | N | R
                                                new ItemClassificationCode[]{
                                                        // product tariff code (only applicable to good) | N | O
                                                        new ItemClassificationCode("12344321", "PTC"),
                                                        // category of product (have more than one) | N | R
                                                        new ItemClassificationCode("12344321", "CLASS")
                                                },
                                                "Laptop Peripherals",
                                                "MYS"
                                        ),
                                        // unit price and currencyCode | N | R
                                        new MonetaryAmount(new BigDecimal("17"), "MYR"),
                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                )
                        }
                );

        return invoice;
    }

    public static CreditNote generateTestInvoiceObject2() {
        /*
         * 1. Description
         * 2. Any pattern or max length need follow
         * 3. Required (R) / Optional (O)
         */
        CreditNote creditNote =

                // 02: Credit Note v 1.0
                new CreditNote(
                        "INV12345", // id: invoice id | N | R
//                        // original eInvoice reference number | N | O / required where applicable (applicable: debitCard, creditCard, refundNote)
//                        "Internal ID",
                        // issue date of the invoice (must be current date and time) | yyyy-mm-dd | R, hh:mm:ss
                        new Date(),
                        // invoiceTypeCode: identifies document type | 02 | R
                        "02",
                        // documentCurrencyCode: specify currency code | N | R
                        "MYR",
                        new Period(new Date(), new Date(), "Monthly"), // frequency of invoice | N | O
                        new DocumentReference("E12345678912", Optional.empty(), Optional.empty()),  // id: bill reference number: supplier's internal billing |N | O
//                        new DocumentReference[]{
//                                // for original einvoice number | N | O / Required where applicable
//                                new DocumentReference("IRBM Unique Identifier Number", Optional.empty(), Optional.empty()),
//                                new DocumentReference("Internal ID", Optional.empty(), Optional.empty())
//                        },
                        new DocumentReference("Internal ID", Optional.empty(), Optional.empty()),
                        new DocumentReference[]{
                                // for export | N | O / Required where applicable
                                new DocumentReference("E12345678912", Optional.of("CustomsImportForm"), Optional.empty()), // id: reference number of customs forms: declare goods exported | N | O
                                new DocumentReference("ASEAN-Australia-New Zealand FTA (AANZFTA)", Optional.of("FreeTradeAgreement"), Optional.of("Sample Description")),
                                new DocumentReference("E12345678912", Optional.of("K2"), Optional.empty()),
                                new DocumentReference("CIF", Optional.empty(), Optional.empty()),
                                new DocumentReference("Internal ID", Optional.empty(), Optional.empty())
                        },
                        new AccountingParty(
                                //supplier information | N | R
                                new Party(
                                        new IndustryClassificationCode[]{
                                                // MSIC code and name of the supplier's activity | N | R
                                                // MSIC Code: 5-digit numeric code represent supplier's business activity
                                                new IndustryClassificationCode("01111", "Growing of maize")
                                        },
                                        new PartyIdentification[]{
                                                // supplier's identification number | NRIC (12 CHAR) / BRN (20 CHAR) / PASSPORT (12 CHAR) / ARMY  (12 CHAR)| R
                                                // TIN | 14 char | R
                                                new PartyIdentification("PT26881662100", "TIN"),
                                                new PartyIdentification("202004000864", "BRN")
                                        },
                                        new Address(
                                                // address of the supplier | N | R
                                                "Kuala Lumpur",
                                                "50480",
                                                "14",
                                                new String[]{
                                                        "Lot 66", // 1st line | N | R
                                                        "Bangunan Merdeka", // 2nd line | N | O
                                                        "Persiaran Jaya" // 3rd line | N | O
                                                },
                                                new Country("MYS", "ISO3166-1", "6")
                                                // code to identify country | format as in the 'country' | R
                                        ),
                                        new PartyLegalEntity("AmaSeng Software Sdn. Bhd."), // name of supplier | N | R
                                        new Contact("+60-123456789", "general.ams@supplier.com") // contact information of supplier | N | R
                                ),
                                new AccountID[]{
                                        // for export only to validate certified exporter | only dash as special character is allowed | O
                                        new AccountID("CPT-CCN-W-211111-KL-000002", "CertEX")
                                }
                        ),
                        new AccountingParty(
                                //customer's information | N | R
                                new Party(
                                        new IndustryClassificationCode[]{}, // TODO: Skip when empty
                                        new PartyIdentification[]{
                                                // customer's identification number | NRIC / BRN / PASSPORT / ARMY | R
                                                // businesses: Business registration number / malaysian / non-malaysian
                                                new PartyIdentification("C2584563200", "TIN"),
                                                new PartyIdentification("202001234567", "BRN")
                                        },
                                        // buyer's address | follow supplier format | R
                                        // cityname, postalzone, statecode, countrycode (MYS) | N | R
                                        new Address(
                                                "Kuala Lumpur",
                                                "50480",
                                                "14",
                                                new String[]{
                                                        "Lot 66",   // required
                                                        "Bangunan Merdeka", // optional
                                                        "Persiaran Jaya"    // optional
                                                },
                                                new Country("MYS", "ISO3166-1", "6")
                                        ),
                                        // name of recipient | N | R
                                        new PartyLegalEntity("AMS Setia Jaya Sdn. Bhd."),
                                        // contact number | N | R
                                        // email | N | O
                                        new Contact("+60-123456789", "name@buyer.com")
                                ),
                                new AccountID[]{}
                        ),
                        new Delivery(
                                // shipping information of the buyer | N | O / Required where applicable
                                new Party(
                                        new IndustryClassificationCode[]{}, // TODO: Skip when empty
                                        new PartyIdentification[]{
                                                // Shipping recipient TIN | 14 CHAR | O / Required where applicable
                                                new PartyIdentification("C2584563200", "TIN"),
                                                // Shipping recipient registration number | same as other ID | O / Required where applicable
                                                new PartyIdentification("202001234567", "BRN")
                                        },
                                        // address of the recipient | N (standard format; 3 lines) | O / Required where applicable
                                        // cityname, postalzone, statecode, countrycode (MYS) | N | R
                                        new Address(
                                                "Kuala Lumpur",
                                                "50480",
                                                "14",
                                                new String[]{
                                                        "Lot 66",   // required
                                                        "Bangunan Merdeka", // optional
                                                        "Persiaran Jaya"    // optional
                                                },
                                                new Country("MYS", "ISO3166-1", "6")
                                        ),
                                        new PartyLegalEntity("Greenz Sdn. Bhd."),
                                        // contact number | N | R
                                        // email | N | O
                                        new Contact("+60-123456789", "name@buyer.com")
                                ),
                                // details of shipping charge | N | O
                                new Shipment(
                                        "1234",
                                        new FreightAllowanceCharge(
                                                true,
                                                "Service charge",
                                                new BigDecimal("100"),
                                                "MYR"
                                        )
                                )
                        ),
                        // paymentMode; mechanism funds transferred from buyer to supplier | N | O
                        new PaymentMeans(
                                "01",
                                // 01: cash, 02: cheque, 03: transfer, 04: creditCard, 05: debitCard, 06: eWallet, 07: digitalBank, 08: others
                                new FinancialAccount("1234567890123")
                                // id of the supplier's bank account number | N | R
                        ),
                        // payment terms and conditions (timing or method) | N | O
                        new PaymentTerms("Payment method is cash"),
                        // prepayment: prepaid by buyer | N | O
                        new Payment(
                                "E12345678912", // id: billingReference: AdditonalDocumentReference (prepaid)
                                // prepayment reference number: unique identifier to trace payment | N | O
                                new MonetaryAmount(new BigDecimal("1.00"), "MYR"),
                                // prepaid amount | N | 0
                                new Date()
                                // date of prepayment | yyyy-mm-dd & hh:mm:ss | O
                        ),
                        new Charge[]{
                                // charge associated with product | % or specified rate | O
                                // indicator, reason (description) , amount and currency
                                new Charge(
                                        false,
                                        "Sample Description",
                                        Optional.empty(),
                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                ),
                                new Charge(
                                        true,
                                        "Service charge",
                                        Optional.empty(),
                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                )
                        },
                        new TaxTotal(
                                // amount tax | N | R
                                new MonetaryAmount(new BigDecimal("87.63"), "MYR"),
                                new TaxSubTotal[]{
                                        // details of tax | N | O / Required where applicable
                                        new TaxSubTotal(
                                                // details of tax exemption | N | O / Required where applicable
                                                new MonetaryAmount(new BigDecimal("87.63"), "MYR"),
                                                new MonetaryAmount(new BigDecimal("87.63"), "MYR"),
                                                new TaxCategory(
                                                        "E",
                                                        new BigDecimal("6.00"),
                                                        new TaxScheme(
                                                                "OTH",
                                                                "UN/ECE 5153",
                                                                "6"
                                                        )
                                                )
                                        )
                                }
                        ),
                        new LegalMonetaryTotal(
                                // lineExtensionAmount: sum of total payable amount (exc taxes) | N | O
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // taxExclusiveAmount: sum exclude tax | N | R
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // taxInclusiveAmount: sum exclude tax | N | R
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // allowanceTotalAmount: total amount deduct from original price | N | O
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // chargeTotalAmount: total charge associated with product | N | O
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // payableRoundingAmount: rounding amount add to amount | decimal | O
                                new MonetaryAmount(new BigDecimal("0.30"), "MYR"),
                                // payableAmount: total amount incl tax, charge, and rounding amount | N | R
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR")
                        ),
                        new InvoiceLine[]{
                                new InvoiceLine(
                                        "1234",
                                        new QuantityUnit(new BigDecimal("1"), "C62"),
                                        new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                        new Charge[]{
                                                // charge: discount rate/anything | N | O
                                                new Charge(
                                                        false,
                                                        "Sample Description",
                                                        Optional.of(new BigDecimal("0.15")),
                                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                                ),
                                                new Charge(
                                                        true,
                                                        "Sample Description",
                                                        Optional.of(new BigDecimal("0.15")),
                                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                                )
                                        },
                                        new TaxTotal(
                                                new MonetaryAmount(new BigDecimal("1460.50"), "MYR"),
                                                new TaxSubTotal[]{
                                                        // amount exempted from tax | N | O / required where applicable (tax exemption is applicable)
                                                        new TaxSubTotal(
                                                                new MonetaryAmount(new BigDecimal("1460.50"), "MYR"),
                                                                new MonetaryAmount(new BigDecimal("0.00"), "MYR"),
                                                                new TaxCategory(
                                                                        "E",
                                                                        new BigDecimal("6.00"),
                                                                        new TaxScheme(
                                                                                "OTH",
                                                                                "UN/ECE 5153",
                                                                                "6"
                                                                        )
                                                                        // should have TaxExemptReason ("Exempt New Means of Transport")
                                                                )
                                                        )
                                                }
                                        ),
                                        new Item(
                                                // description of product and country | N | R
                                                new ItemClassificationCode[]{
                                                        // product tariff code (only applicable to good) | N | O
                                                        new ItemClassificationCode("12344321", "PTC"),
                                                        // category of product (have more than one) | N | R
                                                        new ItemClassificationCode("12344321", "CLASS")
                                                },
                                                "Laptop Peripherals",
                                                "MYS"
                                        ),
                                        // unit price and currencyCode | N | R
                                        new MonetaryAmount(new BigDecimal("17"), "MYR"),
                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                )
                        }
                );

        return creditNote;
    }

    public static CreditNote generateTestInvoiceObject3() {
        /*
         * 1. Description
         * 2. Any pattern or max length need follow
         * 3. Required (R) / Optional (O)
         */
        CreditNote creditNote =

                // 03: Debit Note v 1.0
                new CreditNote(
                        "INV12347", // id: invoice id | N | R
//                        // original eInvoice reference number | N | O / required where applicable (applicable: debitCard, creditCard, refundNote)
//                        "Internal ID",
                        // issue date of the invoice (must be current date and time) | yyyy-mm-dd | R, hh:mm:ss
                        new Date(),
                        // invoiceTypeCode: identifies document type | 03 | R
                        "03",
                        // documentCurrencyCode: specify currency code | N | R
                        "MYR",
                        new Period(new Date(), new Date(), "Monthly"), // frequency of invoice | N | O
                        new DocumentReference("E12345678912", Optional.empty(), Optional.empty()),  // id: bill reference number: supplier's internal billing |N | O
//                        new DocumentReference[]{
//                                // for original einvoice number | N | O / Required where applicable
//                                new DocumentReference("IRBM Unique Identifier Number", Optional.empty(), Optional.empty()),
//                                new DocumentReference("Internal ID", Optional.empty(), Optional.empty())
//                        },
                        new DocumentReference("Internal ID", Optional.empty(), Optional.empty()),
                        new DocumentReference[]{
                                // for export | N | O / Required where applicable
                                new DocumentReference("E12345678912", Optional.of("CustomsImportForm"), Optional.empty()), // id: reference number of customs forms: declare goods exported | N | O
                                new DocumentReference("ASEAN-Australia-New Zealand FTA (AANZFTA)", Optional.of("FreeTradeAgreement"), Optional.of("Sample Description")),
                                new DocumentReference("E12345678912", Optional.of("K2"), Optional.empty()),
                                new DocumentReference("CIF", Optional.empty(), Optional.empty())
                        },
                        new AccountingParty(
                                //supplier information | N | R
                                new Party(
                                        new IndustryClassificationCode[]{
                                                // MSIC code and name of the supplier's activity | N | R
                                                // MSIC Code: 5-digit numeric code represent supplier's business activity
                                                new IndustryClassificationCode("01111", "Growing of maize")
                                        },
                                        new PartyIdentification[]{
                                                // supplier's identification number | NRIC (12 CHAR) / BRN (20 CHAR) / PASSPORT (12 CHAR) / ARMY  (12 CHAR)| R
                                                // TIN | 14 char | R
                                                new PartyIdentification("PT26881662100", "TIN"),
                                                new PartyIdentification("202004000864", "BRN")
                                        },
                                        new Address(
                                                // address of the supplier | N | R
                                                "Kuala Lumpur",
                                                "50480",
                                                "14",
                                                new String[]{
                                                        "Lot 66", // 1st line | N | R
                                                        "Bangunan Merdeka", // 2nd line | N | O
                                                        "Persiaran Jaya" // 3rd line | N | O
                                                },
                                                new Country("MYS", "ISO3166-1", "6")
                                                // code to identify country | format as in the 'country' | R
                                        ),
                                        new PartyLegalEntity("AmaSeng Software Sdn. Bhd."), // name of supplier | N | R
                                        new Contact("+60-123456789", "general.ams@supplier.com") // contact information of supplier | N | R
                                ),
                                new AccountID[]{
                                        // for export only to validate certified exporter | only dash as special character is allowed | O
                                        new AccountID("CPT-CCN-W-211111-KL-000002", "CertEX")
                                }
                        ),
                        new AccountingParty(
                                //customer's information | N | R
                                new Party(
                                        new IndustryClassificationCode[]{}, // TODO: Skip when empty
                                        new PartyIdentification[]{
                                                // customer's identification number | NRIC / BRN / PASSPORT / ARMY | R
                                                // businesses: Business registration number / malaysian / non-malaysian
                                                new PartyIdentification("C2584563200", "TIN"),
                                                new PartyIdentification("202001234567", "BRN")
                                        },
                                        // buyer's address | follow supplier format | R
                                        // cityname, postalzone, statecode, countrycode (MYS) | N | R
                                        new Address(
                                                "Kuala Lumpur",
                                                "50480",
                                                "14",
                                                new String[]{
                                                        "Lot 66",   // required
                                                        "Bangunan Merdeka", // optional
                                                        "Persiaran Jaya"    // optional
                                                },
                                                new Country("MYS", "ISO3166-1", "6")
                                        ),
                                        // name of recipient | N | R
                                        new PartyLegalEntity("AMS Setia Jaya Sdn. Bhd."),
                                        // contact number | N | R
                                        // email | N | O
                                        new Contact("+60-123456789", "name@buyer.com")
                                ),
                                new AccountID[]{}
                        ),
                        new Delivery(
                                // shipping information of the buyer | N | O / Required where applicable
                                new Party(
                                        new IndustryClassificationCode[]{}, // TODO: Skip when empty
                                        new PartyIdentification[]{
                                                // Shipping recipient TIN | 14 CHAR | O / Required where applicable
                                                new PartyIdentification("C2584563200", "TIN"),
                                                // Shipping recipient registration number | same as other ID | O / Required where applicable
                                                new PartyIdentification("202001234567", "BRN")
                                        },
                                        // address of the recipient | N (standard format; 3 lines) | O / Required where applicable
                                        // cityname, postalzone, statecode, countrycode (MYS) | N | R
                                        new Address(
                                                "Kuala Lumpur",
                                                "50480",
                                                "14",
                                                new String[]{
                                                        "Lot 66",   // required
                                                        "Bangunan Merdeka", // optional
                                                        "Persiaran Jaya"    // optional
                                                },
                                                new Country("MYS", "ISO3166-1", "6")
                                        ),
                                        new PartyLegalEntity("Greenz Sdn. Bhd."),
                                        // contact number | N | R
                                        // email | N | O
                                        new Contact("+60-123456789", "name@buyer.com")
                                ),
                                // details of shipping charge | N | O
                                new Shipment(
                                        "1234",
                                        new FreightAllowanceCharge(
                                                true,
                                                "Service charge",
                                                new BigDecimal("100"),
                                                "MYR"
                                        )
                                )
                        ),
                        // paymentMode; mechanism funds transferred from buyer to supplier | N | O
                        new PaymentMeans(
                                "01",
                                // 01: cash, 02: cheque, 03: transfer, 04: creditCard, 05: debitCard, 06: eWallet, 07: digitalBank, 08: others
                                new FinancialAccount("1234567890123")
                                // id of the supplier's bank account number | N | R
                        ),
                        // payment terms and conditions (timing or method) | N | O
                        new PaymentTerms("Payment method is cash"),
                        // prepayment: prepaid by buyer | N | O
                        new Payment(
                                "E12345678912", // id: billingReference: AdditonalDocumentReference (prepaid)
                                // prepayment reference number: unique identifier to trace payment | N | O
                                new MonetaryAmount(new BigDecimal("1.00"), "MYR"),
                                // prepaid amount | N | 0
                                new Date()
                                // date of prepayment | yyyy-mm-dd & hh:mm:ss | O
                        ),
                        new Charge[]{
                                // charge associated with product | % or specified rate | O
                                // indicator, reason (description) , amount and currency
                                new Charge(
                                        false,
                                        "Sample Description",
                                        Optional.empty(),
                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                ),
                                new Charge(
                                        true,
                                        "Service charge",
                                        Optional.empty(),
                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                )
                        },
                        new TaxTotal(
                                // amount tax | N | R
                                new MonetaryAmount(new BigDecimal("87.63"), "MYR"),
                                new TaxSubTotal[]{
                                        // details of tax | N | O / Required where applicable
                                        new TaxSubTotal(
                                                // details of tax exemption | N | O / Required where applicable
                                                new MonetaryAmount(new BigDecimal("87.63"), "MYR"),
                                                new MonetaryAmount(new BigDecimal("87.63"), "MYR"),
                                                new TaxCategory(
                                                        "E",
                                                        new BigDecimal("6.00"),
                                                        new TaxScheme(
                                                                "OTH",
                                                                "UN/ECE 5153",
                                                                "6"
                                                        )
                                                )
                                        )
                                }
                        ),
                        new LegalMonetaryTotal(
                                // lineExtensionAmount: sum of total payable amount (exc taxes) | N | O
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // taxExclusiveAmount: sum exclude tax | N | R
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // taxInclusiveAmount: sum exclude tax | N | R
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // allowanceTotalAmount: total amount deduct from original price | N | O
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // chargeTotalAmount: total charge associated with product | N | O
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // payableRoundingAmount: rounding amount add to amount | decimal | O
                                new MonetaryAmount(new BigDecimal("0.30"), "MYR"),
                                // payableAmount: total amount incl tax, charge, and rounding amount | N | R
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR")
                        ),
                        new InvoiceLine[]{
                                new InvoiceLine(
                                        "1234",
                                        new QuantityUnit(new BigDecimal("1"), "C62"),
                                        new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                        new Charge[]{
                                                // charge: discount rate/anything | N | O
                                                new Charge(
                                                        false,
                                                        "Sample Description",
                                                        Optional.of(new BigDecimal("0.15")),
                                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                                ),
                                                new Charge(
                                                        true,
                                                        "Sample Description",
                                                        Optional.of(new BigDecimal("0.15")),
                                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                                )
                                        },
                                        new TaxTotal(
                                                new MonetaryAmount(new BigDecimal("1460.50"), "MYR"),
                                                new TaxSubTotal[]{
                                                        // amount exempted from tax | N | O / required where applicable (tax exemption is applicable)
                                                        new TaxSubTotal(
                                                                new MonetaryAmount(new BigDecimal("1460.50"), "MYR"),
                                                                new MonetaryAmount(new BigDecimal("0.00"), "MYR"),
                                                                new TaxCategory(
                                                                        "E",
                                                                        new BigDecimal("6.00"),
                                                                        new TaxScheme(
                                                                                "OTH",
                                                                                "UN/ECE 5153",
                                                                                "6"
                                                                        )
                                                                        // should have TaxExemptReason ("Exempt New Means of Transport")
                                                                )
                                                        )
                                                }
                                        ),
                                        new Item(
                                                // description of product and country | N | R
                                                new ItemClassificationCode[]{
                                                        // product tariff code (only applicable to good) | N | O
                                                        new ItemClassificationCode("12344321", "PTC"),
                                                        // category of product (have more than one) | N | R
                                                        new ItemClassificationCode("12344321", "CLASS")
                                                },
                                                "Laptop Peripherals",
                                                "MYS"
                                        ),
                                        // unit price and currencyCode | N | R
                                        new MonetaryAmount(new BigDecimal("17"), "MYR"),
                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                )
                        }
                );

        return creditNote;
    }

    public static CreditNote generateTestInvoiceObject4() {
        /*
         * 1. Description
         * 2. Any pattern or max length need follow
         * 3. Required (R) / Optional (O)
         */
        CreditNote creditNote =

                // 04: Refund Note v 1.0
                new CreditNote(
                        "INV12348", // id: invoice id | N | R
//                        // original eInvoice reference number | N | O / required where applicable (applicable: debitCard, creditCard, refundNote)
//                        "Internal ID",
                        // issue date of the invoice (must be current date and time) | yyyy-mm-dd | R, hh:mm:ss
                        new Date(),
                        // invoiceTypeCode: identifies document type | 04 | R
                        "04",
                        // documentCurrencyCode: specify currency code | N | R
                        "MYR",
                        new Period(new Date(), new Date(), "Monthly"), // frequency of invoice | N | O
                        new DocumentReference("E12345678912", Optional.empty(), Optional.empty()),  // id: bill reference number: supplier's internal billing |N | O
//                        new DocumentReference[]{    // InvoiceDocumentReference
//                                // for original einvoice number | N | O / Required where applicable
//                                new DocumentReference("IRBM Unique Identifier Number", Optional.empty(), Optional.empty()),
//                                new DocumentReference("Internal ID", Optional.empty(), Optional.empty())
//                        },
                        new DocumentReference("Internal ID", Optional.empty(), Optional.empty()),
                        new DocumentReference[]{
                                // for export | N | O / Required where applicable
                                new DocumentReference("E12345678912", Optional.of("CustomsImportForm"), Optional.empty()), // id: reference number of customs forms: declare goods exported | N | O
                                new DocumentReference("ASEAN-Australia-New Zealand FTA (AANZFTA)", Optional.of("FreeTradeAgreement"), Optional.of("Sample Description")),
                                new DocumentReference("E12345678912", Optional.of("K2"), Optional.empty()),
                                new DocumentReference("CIF", Optional.empty(), Optional.empty())
                        },
                        new AccountingParty(
                                //supplier information | N | R
                                new Party(
                                        new IndustryClassificationCode[]{
                                                // MSIC code and name of the supplier's activity | N | R
                                                // MSIC Code: 5-digit numeric code represent supplier's business activity
                                                new IndustryClassificationCode("01111", "Growing of maize")
                                        },
                                        new PartyIdentification[]{
                                                // supplier's identification number | NRIC (12 CHAR) / BRN (20 CHAR) / PASSPORT (12 CHAR) / ARMY  (12 CHAR)| R
                                                // TIN | 14 char | R
                                                new PartyIdentification("PT26881662100", "TIN"),
                                                new PartyIdentification("202004000864", "BRN")
                                        },
                                        new Address(
                                                // address of the supplier | N | R
                                                "Kuala Lumpur",
                                                "50480",
                                                "14",
                                                new String[]{
                                                        "Lot 66", // 1st line | N | R
                                                        "Bangunan Merdeka", // 2nd line | N | O
                                                        "Persiaran Jaya" // 3rd line | N | O
                                                },
                                                new Country("MYS", "ISO3166-1", "6")
                                                // code to identify country | format as in the 'country' | R
                                        ),
                                        new PartyLegalEntity("AmaSeng Software Sdn. Bhd."), // name of supplier | N | R
                                        new Contact("+60-123456789", "general.ams@supplier.com") // contact information of supplier | N | R
                                ),
                                new AccountID[]{
                                        // for export only to validate certified exporter | only dash as special character is allowed | O
                                        new AccountID("CPT-CCN-W-211111-KL-000002", "CertEX")
                                }
                        ),
                        new AccountingParty(
                                //customer's information | N | R
                                new Party(
                                        new IndustryClassificationCode[]{}, // TODO: Skip when empty
                                        new PartyIdentification[]{
                                                // customer's identification number | NRIC / BRN / PASSPORT / ARMY | R
                                                // businesses: Business registration number / malaysian / non-malaysian
                                                new PartyIdentification("C2584563200", "TIN"),
                                                new PartyIdentification("202001234567", "BRN")
                                        },
                                        // buyer's address | follow supplier format | R
                                        // cityname, postalzone, statecode, countrycode (MYS) | N | R
                                        new Address(
                                                "Kuala Lumpur",
                                                "50480",
                                                "14",
                                                new String[]{
                                                        "Lot 66",   // required
                                                        "Bangunan Merdeka", // optional
                                                        "Persiaran Jaya"    // optional
                                                },
                                                new Country("MYS", "ISO3166-1", "6")
                                        ),
                                        // name of recipient | N | R
                                        new PartyLegalEntity("AMS Setia Jaya Sdn. Bhd."),
                                        // contact number | N | R
                                        // email | N | O
                                        new Contact("+60-123456789", "name@buyer.com")
                                ),
                                new AccountID[]{}
                        ),
                        new Delivery(
                                // shipping information of the buyer | N | O / Required where applicable
                                new Party(
                                        new IndustryClassificationCode[]{}, // TODO: Skip when empty
                                        new PartyIdentification[]{
                                                // Shipping recipient TIN | 14 CHAR | O / Required where applicable
                                                new PartyIdentification("C2584563200", "TIN"),
                                                // Shipping recipient registration number | same as other ID | O / Required where applicable
                                                new PartyIdentification("202001234567", "BRN")
                                        },
                                        // address of the recipient | N (standard format; 3 lines) | O / Required where applicable
                                        // cityname, postalzone, statecode, countrycode (MYS) | N | R
                                        new Address(
                                                "Kuala Lumpur",
                                                "50480",
                                                "14",
                                                new String[]{
                                                        "Lot 66",   // required
                                                        "Bangunan Merdeka", // optional
                                                        "Persiaran Jaya"    // optional
                                                },
                                                new Country("MYS", "ISO3166-1", "6")
                                        ),
                                        new PartyLegalEntity("Greenz Sdn. Bhd."),
                                        // contact number | N | R
                                        // email | N | O
                                        new Contact("+60-123456789", "name@buyer.com")
                                ),
                                // details of shipping charge | N | O
                                new Shipment(
                                        "1234",
                                        new FreightAllowanceCharge(
                                                true,
                                                "Service charge",
                                                new BigDecimal("100"),
                                                "MYR"
                                        )
                                )
                        ),
                        // paymentMode; mechanism funds transferred from buyer to supplier | N | O
                        new PaymentMeans(
                                "01",
                                // 01: cash, 02: cheque, 03: transfer, 04: creditCard, 05: debitCard, 06: eWallet, 07: digitalBank, 08: others
                                new FinancialAccount("1234567890123")
                                // id of the supplier's bank account number | N | R
                        ),
                        // payment terms and conditions (timing or method) | N | O
                        new PaymentTerms("Payment method is cash"),
                        // prepayment: prepaid by buyer | N | O
                        new Payment(
                                "E12345678912", // id: billingReference: AdditonalDocumentReference (prepaid)
                                // prepayment reference number: unique identifier to trace payment | N | O
                                new MonetaryAmount(new BigDecimal("1.00"), "MYR"),
                                // prepaid amount | N | 0
                                new Date()
                                // date of prepayment | yyyy-mm-dd & hh:mm:ss | O
                        ),
                        new Charge[]{
                                // charge associated with product | % or specified rate | O
                                // indicator, reason (description) , amount and currency
                                new Charge(
                                        false,
                                        "Sample Description",
                                        Optional.empty(),
                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                ),
                                new Charge(
                                        true,
                                        "Service charge",
                                        Optional.empty(),
                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                )
                        },
                        new TaxTotal(
                                // amount tax | N | R
                                new MonetaryAmount(new BigDecimal("87.63"), "MYR"),
                                new TaxSubTotal[]{
                                        // details of tax | N | O / Required where applicable
                                        new TaxSubTotal(
                                                // details of tax exemption | N | O / Required where applicable
                                                new MonetaryAmount(new BigDecimal("87.63"), "MYR"),
                                                new MonetaryAmount(new BigDecimal("87.63"), "MYR"),
                                                new TaxCategory(
                                                        "E",
                                                        new BigDecimal("6.00"),
                                                        new TaxScheme(
                                                                "OTH",
                                                                "UN/ECE 5153",
                                                                "6"
                                                        )
                                                )
                                        )
                                }
                        ),
                        new LegalMonetaryTotal(
                                // lineExtensionAmount: sum of total payable amount (exc taxes) | N | O
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // taxExclusiveAmount: sum exclude tax | N | R
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // taxInclusiveAmount: sum exclude tax | N | R
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // allowanceTotalAmount: total amount deduct from original price | N | O
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // chargeTotalAmount: total charge associated with product | N | O
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                // payableRoundingAmount: rounding amount add to amount | decimal | O
                                new MonetaryAmount(new BigDecimal("0.30"), "MYR"),
                                // payableAmount: total amount incl tax, charge, and rounding amount | N | R
                                new MonetaryAmount(new BigDecimal("1436.50"), "MYR")
                        ),
                        new InvoiceLine[]{
                                new InvoiceLine(
                                        "1234",
                                        new QuantityUnit(new BigDecimal("1"), "C62"),
                                        new MonetaryAmount(new BigDecimal("1436.50"), "MYR"),
                                        new Charge[]{
                                                // charge: discount rate/anything | N | O
                                                new Charge(
                                                        false,
                                                        "Sample Description",
                                                        Optional.of(new BigDecimal("0.15")),
                                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                                ),
                                                new Charge(
                                                        true,
                                                        "Sample Description",
                                                        Optional.of(new BigDecimal("0.15")),
                                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                                )
                                        },
                                        new TaxTotal(
                                                new MonetaryAmount(new BigDecimal("1460.50"), "MYR"),
                                                new TaxSubTotal[]{
                                                        // amount exempted from tax | N | O / required where applicable (tax exemption is applicable)
                                                        new TaxSubTotal(
                                                                new MonetaryAmount(new BigDecimal("1460.50"), "MYR"),
                                                                new MonetaryAmount(new BigDecimal("0.00"), "MYR"),
                                                                new TaxCategory(
                                                                        "E",
                                                                        new BigDecimal("6.00"),
                                                                        new TaxScheme(
                                                                                "OTH",
                                                                                "UN/ECE 5153",
                                                                                "6"
                                                                        )
                                                                        // should have TaxExemptReason ("Exempt New Means of Transport")
                                                                )
                                                        )
                                                }
                                        ),
                                        new Item(
                                                // description of product and country | N | R
                                                new ItemClassificationCode[]{
                                                        // product tariff code (only applicable to good) | N | O
                                                        new ItemClassificationCode("12344321", "PTC"),
                                                        // category of product (have more than one) | N | R
                                                        new ItemClassificationCode("12344321", "CLASS")
                                                },
                                                "Laptop Peripherals",
                                                "MYS"
                                        ),
                                        // unit price and currencyCode | N | R
                                        new MonetaryAmount(new BigDecimal("17"), "MYR"),
                                        new MonetaryAmount(new BigDecimal("100"), "MYR")
                                )
                        }
                );

        return creditNote;
    }
}
