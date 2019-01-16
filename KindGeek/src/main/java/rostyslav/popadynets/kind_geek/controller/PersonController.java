package rostyslav.popadynets.kind_geek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rostyslav.popadynets.kind_geek.entity.Departament;
import rostyslav.popadynets.kind_geek.entity.Person;
import rostyslav.popadynets.kind_geek.entity.Position;
import rostyslav.popadynets.kind_geek.entity.Project;
import rostyslav.popadynets.kind_geek.service.DepartamentService;
import rostyslav.popadynets.kind_geek.service.PersonService;
import rostyslav.popadynets.kind_geek.service.PositionService;
import rostyslav.popadynets.kind_geek.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private DepartamentService departamentService;

    @PostMapping
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        Project project = person.getProject();
        Position position = person.getPosition();
        Person p = addProject(project,person);
        p = addPosition(position,p);
        try {
            personService.addPerson(p);
        } catch (Exception e) {
            System.err.println("Помилка збереження особи.");
        }
        return new ResponseEntity<>(person.getPhoneNumber(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        return new ResponseEntity<>(personService.findAllPersons(), HttpStatus.OK);
    }

    @GetMapping("/{personFNameOrLNameOrPhoneNumber}")
    public ResponseEntity<Person> getPositionByName(@PathVariable("personFNameOrLNameOrPhoneNumber") String personFNameOrLNameOrPhoneNumber) {
        Person person = personService.findPersonByPhoneNumber(personFNameOrLNameOrPhoneNumber);
        if (person == null) {
            person = personService.findPersonByFirstName(personFNameOrLNameOrPhoneNumber);
            if (person == null) {
                person = personService.findPersonByLastName(personFNameOrLNameOrPhoneNumber);
            }
        }
        if (!personService.existsPositionByPhoneNumber(personFNameOrLNameOrPhoneNumber) &&
                !personService.existsPersonByFirstName(personFNameOrLNameOrPhoneNumber) &&
                !personService.existsPersonByLastName(personFNameOrLNameOrPhoneNumber)) {
            System.err.println("Особу з таким iменем,прізвищем чи номером телефону не знайдено!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/byProject/{projectName}")
    public ResponseEntity<List<Person>> getPersonByPersonProjectName(@PathVariable("projectName") String projectName) {
        List<Person> persons = personService.findPersonByProjectName(projectName);
        if (persons.isEmpty()) {
            System.err.println("Особи з таким проектом не знайдені!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/byPosition/{positionName}")
    public ResponseEntity<List<Person>> getPersonByPersonPositionName(@PathVariable("positionName") String positionName) {
        List<Person> persons = personService.findPersonByPositionName(positionName);
        if (persons.isEmpty()) {
            System.err.println("Особи з такою позицією не знайдені!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @PutMapping("/{personPhoneNumber}")
    public ResponseEntity<Void> updatePosition(@PathVariable("personPhoneNumber") String personPhoneNumber,
                                               @RequestBody Person person
    ){
        Person personNew = personService.findPersonByPhoneNumber(personPhoneNumber);
        if (personService.existsPersonByPhoneNumber(personPhoneNumber)) {
            /*
                Можна було використати Lombok, щоб не прописувати гетери, сетери, конструкори та використати
                Builder щоб скоротити код нижче
             */
            personNew.setFirstName(person.getFirstName());
            personNew.setLastName(person.getLastName());
            personNew.setEmail(person.getEmail());
            personNew.setPhoneNumber(person.getPhoneNumber());
            personNew.setAge(person.getAge());
            personNew.setSkills(person.getSkills());

            Project project = person.getProject();
            Position position = person.getPosition();
            Person p = addProject(project,personNew);
            p = addPosition(position,p);
            personService.updatePerson(p);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{personPhomeNumber}")
    public ResponseEntity<Void> deletePerson(@PathVariable("personPhomeNumber") String personPhomeNumber) {
        if (personService.existsPositionByPhoneNumber(personPhomeNumber)) {
            personService.deletePerson(personPhomeNumber);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Person addProject(Project project, Person person){
        boolean existProject = projectService.existsProjectByName(project.getName());
        if (existProject) {
            person.setProject(projectService.findProjectByName(project.getName()));
        } else {
            try {
                projectService.addProject(project);
                person.setProject(projectService.findProjectByName(project.getName()));
            } catch (Exception e) {
                System.err.println("Помилка збереження проекту.");
            }
        }
        return person;
    }

    private Person addPosition(Position position, Person person){
        boolean existPosition = positionService.existsPositionByName(position.getName());
        if (existPosition) {
            person.setPosition(positionService.findPositionByName(position.getName()));
        } else {
            try {
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
                positionService.addPosition(position);
                person.setPosition(positionService.findPositionByName(position.getName()));
            } catch (Exception e) {
                System.err.println("Помилка збереження позиції.");
            }
        }
        return person;
    }

}
