package com.newbie.Spring_Newbie.User.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

public class EmbeddedDbTest {
    EmbeddedDatabase db;
    SimpleJdbcTemplate template;

    @Before
    public void setUp(){
        db = new EmbeddedDatabaseBuilder()
                .setType(HSQL)
                .addScript("classpath:/schema.sql")
                .addScript("classpath:/data.sql")
                .build();

        template = new SimpleJdbcTemplate(db);
    }

    @After
    public void tearDown(){
        db.shutdown();
    }

    @Test
    public void initData(){
        assertThat(template.queryForObject("select count(*) from sqlmap",int.class), is(2));

        List<Map<String,Object>> list = template.queryForList("select * from sqlmap order by key_");
        assertThat((String)list.get(0).get("key_"), is("KEY1"));
        assertThat((String)list.get(0).get("sql_"), is("SQL1"));
        assertThat((String)list.get(1).get("key_"), is("KEY2"));
        assertThat((String)list.get(1).get("sql_"), is("SQL2"));
    }

    @Test
    public void insert(){
        template.update("insert into sqlmap(key_, sql_) values(?,?)", "KEY3", "SQL3");

        assertThat(template.queryForObject("select count(*) from sqlmap",int.class), is(3));
    }
}
