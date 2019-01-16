package rostyslav.popadynets.kind_geek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import rostyslav.popadynets.kind_geek.entity.Departament;
import rostyslav.popadynets.kind_geek.entity.Person;
import rostyslav.popadynets.kind_geek.entity.Position;
import rostyslav.popadynets.kind_geek.entity.Project;
import rostyslav.popadynets.kind_geek.reader.Reader;
import rostyslav.popadynets.kind_geek.repository.DepartamentRepository;
import rostyslav.popadynets.kind_geek.repository.PersonRepository;
import rostyslav.popadynets.kind_geek.repository.PositionRepository;
import rostyslav.popadynets.kind_geek.repository.ProjectRepository;
import rostyslav.popadynets.kind_geek.service.DepartamentService;
import rostyslav.popadynets.kind_geek.service.PersonService;
import rostyslav.popadynets.kind_geek.service.PositionService;
import rostyslav.popadynets.kind_geek.service.ProjectService;

@SpringBootApplication
public class KindGeekApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(KindGeekApplication.class, args);
        AddInfoInDataBase(context);
    }

    public static void AddInfoInDataBase(ConfigurableApplicationContext context) {
        DepartamentRepository departamentRepository = context.getBean(DepartamentRepository.class);
        PersonRepository personRepository = context.getBean(PersonRepository.class);
        PositionRepository positionRepository = context.getBean(PositionRepository.class);
        ProjectRepository projectRepository = context.getBean(ProjectRepository.class);

        DepartamentService departamentService = context.getBean(DepartamentService.class);
        PersonService personService = context.getBean(PersonService.class);
        PositionService positionService = context.getBean(PositionService.class);
        ProjectService projectService = context.getBean(ProjectService.class);

        if (departamentRepository.count() == 0) {
            try {
                Reader.read();
                Departament departament = new Departament(Reader.info.get(5),Reader.info.get(6));
                departamentService.addDepartament(departament);
            } catch (Exception e) {
                System.err.println("Departament Error...");
                e.printStackTrace();
            }
        }

        if (projectRepository.count() == 0) {
            try {
                Reader.read();
                Project project = new Project(Reader.info.get(9),Reader.info.get(10));
                projectService.addProject(project);
            } catch (Exception e) {
                System.err.println("Project Error...");
                e.printStackTrace();
            }
        }

        if (positionRepository.count() == 0) {
            try {
                Reader.read();
                if (departamentService.existsDepartamentByName(Reader.info.get(5))) {
                    Position position = new Position(Reader.info.get(7), Reader.info.get(8),
                            departamentService.findDepartamentByName(Reader.info.get(5)));
                    positionService.addPosition(position);
                }
            } catch (Exception e) {
                System.err.println("Position Error...");
                e.printStackTrace();
            }
        }

        if (personRepository.count() == 0) {
            try {
                Reader.read();
                if (projectService.existsProjectByName(Reader.info.get(9)) && positionService.existsPositionByName(Reader.info.get(7))) {
                    Person person = new Person(Reader.info.get(0),Reader.info.get(1),Reader.info.get(2),Reader.info.get(3),22,Reader.info.get(4),
                            projectService.findProjectByName(Reader.info.get(9)),positionService.findPositionByName(Reader.info.get(7)));
                    personService.addPerson(person);
                }
            } catch (Exception e) {
                System.err.println("Person Error...");
                e.printStackTrace();
            }
        }

    }

}

