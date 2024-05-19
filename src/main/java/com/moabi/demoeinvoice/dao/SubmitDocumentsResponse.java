package com.moabi.demoeinvoice.dao;

import com.moabi.demoeinvoice.dao.submitdocument.Document;
import lombok.Data;

@Data
public class SubmitDocumentsResponse {

    private String submissionUUID;
    private Document[] acceptedDocuments;
    private Document[] rejectedDocuments;

}
