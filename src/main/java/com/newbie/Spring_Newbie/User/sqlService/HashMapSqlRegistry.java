package com.newbie.Spring_Newbie.User.sqlService;

import java.util.HashMap;
import java.util.Map;

public class HashMapSqlRegistry implements SqlRegistry {
	private Map<String, String> sqlMap = new HashMap<String, String>();

	public String findSql(String key) throws SqlNotFoundException {
		String sql = sqlMap.get(key);
		if (sql == null)  throw new SqlRetrievalFailureException(key + "를 이용하여 SQL를 찾을 수 없습니다");
		else return sql;
	}

	public void registerSql(String key, String sql) { sqlMap.put(key, sql);	}
}
