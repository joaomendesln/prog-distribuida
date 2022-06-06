package com.authentication.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

//@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    Long id;

    private String username;
    private String personalname;
    private String password;
    private String role;
    private int permission;

    public Client(String username, String personalname, String password, String role, int permission) {

        this.username = username;
        this.personalname = personalname;
        this.password = password;
        this.role = role;
        this.permission = permission;
    }

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPersonalname() {
        return this.personalname;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRole() {
        return this.role;
    }

    public int getPermission() {
        return this.permission;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPersonalname(String personalname) {
        this.personalname = personalname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
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
        return Objects.equals(this.id, client.id) && Objects.equals(this.username, client.username)
                && Objects.equals(this.personalname, client.personalname) && Objects.equals(this.password, client.password)
                && Objects.equals(this.role, client.role) && Objects.equals(this.permission, client.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.username, this.personalname, this.password, this.role, this.permission);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id + ", login='" + this.username + '\'' + ", personalName='" + this.personalname
                + '\'' + ", password='" + this.password + '\'' + ", role='" + this.role + '\'' + ", permission='" + this.permission + '\'' + '}';
    }
}
