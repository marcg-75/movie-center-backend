package se.giron.moviecenter.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Genre {

    @Id
    @NotBlank(message = "{movie.genre.notnull}")
    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public Genre setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public Genre setName(String name) {
        this.name = name;
        return this;
    }
}
