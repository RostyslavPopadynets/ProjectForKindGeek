package rostyslav.popadynets.kind_geek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rostyslav.popadynets.kind_geek.entity.Person;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    Person findByFirstName(String firstName);

    Person findByLastName(String lastName);

    Person findByEmail(String email);

    Person findByPhoneNumber(String phoneNumber);

    List<Person> findByProjectId(Long id);

    List<Person> findByPositionId(Long id);

    boolean existsByFirstName(String firstName);

    boolean existsByLastName(String lastName);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByProjectId(Long id);

    boolean existsByPositionId(Long id);

}
