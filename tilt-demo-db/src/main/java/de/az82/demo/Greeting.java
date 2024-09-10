package de.az82.demo;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Greeting extends PanacheEntity {
    public String text;
}
