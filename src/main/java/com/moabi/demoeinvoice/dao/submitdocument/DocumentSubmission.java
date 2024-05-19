package com.moabi.demoeinvoice.dao.submitdocument;

import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Data
public class DocumentSubmission {

    private Document[] documents;

    public DocumentSubmission(Document[] documents) {
        this.documents = documents;
    }

    public Document[] getDocuments() {
        return documents;
    }

    public Map<Object, Object> toMap() {
        return new LinkedHashMap<Object, Object>() {{
            put("documents", Arrays.stream(documents).map(Document::toMap).toArray());
        }};
    }

}
