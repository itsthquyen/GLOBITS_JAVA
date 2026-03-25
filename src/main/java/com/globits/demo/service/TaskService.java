package com.globits.demo.service;

import com.globits.demo.model.Task;
import com.globits.demo.repository.TaskRepository;
import com.globits.demo.repository.ProjectRepository;
import com.globits.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PersonRepository personRepository;

    public Task createTask(Task task) {
        resolveRelations(task);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Page<Task> getTasksPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findAll(pageable);
    }

    public Page<Task> searchTasks(Long companyId, Long projectId, Long personId, Integer status, Integer priority, String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.searchTasks(companyId, projectId, personId, status, priority, name, pageable);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(Long id, Task updated) {
        if (taskRepository.existsById(id)) {
            updated.setId(id);
            resolveRelations(updated);
            return taskRepository.save(updated);
        }
        return null;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private void resolveRelations(Task task) {
        if (task.getProject() != null && task.getProject().getId() != null) {
            projectRepository.findById(task.getProject().getId())
                             .ifPresent(task::setProject);
        }
        if (task.getPerson() != null && task.getPerson().getId() != null) {
            personRepository.findById(task.getPerson().getId())
                            .ifPresent(task::setPerson);
        }
    }
}
