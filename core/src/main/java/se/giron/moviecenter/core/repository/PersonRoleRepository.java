package se.giron.moviecenter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.giron.moviecenter.model.entity.PersonRole;
import se.giron.moviecenter.model.entity.Role;

import java.util.List;

public interface PersonRoleRepository extends JpaRepository<PersonRole, Long> {

    @Query("SELECT pr FROM PersonRole pr WHERE pr.role.code = :roleCd")
    List<PersonRole> findByRole(@Param("roleCd") String roleCd);

    @Query("SELECT pr FROM PersonRole pr WHERE pr.role.code <> 'ACTOR'")
    List<PersonRole> findAllCrewMembers();

    @Query("SELECT pr FROM PersonRole pr WHERE pr.person.name = :pName")
    List<PersonRole> findByName(@Param("pName") String name);

    @Query("SELECT r FROM Role r LEFT OUTER JOIN PersonRole pr WHERE pr.role = r AND pr.person.id = :pId")
    List<Role> findRolesByPersonId(@Param("pId") Long personId);

    @Query("SELECT COUNT(pr) FROM PersonRole pr WHERE pr.role.code = :roleCd")
    Long countByRole(@Param("roleCd") String roleCd);
}
