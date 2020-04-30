package com.newbie.Spring_Newbie.User.dao;

import java.sql.*;

public class DeleteAllStatement implements StatementStrategy{
    public PreparedStatement makePreparedStatement(Connection c) throws SQLException{
        PreparedStatement ps = c.prepareStatement("delete from users");
        return ps;
    }
}
