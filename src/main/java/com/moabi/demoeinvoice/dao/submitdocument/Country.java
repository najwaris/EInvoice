
package com.moabi.demoeinvoice.dao.submitdocument;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Data
public class Country {
    private String identificationCode;
    private String listID;
    private String listAgencyID;

    public Country(String identificationCode, String listID, String listAgencyID) {
        this.identificationCode = identificationCode;
        this.listID = listID;
        this.listAgencyID = listAgencyID;
    }

    public Map<Object, Object> toMap() {
        return new LinkedHashMap<Object, Object>() {{
            put("IdentificationCode", new ArrayList<Object>() {{
                add(new LinkedHashMap<Object, Object>() {{
                    put("_", identificationCode);
                    put("listID", listID);
                    put("listAgencyID", listAgencyID);
                }});
            }});
        }};
    }
}
