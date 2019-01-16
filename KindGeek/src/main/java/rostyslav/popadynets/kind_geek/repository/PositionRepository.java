package rostyslav.popadynets.kind_geek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rostyslav.popadynets.kind_geek.entity.Position;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long>, JpaSpecificationExecutor<Position> {

    Position findByName(String name);

    boolean existsByName(String name);

    List<Position> findByDepartamentId(Long id);

    boolean existsByDepartamentId(Long id);

}
