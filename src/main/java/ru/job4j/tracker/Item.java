package ru.job4j.tracker;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private Timestamp created;

    public Item(String name, String description, Timestamp created) {
        this.name = name;
        this.description = description;
        this.created = created;
    }
}