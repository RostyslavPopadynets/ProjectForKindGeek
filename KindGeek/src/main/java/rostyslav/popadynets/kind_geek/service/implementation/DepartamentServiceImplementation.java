package rostyslav.popadynets.kind_geek.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyslav.popadynets.kind_geek.entity.Departament;
import rostyslav.popadynets.kind_geek.entity.Position;
import rostyslav.popadynets.kind_geek.repository.DepartamentRepository;
import rostyslav.popadynets.kind_geek.repository.PositionRepository;
import rostyslav.popadynets.kind_geek.service.DepartamentService;

import java.util.List;

@Service
public class DepartamentServiceImplementation implements DepartamentService {

    @Autowired
    private DepartamentRepository departamentRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public void addDepartament(Departament departament) {
        departamentRepository.save(departament);
    }

    @Override
    public List<Departament> findAllDepartaments() {
        List<Departament> departaments = departamentRepository.findAll();
        return departaments;
    }

    @Override
    public Departament findDepartamentByName(String name) {
        Departament departament = departamentRepository.findByName(name);
        if(departament != null) {
            return departament;
        } else {
            return null;
        }
    }

    @Override
    public void updateDepartament(Departament departament) {
        departamentRepository.save(departament);
    }

    @Override
    public void deleteDepartament(String name) {
        Departament departament = departamentRepository.findByName(name);
        if (departament != null) {
            List<Position> positions = positionRepository.findAll();
            for (int i = 0; i < positions.size(); i++) {
                if (positions.get(i).getDepartament().getName().equals(name)) {
                    positionRepository.findByName(positions.get(i).getName()).setDepartament(null);
                }
            }
            departamentRepository.delete(departament);
        }
    }

    @Override
    public boolean existsDepartamentByName(String name) {
        return departamentRepository.existsByName(name);
    }

    @Override
    public Departament findDepartamentByPositionName(String positionName) {
        List<Departament> departaments = departamentRepository.findAll();
        Departament depart = new Departament();
        for(Departament dep : departaments) {
            for (int i = 0; i < dep.getPositions().size(); i++) {
                if (dep.getPositions().get(i).getName().equals(positionName)) {
                    depart =  dep;
                } else
                    depart = null;
            }
        }
        return depart;
    }
}
