package se.giron.moviecenter.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.giron.moviecenter.core.service.PersonService;
import se.giron.moviecenter.model.entity.Role;
import se.giron.moviecenter.model.resource.PersonResource;
import se.giron.moviecenter.model.resource.PersonRoleResource;
import se.giron.moviecenter.model.resource.error.ErrorResponse;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "Person", value = "Api for retrieving current persons.")
@RestController
@RequestMapping({"/person"})
public class PersonController {

    @Autowired
    private PersonService personService;

    @ApiOperation(value = "Fetch list of all persons in the database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PersonResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/all", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonResource> getAllPersons() {
        return personService.getAllPersons();
    }

    @ApiOperation(value = "Convenience method for retrieving a list of all the actors in the database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PersonResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/actors", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonRoleResource> getAllActors() {
        return personService.getAllActors();
    }

    @ApiOperation(value = "Convenience method for retrieving a list of all the crew members (not actors) in the database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PersonResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/crew", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonRoleResource> getAllCrewMembers() {
        return personService.getAllCrewMembers();
    }


    @ApiOperation(value = "Convenience method for retrieving a list of all the directors in the database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PersonResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/directors", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonRoleResource> getAllDirectors() {
        return personService.getAllDirectors();
    }

    @ApiOperation(value = "Convenience method for retrieving a list of all the producers in the database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PersonResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/producers", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonRoleResource> getAllProducers() {
        return personService.getAllProducers();
    }

    @ApiOperation(value = "Convenience method for retrieving a list of all the music composers in the database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PersonResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/music", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonRoleResource> getAllComposers() {
        return personService.getAllComposers();
    }

    @ApiOperation(value = "Convenience method for retrieving a list of all the writers in the database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PersonResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/writers", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonRoleResource> getAllWriters() {
        return personService.getAllWriters();
    }

    @ApiOperation(value = "Convenience method for retrieving a list of all the casters in the database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PersonResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/casters", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonRoleResource> getAllCasters() {
        return personService.getAllCasters();
    }

    @ApiOperation(value = "Convenience method for retrieving a list of all the editors in the database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PersonResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/editors", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonRoleResource> getAllEditors() {
        return personService.getAllEditors();
    }

    @ApiOperation(value = "Convenience method for retrieving a list of all the cinematography persons in the database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PersonResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/cinematoraphors", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonRoleResource> getAllCinematoraphors() {
        return personService.getAllCinematoraphors();
    }

    @ApiOperation(value = "Convenience method for retrieving a list of all the sound makers in the database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PersonResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/sound", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonRoleResource> getAllSoundMakers() {
        return personService.getAllSoundMakers();
    }

    @ApiOperation(value = "Convenience method for retrieving a list of all the art people in the database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PersonResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/art", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonRoleResource> getAllArtPeople() {
        return personService.getAllArtPeople();
    }

    @ApiOperation(value = "Convenience method for retrieving a list of all the uncategorized persons in the database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PersonResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/other", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonRoleResource> getAllMiscPersons() {
        return personService.getAllMiscPersons();
    }

    @ApiOperation(value = "Fetch a list of all the person role objects with the given person name in the database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PersonRoleResource.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "PersonRoles not found"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/person-roles-with-name/{name}", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonRoleResource> getPersonRolesWithName(@PathVariable String name) {
        return personService.getPersonRolesWithName(name);
    }

    @ApiOperation(value = "Fetch a list of all the roles for a person with the given name.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Role.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Roles not found"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/roles-for-person/{personId}", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<Role> getRolesForPerson(@PathVariable Long personId) {
        return personService.getRolesForPerson(personId);
    }

    @ApiOperation(value = "Fetch a list of all the persons with the given name in the database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PersonResource.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Persons not found"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/persons-with-name/{name}", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonResource> getPersonsWithName(@PathVariable String name) {
        return personService.getPersonsWithName(name);
    }
}
