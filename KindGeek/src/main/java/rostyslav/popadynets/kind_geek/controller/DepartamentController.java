package rostyslav.popadynets.kind_geek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rostyslav.popadynets.kind_geek.entity.Departament;
import rostyslav.popadynets.kind_geek.service.DepartamentService;

import java.util.List;

@RestController
@RequestMapping("departament")
public class DepartamentController {

    @Autowired
    private DepartamentService departamentService;

    @PostMapping
    public ResponseEntity<String> addDepartament(@RequestBody Departament departament) {
        try {
            departamentService.addDepartament(departament);
        } catch (Exception e) {
            System.err.println("Помилка збереження департамента.");
        }
        return new ResponseEntity<>(departament.getName(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Departament>> getAllDepartaments() {
        return new ResponseEntity<>(departamentService.findAllDepartaments(), HttpStatus.OK);
    }

    @GetMapping("/{departamentName}")
    public ResponseEntity<Departament> getDepartamentByName(@PathVariable("departamentName") String departamentName) {
        Departament departament = departamentService.findDepartamentByName(departamentName);
        if (!departamentService.existsDepartamentByName(departamentName)) {
            System.err.println("Департамент з таким iменем не знайдений!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(departament, HttpStatus.OK);
    }

    @GetMapping("/byPosition/{positionName}")
    public ResponseEntity<Departament> getDepartamentByPositionName(@PathVariable("positionName") String positionName) {
        Departament departament = departamentService.findDepartamentByPositionName(positionName);
        if (departament == null) {
            System.err.println("Департамент з таким iменем позиції не знайдений!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(departament, HttpStatus.OK);
    }

    @PutMapping("/{departamentName}")
    public ResponseEntity<Void> updateDepartament(@PathVariable("departamentName") String departamentName,
                                             @RequestBody Departament departament
    ){
        Departament departamentNew = departamentService.findDepartamentByName(departamentName);
        if (departamentService.existsDepartamentByName(departamentName)) {
            departamentNew.setName(departament.getName());
            departamentNew.setDescription(departament.getDescription());
            departamentService.updateDepartament(departamentNew);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{departamentName}")
    public ResponseEntity<Void> deleteDepartament(@PathVariable("departamentName") String departamentName) {
        if (departamentService.existsDepartamentByName(departamentName)) {
            departamentService.deleteDepartament(departamentName);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
