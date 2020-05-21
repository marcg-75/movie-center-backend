package se.giron.moviecenter.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nameSwedish;

    public Long getId() {
        return id;
    }

    public Language setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Language setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameSwedish() {
        return nameSwedish;
    }

    public Language setNameSwedish(String nameSwedish) {
        this.nameSwedish = nameSwedish;
        return this;
    }
}
