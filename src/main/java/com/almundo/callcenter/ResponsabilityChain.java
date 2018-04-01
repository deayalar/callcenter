package com.almundo.callcenter;

import java.util.HashMap;
import java.util.Map;

public class ResponsabilityChain {

    private final Map<String, String> roleSequence = new HashMap<>();
    public final static String FIRST_ROLE_IN_CHAIN = Constants.OPERATOR_KEY;

    public ResponsabilityChain(){
        roleSequence.put(Constants.OPERATOR_KEY, Constants.SUPERVISOR_KEY);
        roleSequence.put(Constants.SUPERVISOR_KEY, Constants.DIRECTOR_KEY);
        roleSequence.put(Constants.DIRECTOR_KEY, Constants.OPERATOR_KEY);
    };

    public String getNextRoleInChain(String currentRole) {
        return roleSequence.get(currentRole);
    }
}
