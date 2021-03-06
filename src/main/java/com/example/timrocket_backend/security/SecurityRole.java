package com.example.timrocket_backend.security;

import com.google.common.collect.Lists;

import java.util.*;

import static com.example.timrocket_backend.security.Feature.*;

public enum SecurityRole {
    ADMIN(GET_USER_INFORMATION, UPDATE_PROFILE, GET_COACH_INFORMATION),
    COACH(GET_USER_INFORMATION, UPDATE_PROFILE, GET_COACH_INFORMATION),
    COACHEE(GET_USER_INFORMATION, UPDATE_PROFILE, GET_COACH_INFORMATION, BECOME_A_COACH);


    private final List<Feature> features;

    SecurityRole(Feature... features) {
        this.features = Lists.newArrayList(features);
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public String getRoleName() {
        String lowercaseName = this.name().toLowerCase();
        return lowercaseName.substring(0, 1).toUpperCase() + lowercaseName.substring(1);
    }

    public static SecurityRole getByName(String name) {
        for(SecurityRole role : SecurityRole.values()) {
            if(name.equalsIgnoreCase(role.getRoleName())) {
                return role;
            }
        }
        return null;
    }

    public static class RoleComparator implements Comparator<SecurityRole> {
        public int compare(SecurityRole o1, SecurityRole o2) {
            return -o1.getRoleName().toLowerCase().compareTo(o2.getRoleName().toLowerCase());
        }
    }
}


