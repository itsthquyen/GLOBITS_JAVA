package com.globits.demo.controller;

import com.globits.demo.model.Project;
import com.globits.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public Project create(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @GetMapping
    public List<Project> getAll() {
        return projectService.getAllProjects();
    }

    @GetMapping("/page")
    public Page<Project> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return projectService.getProjectsPage(page, size);
    }

    @GetMapping("/{id}")
    public Project getById(@PathVariable("id") Long id) {
        Optional<Project> project = projectService.getProjectById(id);
        return project.orElse(null);
    }

    @PutMapping("/{id}")
    public Project update(@PathVariable("id") Long id, @RequestBody Project project) {
        return projectService.updateProject(id, project);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        projectService.deleteProject(id);
        return "Đã xóa thành công project id: " + id;
    }
}
