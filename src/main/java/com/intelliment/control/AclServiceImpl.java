package com.intelliment.control;

import com.intelliment.entity.AclEntry;
import com.intelliment.entity.Request;

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
