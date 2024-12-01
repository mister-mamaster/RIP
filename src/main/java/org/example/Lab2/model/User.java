package org.example.Lab2.model;

import org.example.Lab2.DB.DBUtil;

import java.sql.SQLException;

public class User implements Savable, Deletable, Updatable {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String login;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public User(Long id, String name, String email, String password, String login) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.login = login;
    }

    public User(String name, String email, String password, String login) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.login = login;
        this.id = null;
    }

    @Override
    public void save() {
        try {
            if (!(id == null)) throw new SQLException("User already exists");
            DBUtil.createUser(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        try {
            if (id == null) throw new SQLException("User is not exists yet");
            DBUtil.deleteUser(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        try {
            if (id == null) throw new SQLException("User is not exists yet");
            DBUtil.updateUser(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
