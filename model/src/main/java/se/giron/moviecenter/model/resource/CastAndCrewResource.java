package se.giron.moviecenter.model.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import se.giron.moviecenter.model.entity.Role;

@ApiModel(value = "CastAndCrewResource")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CastAndCrewResource {

    @ApiModelProperty(notes = "CastAndCrew entity ID", dataType = "java.lang.Long")
    private Long id;

    private String movieTitle;

    private PersonRoleResource personRole;

    private String characterName;

    private boolean deleted;

    public Long getId() {
        return id;
    }

    public CastAndCrewResource setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public CastAndCrewResource setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
        return this;
    }

    public PersonRoleResource getPersonRole() {
        return personRole;
    }

    public CastAndCrewResource setPersonRole(PersonRoleResource personRole) {
        this.personRole = personRole;
        return this;
    }

    public String getCharacterName() {
        return characterName;
    }

    public CastAndCrewResource setCharacterName(String characterName) {
        this.characterName = characterName;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public CastAndCrewResource setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
