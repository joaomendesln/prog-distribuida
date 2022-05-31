package com.authentication.model;

import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public
class Client {

    private @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    ) Long id;
    private String login;
    private String personalName;
    private String password;
    private int permission;

    Client() {}

    public Client(String login, String personalName, String password, int permission) {

        this.login = login;
        this.personalName = personalName;
        this.password = password;
        this.permission = permission;
    }

    public Long getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPersonalName() {
        return this.personalName;
    }

    public String getPassword() {
        return this.password;
    }

    public int getPermission() {
        return this.permission;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Client))
            return false;
        Client client = (Client) o;
        return Objects.equals(this.id, client.id) && Objects.equals(this.login, client.login)
                && Objects.equals(this.personalName, client.personalName) && Objects.equals(this.password, client.password)
                && Objects.equals(this.permission, client.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.login, this.personalName, this.password, this.permission);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id + ", login='" + this.login + '\'' + ", personalName='" + this.personalName
                + '\'' + ", password='" + this.password + '\'' + ", permission='" + this.permission + '\'' + '}';
    }
}