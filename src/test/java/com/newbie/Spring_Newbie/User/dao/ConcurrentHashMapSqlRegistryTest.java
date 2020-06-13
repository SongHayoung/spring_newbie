package com.newbie.Spring_Newbie.User.dao;

import com.newbie.Spring_Newbie.User.sqlService.ConcurrentHashMapSqlRegistry;
import com.newbie.Spring_Newbie.User.sqlService.UpdatetableSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatebleSqlRegistryTest{
    protected UpdatetableSqlRegistry createUpdateableSqlRegistry(){
        return new ConcurrentHashMapSqlRegistry();
    }
}
