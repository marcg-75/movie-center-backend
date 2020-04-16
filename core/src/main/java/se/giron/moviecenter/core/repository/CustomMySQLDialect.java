package se.giron.moviecenter.core.repository;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class CustomMySQLDialect extends MySQL5Dialect {

    public CustomMySQLDialect() {
        super();

        String params = "?1";
        for (int i = 1; i < 7; i++) {
            registerFunction("match" + i, new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "match (" + params + ") against (?" + (i + 1)+ " in boolean mode)"));
            params += ",?" + (i + 1);
        }
    }
}
