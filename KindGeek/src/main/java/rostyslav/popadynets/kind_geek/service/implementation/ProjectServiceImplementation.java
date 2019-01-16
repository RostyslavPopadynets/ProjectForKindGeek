package rostyslav.popadynets.kind_geek.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyslav.popadynets.kind_geek.entity.Person;
import rostyslav.popadynets.kind_geek.entity.Project;
import rostyslav.popadynets.kind_geek.repository.PersonRepository;
import rostyslav.popadynets.kind_geek.repository.ProjectRepository;
import rostyslav.popadynets.kind_geek.service.ProjectService;

import java.util.List;

@Service
public class ProjectServiceImplementation implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void addProject(Project project) {
        projectRepository.save(project);
    }

    @Override
    public List<Project> findAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects;
    }

    @Override
    public Project findProjectByName(String name) {
        Project project = projectRepository.findByName(name);
        if(project != null) {
            return project;
        } else {
            return null;
        }
    }

    @Override
    public Project findProjectByPersonName(String personPhoneNumber) {
        List<Project> projects = projectRepository.findAll();
        Project pr = new Project();
        for(Project p : projects) {
            for (int i = 0; i < p.getPersons().size(); i++) {
                if (p.getPersons().get(i).getPhoneNumber().equals(personPhoneNumber) ) {
                    pr =  p;
                } else
                    pr = null;
            }
        }
        return pr;
    }

    @Override
    public void updateProject(Project project) {
        projectRepository.save(project);
    }

    @Override
    public void deleteProject(String name) {
        Project project = projectRepository.findByName(name);
        if (project != null) {
            List<Person> persons = personRepository.findAll();
            for (int i = 0; i < persons.size(); i++) {
                if (persons.get(i).getProject().getName().equals(name)) {
                    personRepository.findByEmail(persons.get(i).getEmail()).setProject(null);
                }
            }
            projectRepository.delete(project);
        }
    }

    @Override
    public boolean existsProjectByName(String name) {
        return projectRepository.existsByName(name);
    }
}
