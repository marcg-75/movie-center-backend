package se.giron.moviecenter.core.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.giron.moviecenter.core.repository.PersonRepository;
import se.giron.moviecenter.core.repository.PersonRoleRepository;
import se.giron.moviecenter.model.entity.Person;
import se.giron.moviecenter.model.entity.PersonRole;
import se.giron.moviecenter.model.resource.PersonResource;
import se.giron.moviecenter.model.resource.PersonRoleResource;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PersonMapper {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonRoleRepository personRoleRepository;

    public PersonResource entity2PersonResource(Person person) {
        return new PersonResource()
                .setId(person.getId())
                .setName(person.getName());
    }

    @Deprecated
    public List<PersonResource> entities2PersonResources(Set<Person> persons) {
        if (persons == null || persons.size() == 0) {
            return null;
        }

        return persons.stream().map(this::entity2PersonResource).collect(Collectors.toList());
    }

    public Person resource2PersonEntity(PersonResource resource) {
        return new Person()
                .setId(resource.getId())
                .setName(resource.getName());
    }

    @Deprecated
    public Set<Person> resources2PersonEntities(List<PersonResource> resources, final Set<Person> persons) {
        if (resources == null) {
            return persons;
        }

        // All new.
        if (persons.isEmpty()) {
            return resources.stream()
                    .map(this::resource2PersonEntity)
                    .collect(Collectors.toSet());
        }

        // Update existing persons.
        for (Person person : persons) {
            Long id = person.getId();
            Optional<PersonResource> pr = resources.stream().filter(_person -> id.equals(_person.getId())).findFirst();
            if (pr.isPresent()) {
                Person newPerson = resource2PersonEntity(pr.get());
                if (!newPerson.equals(person)) {
                    person.setName(pr.get().getName());
                }
            }
        }

        // Add new persons.
        persons.addAll(
                resources.stream().filter(pr -> pr.getId() == null || pr.getId() < 0)
                        .map(this::resource2PersonEntity).collect(Collectors.toList()));

        return persons;
    }

    public PersonRoleResource entity2PersonRoleResource(PersonRole personRole) {
        return new PersonRoleResource()
                .setId(personRole.getId())
                .setPerson(entity2PersonResource(personRole.getPerson()))
                .setRole(personRole.getRole());
    }

    @Deprecated
    public List<PersonRoleResource> entities2PersonRoleResources(Set<PersonRole> personRoles) {
        if (personRoles == null || personRoles.size() == 0) {
            return null;
        }

        return personRoles.stream().map(this::entity2PersonRoleResource).collect(Collectors.toList());
    }

    /**
     * NOTE: Two persons with the same name will be treated as the same person, if the person ID is missing.
     */
    public PersonRole resource2PersonRoleEntity(PersonRoleResource resource) {
        // Find existing personRole.
        PersonRole personRole = null;

        if (resource.getId() != null) {
            Optional<PersonRole> oPersonRole = personRoleRepository.findById(resource.getId());
            if (oPersonRole.isPresent()) {
                personRole = oPersonRole.get();
            }
        }

        if (personRole == null) {
            List<PersonRole> personRoles = personRoleRepository.findAllByNameAndRole(resource.getPerson().getName(), resource.getRole().getCode());

            if (personRoles != null && !personRoles.isEmpty()) {
                personRole = personRoles.get(0);
            }
        }

        if (personRole != null) {
            return personRole;
        }

        // Find existing person (by ID or name)
        Person person = null;

        if (resource.getPerson().getId() != null) {
            Optional<Person> oPerson = personRepository.findById(resource.getPerson().getId());
            person = oPerson.get();
        } else {
            List<Person> personsWithName = personRepository.findAllByName(resource.getPerson().getName());
            person = (personsWithName != null && !personsWithName.isEmpty()) ? personsWithName.get(0) : null;
        }

        return new PersonRole()
                .setId(resource.getId())
                .setPerson(person != null ? person : resource2PersonEntity(resource.getPerson()))
                .setRole(resource.getRole());
    }

    @Deprecated
    public Set<PersonRole> resources2PersonRoleEntities(List<PersonRoleResource> resources, final Set<PersonRole> personRoles) {
        if (resources == null) {
            return personRoles;
        }

        // All new.
        if (personRoles.isEmpty()) {
            return resources.stream()
                    .map(this::resource2PersonRoleEntity)
                    .collect(Collectors.toSet());
        }

        // Update existing personRoles.
        for (PersonRole personRole : personRoles) {
            Long id = personRole.getId();
            Optional<PersonRoleResource> prr = resources.stream().filter(_pr -> id.equals(_pr.getId())).findFirst();
            if (prr.isPresent()) {
                PersonRole newPr = resource2PersonRoleEntity(prr.get());
                if (!newPr.equals(personRole)) {
                    personRole.setPerson(newPr.getPerson());
                    personRole.setRole(newPr.getRole());
                }
            }
        }

        // Add new mvps.
        personRoles.addAll(
                resources.stream().filter(pr -> pr.getId() == null || pr.getId() < 0)
                        .map(this::resource2PersonRoleEntity)
                        .collect(Collectors.toSet()));

        return personRoles;
    }
}
