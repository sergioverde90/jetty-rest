package com.intelliment.control;

import com.intelliment.entity.AclEntry;
import com.intelliment.entity.Request;

import java.util.Collection;

class AclServiceImpl implements AclService {

    final Collection<AclEntry> policies;

    AclServiceImpl(AclLoader loader) {
        this.policies = loader.sources();
    }

    @Override
    public boolean isAllowed(Request request) {
        for (AclEntry policy : policies) {
            if(policy.matches(request)) return true;
        }
        return false;
    }

    @Override
    public Collection<AclEntry> acl() {
        return policies;
    }

    @Override
    public AclEntry get(int id) {
        return null;
    }

}
