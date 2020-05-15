package se.giron.moviecenter.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.giron.moviecenter.core.repository.PersonRepository;
import se.giron.moviecenter.core.repository.PersonRoleRepository;
import se.giron.moviecenter.model.entity.Role;
import se.giron.moviecenter.model.enums.RoleEnum;
import se.giron.moviecenter.model.map.PersonMapper;
import se.giron.moviecenter.model.resource.PersonResource;
import se.giron.moviecenter.model.resource.PersonRoleResource;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonRoleRepository personRoleRepository;

    @Transactional(readOnly = true)
    public List<PersonResource> getAllPersons() {
        return personRepository.findAll().stream().map(PersonMapper::entity2PersonResource).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PersonResource> getAllActors() {
        return personRoleRepository.findByRole(RoleEnum.ACTOR.name()).stream()
                .map(pr -> PersonMapper.entity2PersonResource(pr.getPerson()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PersonResource> getAllDirectors() {
        return personRoleRepository.findByRole(RoleEnum.DIRECTOR.name()).stream()
                .map(pr -> PersonMapper.entity2PersonResource(pr.getPerson()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PersonResource> getAllProducers() {
        return personRoleRepository.findByRole(RoleEnum.PRODUCER.name()).stream()
                .map(pr -> PersonMapper.entity2PersonResource(pr.getPerson()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PersonResource> getAllComposers() {
        return personRoleRepository.findByRole(RoleEnum.MUSIC.name()).stream()
                .map(pr -> PersonMapper.entity2PersonResource(pr.getPerson()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PersonResource> getAllWriters() {
        return personRoleRepository.findByRole(RoleEnum.WRITER.name()).stream()
                .map(pr -> PersonMapper.entity2PersonResource(pr.getPerson()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PersonResource> getAllCasters() {
        return personRoleRepository.findByRole(RoleEnum.CASTING.name()).stream()
                .map(pr -> PersonMapper.entity2PersonResource(pr.getPerson()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PersonResource> getAllEditors() {
        return personRoleRepository.findByRole(RoleEnum.EDITOR.name()).stream()
                .map(pr -> PersonMapper.entity2PersonResource(pr.getPerson()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PersonResource> getAllCinematoraphors() {
        return personRoleRepository.findByRole(RoleEnum.CINEMATOGRAPHY.name()).stream()
                .map(pr -> PersonMapper.entity2PersonResource(pr.getPerson()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PersonResource> getAllSoundMakers() {
        return personRoleRepository.findByRole(RoleEnum.SOUND.name()).stream()
                .map(pr -> PersonMapper.entity2PersonResource(pr.getPerson()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PersonResource> getAllArtPeople() {
        return personRoleRepository.findByRole(RoleEnum.ART.name()).stream()
                .map(pr -> PersonMapper.entity2PersonResource(pr.getPerson()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PersonResource> getAllMiscPersons() {
        return personRoleRepository.findByRole(RoleEnum.MISC.name()).stream()
                .map(pr -> PersonMapper.entity2PersonResource(pr.getPerson()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PersonRoleResource> getPersonRolesWithName(String name) {
        return personRoleRepository.findByName(name).stream()
                .map(PersonMapper::entity2PersonRoleResource)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Role> getRolesForPerson(Long personId) {
        return personRoleRepository.findRolesByPersonId(personId);
    }

    @Transactional(readOnly = true)
    public List<PersonResource> getPersonsWithName(String name) {
        return personRepository.findByName(name).stream()
                .map(PersonMapper::entity2PersonResource)
                .collect(Collectors.toList());
    }
}
