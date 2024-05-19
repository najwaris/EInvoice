
package com.moabi.demoeinvoice.dao.submitdocument;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Data
public class LegalMonetaryTotal {
    private MonetaryAmount lineExtensionAmount;
    private MonetaryAmount taxExclusiveAmount;
    private MonetaryAmount taxInclusiveAmount;
    private MonetaryAmount allowanceTotalAmount;
    private MonetaryAmount chargeTotalAmount;
    private MonetaryAmount payableRoundingAmount;
    private MonetaryAmount payableAmount;

    public LegalMonetaryTotal(MonetaryAmount lineExtensionAmount, MonetaryAmount taxExclusiveAmount, MonetaryAmount taxInclusiveAmount, MonetaryAmount allowanceTotalAmount, MonetaryAmount chargeTotalAmount, MonetaryAmount payableRoundingAmount, MonetaryAmount payableAmount) {
        this.lineExtensionAmount = lineExtensionAmount;
        this.taxExclusiveAmount = taxExclusiveAmount;
        this.taxInclusiveAmount = taxInclusiveAmount;
        this.allowanceTotalAmount = allowanceTotalAmount;
        this.chargeTotalAmount = chargeTotalAmount;
        this.payableRoundingAmount = payableRoundingAmount;
        this.payableAmount = payableAmount;
    }

    public Map<Object, Object> toMap() {
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("LineExtensionAmount", new ArrayList<Object>() {{ add(lineExtensionAmount.toMap()); }});
        map.put("TaxExclusiveAmount", new ArrayList<Object>() {{ add(taxExclusiveAmount.toMap()); }});
        map.put("TaxInclusiveAmount", new ArrayList<Object>() {{ add(taxInclusiveAmount.toMap()); }});
        map.put("AllowanceTotalAmount", new ArrayList<Object>() {{ add(allowanceTotalAmount.toMap()); }});
        map.put("ChargeTotalAmount", new ArrayList<Object>() {{ add(chargeTotalAmount.toMap()); }});
        map.put("PayableRoundingAmount", new ArrayList<Object>() {{ add(payableRoundingAmount.toMap()); }});
        map.put("PayableAmount", new ArrayList<Object>() {{ add(payableAmount.toMap()); }});
        return map;
    }
}
