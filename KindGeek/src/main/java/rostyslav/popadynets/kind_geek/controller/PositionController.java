package rostyslav.popadynets.kind_geek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rostyslav.popadynets.kind_geek.entity.Departament;
import rostyslav.popadynets.kind_geek.entity.Position;
import rostyslav.popadynets.kind_geek.service.DepartamentService;
import rostyslav.popadynets.kind_geek.service.PositionService;

import java.util.List;

@RestController
@RequestMapping("position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private DepartamentService departamentService;

    @PostMapping
    public ResponseEntity<String> addPosition(@RequestBody Position position) {
        Departament departament = position.getDepartament();
        boolean existDepartament = departamentService.existsDepartamentByName(departament.getName());
        if (existDepartament) {
            position.setDepartament(departamentService.findDepartamentByName(departament.getName()));
        } else {
            try {
                departamentService.addDepartament(departament);
                position.setDepartament(departamentService.findDepartamentByName(departament.getName()));
            } catch (Exception e) {
                System.err.println("Помилка збереження департамента.");
            }
        }
        try {
            positionService.addPosition(position);
        } catch (Exception e) {
            System.err.println("Помилка збереження проекта.");
        }
        return new ResponseEntity<>(position.getName(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Position>> getAllPositions() {
        return new ResponseEntity<>(positionService.findAllPositions(), HttpStatus.OK);
    }

    @GetMapping("/{positionName}")
    public ResponseEntity<Position> getPositionByName(@PathVariable("positionName") String positionName) {
        Position position = positionService.findPositionByName(positionName);
        if (!positionService.existsPositionByName(positionName)) {
            System.err.println("Позиція з таким iменем не знайдена!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(position, HttpStatus.OK);
    }

    @GetMapping("/byPerson/{personPhoneNumber}")
    public ResponseEntity<Position> getProjectByPersonPhoneNumber(@PathVariable("personPhoneNumber") String personPhoneNumber) {
        Position position = positionService.findPositionByPersonName(personPhoneNumber);
        if (position == null) {
            System.err.println("Позиція з таким номером телефона користувача не знайдений!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(position, HttpStatus.OK);
    }

    @GetMapping("/byDepartament/{departamentName}")
    public ResponseEntity<List<Position>> getProjectByPersonDepartamentName(@PathVariable("departamentName") String departamentName) {
        List<Position> positions = positionService.findPositionByDepartamentName(departamentName);
        if (positions.isEmpty()) {
            System.err.println("Позиції з таким департаментом не знайдені!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    @PutMapping("/{positionName}")
    public ResponseEntity<Void> updatePosition(@PathVariable("positionName") String positionName,
                                              @RequestBody Position position
    ){
        Position positionNew = positionService.findPositionByName(positionName);
        if (positionService.existsPositionByName(positionName)) {
            positionNew.setName(position.getName());
            positionNew.setDescription(position.getDescription());
            Departament departament = position.getDepartament();
            boolean existDepartament = departamentService.existsDepartamentByName(departament.getName());
            if (existDepartament) {
                positionNew.setDepartament(departamentService.findDepartamentByName(departament.getName()));
            } else {
                try {
                    departamentService.addDepartament(departament);
                    positionNew.setDepartament(departamentService.findDepartamentByName(departament.getName()));
                } catch (Exception e) {
                    System.err.println("Помилка збереження департамента.");
                }
            }
            positionService.updatePosition(positionNew);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{positionName}")
    public ResponseEntity<Void> deletePosition(@PathVariable("positionName") String positionName) {
        if (positionService.existsPositionByName(positionName)) {
            positionService.deletePosition(positionName);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
