package com.newbie.Spring_Newbie.User.dao;

import com.newbie.Spring_Newbie.User.sqlService.EmbeddedDbSqlRegistry;
import com.newbie.Spring_Newbie.User.sqlService.SqlUpdateFailureException;
import com.newbie.Spring_Newbie.User.sqlService.UpdatetableSqlRegistry;
import org.junit.After;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;
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

    @Test
    public void transactionalUpdate(){
        checkFindResult("SQL1","SQL2","SQL3");

        Map<String, String> sqlmap = new HashMap<String, String>();
        sqlmap.put("KEY1","Modified1");
        sqlmap.put("KEY9999!@#$","Modified9999");

        try{
            sqlRegistry.updateSql(sqlmap);
            fail();
        }
        catch (SqlUpdateFailureException e) {}
        checkFindResult("SQL1","SQL2","SQL3");
    }

    @After
    public void tearDown(){
        db.shutdown();;
    }
}
