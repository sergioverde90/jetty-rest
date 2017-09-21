package com.intelliment;

import com.intelliment.control.AclFileLoader;
import com.intelliment.control.AclLoader;
import com.intelliment.control.SubnetUtilsAnalyzer;
import com.intelliment.entity.AclEntry;
import com.intelliment.entity.AclEntryBuilder;
import com.intelliment.entity.Constants;
import com.intelliment.entity.Protocol;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <h2>This decorator has been created for testing purpose only.</h2>
 *
 * <p>
 * The decorated behaviour of this class is to eliminate the "any allowed"
 * acl entries to be able to test unmatched cases.
 * </p>
 */
public class DiscardAnyAllowedLoader implements AclLoader {

    private AclFileLoader decorator;

    private static final AclEntry anyAllow = new AclEntryBuilder(new SubnetUtilsAnalyzer())
            .source(Constants.OPEN_WORLD_ADDRESS)
            .destination(Constants.OPEN_WORLD_ADDRESS)
            .protocol(Protocol.valueOf(Constants.OPEN_WORLD_LABEL))
            .action(AclEntry.ActionType.ALLOW)
            .build();

    public DiscardAnyAllowedLoader(AclFileLoader decorator) {
        this.decorator = decorator;
    }

    @Override
    public List<AclEntry> sources() {
        List<AclEntry> sources = decorator.sources();
        return sources.stream()
                .filter(this::isNotAnyAllowed)
                .collect(Collectors.toList());
    }

    private boolean isNotAnyAllowed(AclEntry el) {
        return !el.equals(anyAllow);
    }
}
