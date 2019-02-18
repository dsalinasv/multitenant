package server.hibernate;

import server.util.TenantContextHolder;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl
        implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        return TenantContextHolder.getTenant();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
