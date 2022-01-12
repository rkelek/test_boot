package com.test.project.config;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL10Dialect;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class CustomPostgreSQLDialect extends PostgreSQL10Dialect {

    public CustomPostgreSQLDialect() {
        super();
        registerHibernateType(Types.OTHER, StringArrayType.class.getName());
        registerHibernateType(Types.OTHER, IntArrayType.class.getName());
        registerHibernateType(Types.OTHER, JsonStringType.class.getName());
        registerHibernateType(Types.OTHER, JsonBinaryType.class.getName());
        registerHibernateType(Types.OTHER, JsonNodeBinaryType.class.getName());
        registerHibernateType(Types.OTHER, JsonNodeStringType.class.getName());


        // 사용자 정의함수
        this.registerFunction("fn_rentdtbetween", new SQLFunctionTemplate(StandardBasicTypes.STRING, "fn_rentdtbetween(?1)"));
        this.registerFunction("fn_exist_stay", new SQLFunctionTemplate(StandardBasicTypes.STRING, "fn_exist_stay(?1)"));
        this.registerFunction("fn_exist_rentcar", new SQLFunctionTemplate(StandardBasicTypes.STRING, "fn_exist_rentcar(?1)"));
        this.registerFunction("fn_exist_private", new SQLFunctionTemplate(StandardBasicTypes.STRING, "fn_exist_private(?1)"));
        this.registerFunction("fn_exist_ticket", new SQLFunctionTemplate(StandardBasicTypes.STRING, "fn_exist_ticket(?1)"));

    }
}