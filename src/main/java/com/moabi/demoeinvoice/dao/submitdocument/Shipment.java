/*
 * Copyright 2011-2024 AmaSeng Software Sdn. Bhd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moabi.demoeinvoice.dao.submitdocument;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Data
public class Shipment {
    private String id;
    private FreightAllowanceCharge freightAllowanceCharge;

    public Shipment(String id, FreightAllowanceCharge freightAllowanceCharge) {
        this.id = id;
        this.freightAllowanceCharge = freightAllowanceCharge;
    }

    public String getId() {
        return id;
    }

    public Map<Object, Object> toMap() {
        return new LinkedHashMap<Object, Object>() {{
            put("ID", new ArrayList<Object>() {{ add(new LinkedHashMap<Object, Object>() {{ put("_", id); }}); }});
            put("FreightAllowanceCharge", new ArrayList<Object>() {{ add(freightAllowanceCharge.toMap()); }});
        }};
    }
}
