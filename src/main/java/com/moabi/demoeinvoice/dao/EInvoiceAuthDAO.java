package com.moabi.demoeinvoice.dao;

import lombok.Data;

@Data
public class EInvoiceAuthDAO {
    private String client_id;
    private String client_secret;
    private String grant_type;
    private String scope;

    public EInvoiceAuthDAO(String client_id, String client_secret, String grant_type, String scope) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.grant_type = grant_type;
        this.scope = scope;
    }
}
