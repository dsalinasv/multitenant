package server.util;

public class TenantContextHolder {

    private static final String DEFAULT_TENANT_ID = "HEROKU_POSTGRESQL_TEAL_URL";

    private static final ThreadLocal<String> CONTEXT
            = new ThreadLocal<String>() {
        @Override
        public String initialValue() {
            return DEFAULT_TENANT_ID;
        }
    };

    public static void setTenantId(String tenant) {
        CONTEXT.set(tenant);
    }

    public static String getTenant() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

}
