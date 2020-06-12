package com.newbie.Spring_Newbie.User.sqlService;

public interface SqlRegistry {
	void registerSql(String key, String sql);

	String findSql(String key) throws SqlNotFoundException;
}
