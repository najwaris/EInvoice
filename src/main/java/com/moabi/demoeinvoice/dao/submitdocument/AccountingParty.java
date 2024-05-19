
package com.moabi.demoeinvoice.dao.submitdocument;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Data
public class AccountingParty {

    private Party party;
    private AccountID[] additionalAccountID;

    public AccountingParty(Party party, AccountID[] additionalAccountID) {
        this.party = party;
        this.additionalAccountID = additionalAccountID;
    }

    public Party getParty() {
        return party;
    }

    public AccountID[] getAdditionalAccountID() {
        return additionalAccountID;
    }

    public Map<Object, Object> toMap() {
        return new LinkedHashMap<Object, Object>() {{
            if (additionalAccountID != null && additionalAccountID.length > 0)
                put("AdditionalAccountID", Arrays.stream(additionalAccountID).map(AccountID::toMap).toArray());
            put("Party", new ArrayList<Object>() {{
                add(party.toMap());
            }});
        }};
    }
}
