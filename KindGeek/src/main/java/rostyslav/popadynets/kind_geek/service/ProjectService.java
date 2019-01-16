package rostyslav.popadynets.kind_geek.service;

import rostyslav.popadynets.kind_geek.entity.Project;

import java.util.List;

public interface ProjectService {

    public void addProject(Project project); // CREATE

    public List<Project> findAllProjects(); // READ

    public Project findProjectByName(String name); // READ

    public void updateProject(Project project); //UPDATE

    public void deleteProject(String name); //DELETE

    public boolean existsProjectByName(String name);

    public Project findProjectByPersonName(String personPhoneNumber);

}
