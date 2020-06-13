package com.newbie.Spring_Newbie.User.dao;

import com.newbie.Spring_Newbie.User.sqlService.EmbeddedDbSqlRegistry;
import com.newbie.Spring_Newbie.User.sqlService.UpdatetableSqlRegistry;
import org.junit.After;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

public class EmbeddedDbSqlRegistryTest extends AbstractUpdatebleSqlRegistryTest{
    EmbeddedDatabase db;

    protected UpdatetableSqlRegistry createUpdateableSqlRegistry(){
        db = new EmbeddedDatabaseBuilder()
                .setType(HSQL).addScript(
                        "classpath:com/newbie/Spring_Newbie/User/sqlService/sqlRegistrySchema.sql"
                ).build();

        EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
        embeddedDbSqlRegistry.setDataSource(db);

        return embeddedDbSqlRegistry;
    }

    @After
    public void tearDown(){
        db.shutdown();;
    }
}
