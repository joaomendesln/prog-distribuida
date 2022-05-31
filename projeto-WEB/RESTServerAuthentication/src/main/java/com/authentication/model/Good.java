package com.authentication.model;

import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

import javax.persistence.*;

@Entity
public class Good {

    private @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    ) Long id;

    private String description;
    @ManyToOne
    private Client client;

    Good() {}

    public Good(String description, Client client) {

        this.description = description;
        this.client = client;
    }

    public Long getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public Client getClient() {
        return this.client;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Good))
            return false;
        Good good = (Good) o;
        return Objects.equals(this.id, good.id) && Objects.equals(this.description, good.description)
                && this.client == good.client;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.description, this.client);
    }

    @Override
    public String toString() {
        return "Good{" + "id=" + this.id + ", description='" + this.description + '\'' + ", client=" + this.client.getPersonalName() + '}';
    }
}