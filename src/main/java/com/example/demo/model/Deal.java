package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Deal {

    @Id
    private String id;
    private String name;

    public Deal() {
    }

    public Deal(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deal deal = (Deal) o;

        if (id != null ? !id.equals(deal.id) : deal.id != null) return false;
        return name != null ? name.equals(deal.name) : deal.name == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Deal{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
