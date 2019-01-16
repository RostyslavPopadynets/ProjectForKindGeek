package rostyslav.popadynets.kind_geek.service;

import rostyslav.popadynets.kind_geek.entity.Departament;

import java.util.List;

public interface DepartamentService {

    public void addDepartament(Departament departament); // CREATE

    public List<Departament> findAllDepartaments(); // READ

    public Departament findDepartamentByName(String name); // READ

    public void updateDepartament(Departament departament); //UPDATE

    public void deleteDepartament(String name); //DELETE

    public boolean existsDepartamentByName(String name);

    public Departament findDepartamentByPositionName(String positionName);

}
