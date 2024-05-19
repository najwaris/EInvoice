
package com.moabi.demoeinvoice.dao.submitdocument;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Data
public class Delivery {
    private Party deliveryParty;
    private Shipment shipment;

    public Delivery(Party deliveryParty, Shipment shipment) {
        this.deliveryParty = deliveryParty;
        this.shipment = shipment;
    }

    public Map<Object, Object> toMap() {
        return new LinkedHashMap<Object, Object>() {{
            put("DeliveryParty", new ArrayList<Object>() {{ add(deliveryParty.toMap()); }});
            put("Shipment", new ArrayList<Object>() {{ add(shipment.toMap()); }});
        }};
    }

}