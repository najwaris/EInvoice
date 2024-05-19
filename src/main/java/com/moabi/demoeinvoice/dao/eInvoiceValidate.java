package com.moabi.demoeinvoice.dao;

import lombok.Data;

import javax.management.Query;

@Data
public class eInvoiceValidate {
    private String submissionUID;
    private Query idType;
    private Query idValue;
}
