package se.giron.moviecenter.model.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PersonResource")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonResource {

    @ApiModelProperty(notes = "Person entity ID", dataType = "java.lang.Long")
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public PersonResource setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PersonResource setName(String name) {
        this.name = name;
        return this;
    }
}
