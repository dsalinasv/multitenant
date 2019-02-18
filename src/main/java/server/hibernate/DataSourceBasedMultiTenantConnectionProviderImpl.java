package server.hibernate;

import server.util.DataSourceUtil;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.hibernate.service.spi.Stoppable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl
        extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl
        implements Stoppable {

    @Autowired
    private DataSource defaultDataSource;
    private static final long serialVersionUID = 1L;
    private Map<String, DataSource> dataSourceMap
            = new ConcurrentHashMap<>();

    @Override
    protected DataSource selectAnyDataSource() {
        return defaultDataSource;
    }

    @Override
    protected DataSource selectDataSource(String tenantId) {
        if (!dataSourceMap.containsKey(tenantId) && dataSourceMap.get(tenantId) != null) {
            dataSourceMap.put(tenantId, DataSourceUtil
                    .createAndConfigureDataSource(tenantId));
        }
        return dataSourceMap.get(tenantId);
    }

    @Override
    public void stop() {
        if (dataSourceMap != null) {
            dataSourceMap.clear();
            dataSourceMap = null;
        }
    }

}
