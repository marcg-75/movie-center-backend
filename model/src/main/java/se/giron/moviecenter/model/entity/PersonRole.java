package se.giron.moviecenter.model.entity;

import javax.persistence.*;

@Entity
public class PersonRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "role_cd", nullable = false)
    private Role role;

    public Long getId() {
        return id;
    }

    public PersonRole setId(Long id) {
        this.id = id;
        return this;
    }

    public Person getPerson() {
        return person;
    }

    public PersonRole setPerson(Person person) {
        this.person = person;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public PersonRole setRole(Role role) {
        this.role = role;
        return this;
    }
}
