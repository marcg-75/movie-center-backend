package se.giron.moviecenter.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Format {

    @Id
    @NotBlank(message = "{movie.format.code.notnull}")
    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public Format setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public Format setName(String name) {
        this.name = name;
        return this;
    }
}
