package org.example.Lab2.DB;

import org.example.Lab2.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

    private static Mediator initializeMediator() throws SQLException {
        var mediator = new Mediator();
        mediator.createConnect("postgres", "1234");
        return mediator;
    }

    private static void destroyMediator(Mediator mediator) throws SQLException {
        mediator.disconnect();
    }

    private static User parseUser(ResultSet rs) throws SQLException {
        return new User(rs.getLong("id"), rs.getString("name"), rs.getString("password"),
                rs.getString("login"), rs.getString("email"));
    }

    private static User getUser(String queryAddition) throws SQLException {
        var mediator = initializeMediator();
        var resultSet = mediator.executeQuery("SELECT * FROM \"Users\" WHERE " + queryAddition);
        destroyMediator(mediator);
        if(resultSet == null) throw new SQLException("User not found");
        return parseUser(resultSet);
    }

    public static User getUserById(Integer id) throws SQLException {
        return getUser("\"id\" = " + id);
    }

    public static User getUserByName(String name) throws SQLException {
        return getUser("\"name\" = '" + name + "'");
    }

    public static User getUserByEmail(String email) throws SQLException {
        return getUser("\"email\" = '" + email + "'");
    }

    public static User getUserByLogin(String login) throws SQLException {
        return getUser("\"login\" = '" + login + "'");
    }

    public static void createUser(User user) throws SQLException {
        var mediator = initializeMediator();
        mediator.executeUpdate(String.format("insert into \"Users\" values(default,'%s','%s','%s','%s')", user.getName(), user.getLogin(),
                user.getPassword(), user.getEmail()));
    }

    public static void updateUser(User user) throws SQLException {
        var mediator = initializeMediator();
        mediator.executeUpdate(String.format("UPDATE \"Users\" SET name='%s', login='%s', password='%s', email='%s' WHERE id=%d",
                user.getName(), user.getLogin(), user.getPassword(), user.getEmail(), user.getId()));
    }

    public static void deleteUser(Integer id) throws SQLException {
        var mediator = initializeMediator();
        mediator.executeUpdate("DELETE FROM \"Users\" WHERE id=" + id);
    }

    public static void deleteUser(User user) throws SQLException {
        var mediator = initializeMediator();
        mediator.executeUpdate("DELETE FROM \"Users\" WHERE id=" + user.getId());
    }
}
