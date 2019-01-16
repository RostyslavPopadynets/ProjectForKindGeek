package rostyslav.popadynets.kind_geek.service;

import rostyslav.popadynets.kind_geek.entity.Position;

import java.util.List;

public interface PositionService {

    public void addPosition(Position position); // CREATE

    public List<Position> findAllPositions(); // READ

    public Position findPositionByName(String name); // READ

    public List<Position> findPositionByDepartamentName(String departamentName); // READ

    public void updatePosition(Position position); //UPDATE

    public void deletePosition(String name); //DELETE

    public boolean existsPositionByName(String name);

    public boolean existsPositionByDepartamentName(String departamentName);

    public Position findPositionByPersonName(String personPhoneNumber);

}
