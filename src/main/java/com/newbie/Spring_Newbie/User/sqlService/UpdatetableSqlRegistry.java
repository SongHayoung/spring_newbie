package com.newbie.Spring_Newbie.User.sqlService;

import java.util.Map;

public interface UpdatetableSqlRegistry extends SqlRegistry{
    public void updateSql(String key, String sql) throws SqlUpdateFailureException;

    public void updateSql(Map<String, String> sqlmap) throws  SqlUpdateFailureException;
}
