package com.globits.demo.service;

import com.globits.demo.model.Project;
import com.globits.demo.model.Person;

import com.globits.demo.repository.ProjectRepository;
import com.globits.demo.repository.PersonRepository;
import com.globits.demo.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public Project createProject(Project project) {
        resolveRelations(project);
        return projectRepository.save(project);
    }

    public List<Project> createMultipleProjects(List<Project> projects) {
        for (Project project : projects) {
            resolveRelations(project);
        }
        return projectRepository.saveAll(projects);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Page<Project> getProjectsPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return projectRepository.findAll(pageable);
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Project updateProject(Long id, Project updated) {
        if (projectRepository.existsById(id)) {
            updated.setId(id);
            resolveRelations(updated);
            return projectRepository.save(updated);
        }
        return null;
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    private void resolveRelations(Project project) {
        if (project.getCompany() != null && project.getCompany().getId() != null) {
            companyRepository.findById(project.getCompany().getId())
                             .ifPresent(project::setCompany);
        }
        
        if (project.getPersons() != null && !project.getPersons().isEmpty()) {
            Set<Person> existingPersons = new HashSet<>();
            for (Person p : project.getPersons()) {
                if (p.getId() != null) {
                    personRepository.findById(p.getId()).ifPresent(existingPersons::add);
                }
            }
            project.setPersons(existingPersons);
        }
    }
}
