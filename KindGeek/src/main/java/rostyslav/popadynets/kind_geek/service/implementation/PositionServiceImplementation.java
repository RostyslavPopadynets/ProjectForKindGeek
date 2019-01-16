package rostyslav.popadynets.kind_geek.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyslav.popadynets.kind_geek.entity.Person;
import rostyslav.popadynets.kind_geek.entity.Position;
import rostyslav.popadynets.kind_geek.repository.DepartamentRepository;
import rostyslav.popadynets.kind_geek.repository.PersonRepository;
import rostyslav.popadynets.kind_geek.repository.PositionRepository;
import rostyslav.popadynets.kind_geek.service.PositionService;

import java.util.List;

@Service
public class PositionServiceImplementation implements PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartamentRepository departamentRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void addPosition(Position position) {
        positionRepository.save(position);
    }

    @Override
    public List<Position> findAllPositions() {
        List<Position> positions = positionRepository.findAll();
        return positions;
    }

    @Override
    public Position findPositionByName(String name) {
        Position position = positionRepository.findByName(name);
        if(position != null) {
            return position;
        } else {
            return null;
        }
    }

    @Override
    public List<Position> findPositionByDepartamentName(String departamentName) {
        List<Position> positions = positionRepository.findByDepartamentId(departamentRepository.findByName(departamentName).getId());
        if(!positions.isEmpty()) {
            return positions;
        } else {
            return null;
        }
    }

    @Override
    public Position findPositionByPersonName(String personPhoneNumber) {
        List<Position> positions = positionRepository.findAll();
        Position pos = new Position();
        for(Position p : positions) {
            for (int i = 0; i < p.getPersons().size(); i++) {
                if (p.getPersons().get(i).getPhoneNumber().equals(personPhoneNumber) ) {
                    pos =  p;
                } else
                    pos = null;
            }
        }
        return pos;
    }

    @Override
    public void updatePosition(Position position) {
        positionRepository.save(position);
    }

    @Override
    public void deletePosition(String name) {
        Position position = positionRepository.findByName(name);
        if (position != null) {
            List<Person> persons = personRepository.findAll();
            for (int i = 0; i < persons.size(); i++) {
                if (persons.get(i).getProject().getName().equals(name)) {
                    personRepository.findByEmail(persons.get(i).getEmail()).setPosition(null);
                }
            }
            positionRepository.delete(position);
        }
    }

    @Override
    public boolean existsPositionByName(String name) {
        return positionRepository.existsByName(name);
    }

    @Override
    public boolean existsPositionByDepartamentName(String departamentName) {
        return positionRepository.existsByDepartamentId(departamentRepository.findByName(departamentName).getId());
    }
}
