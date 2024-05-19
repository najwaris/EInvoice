package com.moabi.demoeinvoice.dao.submitdocument;

import lombok.Data;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Data
public class Document {
    private String format;
    private String document;
    private String documentHash;
    private String codeNumber;

    public Document(String format, String document, String documentHash, String codeNumber) {
        this.format = format;
        this.document = document;
        this.documentHash = documentHash;
        this.codeNumber = codeNumber;
    }

    public String getFormat() {
        return format;
    }

    public String getDocument() {
        return document;
    }

    public String getDocumentHash() {
        return documentHash;
    }

    public String getCodeNumber() {
        return codeNumber;
    }

    public Map<Object, Object> toMap() {
        return new LinkedHashMap<Object, Object>() {{
            put("format", format);
            put("document", document);
            put("documentHash", documentHash);
            put("codeNumber", codeNumber);
        }};
    }

    public Document fromInvoice(Invoice invoice) {
        return new Document("JSON", invoice.toMap().toString(), "1234567890", "1234567890");
    }
}
