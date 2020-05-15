package se.giron.moviecenter.model.map;

import se.giron.moviecenter.model.entity.Person;
import se.giron.moviecenter.model.entity.PersonRole;
import se.giron.moviecenter.model.resource.PersonResource;
import se.giron.moviecenter.model.resource.PersonRoleResource;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonMapper {

    public static PersonResource entity2PersonResource(Person person) {
        return new PersonResource()
                .setId(person.getId())
                .setName(person.getName());
    }

    public static List<PersonResource> entities2PersonResources(Set<Person> persons) {
        if (persons == null || persons.size() == 0) {
            return null;
        }

        return persons.stream().map(PersonMapper::entity2PersonResource).collect(Collectors.toList());
    }

    public static Person resource2PersonEntity(PersonResource resource) {
        return new Person()
                .setId(resource.getId())
                .setName(resource.getName());
    }

    public static Set<Person> resources2PersonEntities(List<PersonResource> resources, final Set<Person> persons) {
        if (resources == null) {
            return persons;
        }

        // All new.
        if (persons.isEmpty()) {
            return resources.stream()
                    .map(PersonMapper::resource2PersonEntity)
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
                        .map(PersonMapper::resource2PersonEntity).collect(Collectors.toList()));

        // TODO: Remove deleted persons.

        return persons;
    }

    public static PersonRoleResource entity2PersonRoleResource(PersonRole personRole) {
        return new PersonRoleResource()
                .setId(personRole.getId())
                .setPerson(entity2PersonResource(personRole.getPerson()))
                .setRole(personRole.getRole());
    }

    public static List<PersonRoleResource> entities2PersonRoleResources(Set<PersonRole> personRoles) {
        if (personRoles == null || personRoles.size() == 0) {
            return null;
        }

        return personRoles.stream().map(PersonMapper::entity2PersonRoleResource).collect(Collectors.toList());
    }

    public static PersonRole resource2PersonRoleEntity(PersonRoleResource resource) {
        return new PersonRole()
                .setId(resource.getId())
                .setPerson(resource2PersonEntity(resource.getPerson()))
                .setRole(resource.getRole());
    }

    public static Set<PersonRole> resources2PersonRoleEntities(List<PersonRoleResource> resources, final Set<PersonRole> personRoles) {
        if (resources == null) {
            return personRoles;
        }

        // All new.
        if (personRoles.isEmpty()) {
            return resources.stream()
                    .map(PersonMapper::resource2PersonRoleEntity)
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
                        .map(PersonMapper::resource2PersonRoleEntity)
                        .collect(Collectors.toSet()));

        // TODO: Remove deleted personRoles.

        return personRoles;
    }
}
