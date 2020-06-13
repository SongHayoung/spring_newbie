package com.newbie.Spring_Newbie.User.dao;

import com.newbie.Spring_Newbie.User.sqlService.ConcurrentHashMapSqlRegistry;
import com.newbie.Spring_Newbie.User.sqlService.SqlNotFoundException;
import com.newbie.Spring_Newbie.User.sqlService.SqlUpdateFailureException;
import com.newbie.Spring_Newbie.User.sqlService.UpdatetableSqlRegistry;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class AbstractUpdatebleSqlRegistryTest {
    UpdatetableSqlRegistry sqlRegistry;

    @Before
    public void setUp(){
        sqlRegistry = createUpdateableSqlRegistry();
        sqlRegistry.registerSql("KEY1","SQL1");
        sqlRegistry.registerSql("KEY2","SQL2");
        sqlRegistry.registerSql("KEY3","SQL3");
    }

    abstract protected UpdatetableSqlRegistry createUpdateableSqlRegistry();

    @Test
    public void find(){
        checkFindResult("SQL1","SQL2","SQL3");
    }

    protected void checkFindResult(String expected1, String expected2, String expected3){
        assertThat(sqlRegistry.findSql("KEY1"),is(expected1));
        assertThat(sqlRegistry.findSql("KEY2"),is(expected2));
        assertThat(sqlRegistry.findSql("KEY3"),is(expected3));
    }

    @Test(expected= SqlNotFoundException.class)
    public void unknownKey(){
        sqlRegistry.findSql("SQL9999!@#$");
    }

    @Test
    public void updateSingle(){
        sqlRegistry.updateSql("KEY2","Modified2");
        checkFindResult("SQL1","Modified2","SQL3");
    }

    @Test
    public void updateMulti(){
        Map<String, String> sqlmap = new HashMap<String, String>();
        sqlmap.put("KEY1","Modified1");
        sqlmap.put("KEY3","Modified3");

        sqlRegistry.updateSql(sqlmap);
        checkFindResult("Modified1","SQL2","Modified3");
    }

    @Test(expected= SqlUpdateFailureException.class)
    public void updateWithNotExistingKey(){
        sqlRegistry.updateSql("SQL9999!@#$","Modified2");
    }
}
