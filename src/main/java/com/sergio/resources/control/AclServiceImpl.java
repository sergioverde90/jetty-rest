package com.sergio.resources.control;

import com.sergio.resources.entity.AclEntry;
import com.sergio.resources.entity.Request;

import java.util.Collection;

public class AclServiceImpl implements AclService {

    final Collection<AclEntry> policies;

    public AclServiceImpl(AclLoader loader) {
        this.policies = loader.readSources();
    }

    @Override
    public boolean isAllowed(Request request) {
        for (AclEntry policy : policies) {
            if(policy.matches(request)) return true;
        }
        return false;
    }
}
