package rostyslav.popadynets.kind_geek.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyslav.popadynets.kind_geek.entity.Person;
import rostyslav.popadynets.kind_geek.repository.PersonRepository;
import rostyslav.popadynets.kind_geek.repository.PositionRepository;
import rostyslav.popadynets.kind_geek.repository.ProjectRepository;
import rostyslav.popadynets.kind_geek.service.PersonService;

import java.util.List;

@Service
public class PersonServiceImplementation implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public void addPerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public List<Person> findAllPersons() {
        List<Person> persons = personRepository.findAll();
        return persons;
    }

    @Override
    public Person findPersonByFirstName(String firstName) {
        Person person = personRepository.findByFirstName(firstName);
        if(person != null) {
            return person;
        } else {
            return null;
        }
    }

    @Override
    public Person findPersonByLastName(String lastName) {
        Person person = personRepository.findByLastName(lastName);
        if(person != null) {
            return person;
        } else {
            return null;
        }
    }

    @Override
    public Person findPersonByEmail(String email) {
        Person person = personRepository.findByEmail(email);
        if(person != null) {
            return person;
        } else {
            return null;
        }
    }

    @Override
    public Person findPersonByPhoneNumber(String phoneNumber) {
        Person person = personRepository.findByPhoneNumber(phoneNumber);
        if(person != null) {
            return person;
        } else {
            return null;
        }
    }

    @Override
    public List<Person> findPersonByProjectName(String projectName) {
        List<Person> persons = personRepository.findByProjectId(projectRepository.findByName(projectName).getId());
        if(!persons.isEmpty()) {
            return persons;
        } else {
            return null;
        }
    }

    @Override
    public List<Person> findPersonByPositionName(String positionName) {
        List<Person> persons = personRepository.findByPositionId(positionRepository.findByName(positionName).getId());
        if(!persons.isEmpty()) {
            return persons;
        } else {
            return null;
        }
    }

    @Override
    public void updatePerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public void deletePerson(String phoneNumber) {
        Person person = personRepository.findByPhoneNumber(phoneNumber);
        if (person != null) {
            personRepository.delete(person);
        }
    }

    @Override
    public boolean existsPersonByFirstName(String firstName) {
        return personRepository.existsByFirstName(firstName);
    }

    @Override
    public boolean existsPersonByLastName(String lastName) {
        return personRepository.existsByLastName(lastName);
    }

    @Override
    public boolean existsPersonByEmail(String email) {
        return personRepository.existsByEmail(email);
    }

    @Override
    public boolean existsPositionByPhoneNumber(String phoneNumber) {
        return personRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean existsPersonByProjectName(String projectName) {
        return personRepository.existsByProjectId(projectRepository.findByName(projectName).getId());
    }

    @Override
    public boolean existsPersonByPositionName(String positionName) {
        return personRepository.existsByPositionId(positionRepository.findByName(positionName).getId());
    }

    @Override
    public boolean existsPersonByPhoneNumber(String phoneNumber) {
        return personRepository.existsByPhoneNumber(phoneNumber);
    }
}
