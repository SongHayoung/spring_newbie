package com.newbie.Spring_Newbie.User.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.either;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/junit.xml")
public class JUnitTest {
    @Autowired
    ApplicationContext context;
    static Set<JUnitTest> testobjects = new HashSet<JUnitTest>();
    static ApplicationContext contextObject = null;

    @Test
    public void test1(){
        assertThat(testobjects, not(hasItem(this)));
        testobjects.add(this);

        assertThat(contextObject==null||contextObject==this.context,is(true));
        contextObject = this.context;
    }

    @Test
    public void test2(){
        assertThat(testobjects,not(hasItem(this)));
        testobjects.add(this);

        assertTrue(contextObject==null||contextObject==this.context);
        contextObject = this.context;
    }

    @Test
    public void test3(){
        assertThat(testobjects,not(hasItem(this)));
        testobjects.add(this);

        assertThat(contextObject,either(is(nullValue())).or(is(this.context)));
        contextObject = this.context;
    }
    public static void main(String[] args) {
        JUnitCore.main("com.newbie.Spring_Newbie.User.dao.JUnitTest");
    }
}
