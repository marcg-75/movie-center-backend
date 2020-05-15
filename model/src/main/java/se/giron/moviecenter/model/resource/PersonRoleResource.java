package se.giron.moviecenter.model.resource;

import io.swagger.annotations.ApiModelProperty;
import se.giron.moviecenter.model.entity.Role;

public class PersonRoleResource {

    @ApiModelProperty(notes = "PersonRole entity ID", dataType = "java.lang.Long")
    private Long id;

    private PersonResource person;

    private Role role;

    public Long getId() {
        return id;
    }

    public PersonRoleResource setId(Long id) {
        this.id = id;
        return this;
    }

    public PersonResource getPerson() {
        return person;
    }

    public PersonRoleResource setPerson(PersonResource person) {
        this.person = person;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public PersonRoleResource setRole(Role role) {
        this.role = role;
        return this;
    }
}
