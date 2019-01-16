package rostyslav.popadynets.kind_geek.service;

import rostyslav.popadynets.kind_geek.entity.Person;

import java.util.List;

public interface PersonService {

    public void addPerson(Person person); // CREATE

    public List<Person> findAllPersons(); // READ

    public Person findPersonByFirstName(String firstName); // READ

    public Person findPersonByLastName(String lastName); // READ

    public Person findPersonByEmail(String email); // READ

    public Person findPersonByPhoneNumber(String phoneNumber); // READ

    public List<Person> findPersonByProjectName(String projectName); // READ

    public List<Person> findPersonByPositionName(String positionName); // READ

    public void updatePerson(Person person); //UPDATE

    public void deletePerson(String phoneNumber); //DELETE

    public boolean existsPersonByFirstName(String firstName);

    public boolean existsPersonByLastName(String lastName);

    public boolean existsPersonByEmail(String email);

    public boolean existsPersonByPhoneNumber(String phoneNumber);

    public boolean existsPositionByPhoneNumber(String phoneNumber);

    public boolean existsPersonByProjectName(String projectName);

    public boolean existsPersonByPositionName(String positionName);

}
