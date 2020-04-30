package com.newbie.Spring_Newbie.User.dao;

import java.sql.*;

public interface StatementStrategy {
    public PreparedStatement makePreparedStatement(Connection c) throws SQLException;
}
