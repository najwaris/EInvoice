
package com.moabi.demoeinvoice.dao.submitdocument;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Data
public class PartyLegalEntity {
    private String registrationName;

    public PartyLegalEntity(String registrationName) {
        this.registrationName = registrationName;
    }

    public Map<Object, Object> toMap() {
        return new LinkedHashMap<Object, Object>() {{
            put("RegistrationName", new ArrayList<Object>() {{
                add(new LinkedHashMap<Object, Object>() {{
                    put("_", registrationName);
                }});
            }});
        }};
    }
}
