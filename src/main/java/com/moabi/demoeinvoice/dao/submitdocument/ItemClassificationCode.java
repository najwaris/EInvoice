
package com.moabi.demoeinvoice.dao.submitdocument;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Data
public class ItemClassificationCode {
    private String id;
    private String listID;

    public ItemClassificationCode(String id, String listID) {
        this.id = id;
        this.listID = listID;
    }

    public Map<Object, Object> toMap() {
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("ItemClassificationCode", new ArrayList<Object>() {{ add(new LinkedHashMap<Object, Object>() {{
            put("_", id);
            put("listID", listID);
        }}); }});
        return map;
    }
}
