package rostyslav.popadynets.kind_geek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rostyslav.popadynets.kind_geek.entity.Departament;

@Repository
public interface DepartamentRepository extends JpaRepository<Departament, Long>, JpaSpecificationExecutor<Departament> {

    Departament findByName(String name);

    boolean existsByName(String name);

}
