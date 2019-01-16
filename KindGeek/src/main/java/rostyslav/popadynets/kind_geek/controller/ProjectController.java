package rostyslav.popadynets.kind_geek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rostyslav.popadynets.kind_geek.entity.Project;
import rostyslav.popadynets.kind_geek.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<String> addProject(@RequestBody Project project) {
        try {
            projectService.addProject(project);
        } catch (Exception e) {
            System.err.println("Помилка збереження проекта.");
        }
        return new ResponseEntity<>(project.getName(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(projectService.findAllProjects(), HttpStatus.OK);
    }

    @GetMapping("/{projectName}")
    public ResponseEntity<Project> getProjectByName(@PathVariable("projectName") String projectName) {
        Project project = projectService.findProjectByName(projectName);
        if (!projectService.existsProjectByName(projectName)) {
            System.err.println("Проект з таким iменем не знайдений!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/byPerson/{personPhoneNumber}")
    public ResponseEntity<Project> getProjectByPersonPhoneNumber(@PathVariable("personPhoneNumber") String personPhoneNumber) {
        Project project = projectService.findProjectByPersonName(personPhoneNumber);
        if (project == null) {
            System.err.println("Проект з таким номером телефона користувача не знайдений!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PutMapping("/{projectName}")
    public ResponseEntity<Void> updateProject(@PathVariable("projectName") String projectName,
                                                  @RequestBody Project project
    ){
        Project projectNew = projectService.findProjectByName(projectName);
        if (projectService.existsProjectByName(projectName)) {
            projectNew.setName(project.getName());
            projectNew.setDescription(project.getDescription());
            projectService.updateProject(projectNew);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{projectName}")
    public ResponseEntity<Void> deleteProject(@PathVariable("projectName") String projectName) {
        if (projectService.existsProjectByName(projectName)) {
            projectService.deleteProject(projectName);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
