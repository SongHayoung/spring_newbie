package com.newbie.Spring_Newbie.User.dao;

import com.newbie.Spring_Newbie.User.domain.Level;
import com.newbie.Spring_Newbie.User.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDaoJdbc implements UserDao{
    private RowMapper<User> userMapper = new RowMapper<User>() {
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new User();
            user.setID(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            user.setLevel(Level.valueOf(resultSet.getInt("Level")));
            user.setLogin(resultSet.getInt("Login"));
            user.setRecommend(resultSet.getInt("Recommend"));
            return user;
        }
    };
    private JdbcTemplate jdbcTemplate;

    public void add(final User user) {
        this.jdbcTemplate.update("insert into users(id, name, password, email, Level, Login, Recommend) values(?,?,?,?,?,?,?)"
                                        , user.getID(), user.getName(), user.getPassword(), user.getEmail()
                                        , user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?",
        new Object[] {id},this.userMapper);
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<User> getAll(){
        return this.jdbcTemplate.query("select * from users order by id",this.userMapper);
    }

    public void update(User user){
        this.jdbcTemplate.update("update users set name = ? , password = ? , level = ?, email = ?, login = ?, recommend = ? where id = ? ",  user.getName(), user.getPassword(), user.getLevel().intValue(), user.getEmail(), user.getLogin(), user.getRecommend(), user.getID());
    }
}
