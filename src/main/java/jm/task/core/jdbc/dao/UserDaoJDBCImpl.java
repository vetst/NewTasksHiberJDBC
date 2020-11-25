package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() throws SQLException {
        Connection connection = Util.getInstance().getMysqlConnection();
        Statement stmt = connection.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id bigint auto_increment, name varchar(256), lastName varchar(256), age bigint, primary key (id))");
        stmt.close();
    }

    @Override
    public void cleanUsersTable() throws SQLException {
        Connection connection = Util.getInstance().getMysqlConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("TRUNCATE TABLE users ");
        stmt.close();
    }

    @Override
    public void dropUsersTable() throws SQLException {
        Connection connection = Util.getInstance().getMysqlConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS users");
        stmt.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String addQuery = "insert into users (name, lastname, age) values (?, ?, ?)";
        try (Connection connection = Util.getInstance().getMysqlConnection();
             PreparedStatement statement = connection.prepareStatement(addQuery);) {

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new SQLException();
        }
    }

    @Override
    public void removeUserById(long id) throws SQLException {
        String deleteQuery = "DELETE FROM users WHERE id =?";
        try (Connection connection = Util.getInstance().getMysqlConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> resultList = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = Util.getInstance().getMysqlConnection();
            stmt = connection.createStatement();
            stmt.execute("select * from users");
            rs = stmt.getResultSet();
            while (rs.next()) {
                resultList.add(new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getByte(4)));
            }
        } catch (Exception e) {
            throw new SQLException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {

            }
        }
        return resultList;
    }
}
