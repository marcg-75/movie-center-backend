package se.giron.moviecenter.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public Studio setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Studio setName(String name) {
        this.name = name;
        return this;
    }
}
