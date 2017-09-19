package com.intelliment.control;

import com.intelliment.control.exception.NotAllowedException;
import com.intelliment.entity.AclEntry;
import com.intelliment.entity.Request;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

public class AclServiceImpl implements AclService {

    final Collection<AclEntry> policies;

    public AclServiceImpl(AclLoader loader) {
        this.policies = loader.sources();
    }

    @Override
    public AclEntry isAllowed(Request request) throws NotAllowedException {
        for (AclEntry policy : policies) {
            if(policy.matches(request)) return policy;
        }
        throw new NotAllowedException("request not allowed");
    }

    @Override
    public Collection<AclEntry> acl() {
        return policies;
    }

    @Override
    public AclEntry get(int id) {
        Optional<AclEntry> entry = policies.stream().filter(e -> e.id == id).findFirst();
        return entry.orElseThrow(NoSuchElementException::new);
    }

}
