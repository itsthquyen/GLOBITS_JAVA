package com.globits.demo.controller;

import com.globits.demo.model.Task;
import com.globits.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public Task create(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping
    public List<Task> getAll() {
        return taskService.getAllTasks();
    }

    @GetMapping("/page")
    public Page<Task> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return taskService.getTasksPage(page, size);
    }

    @GetMapping("/search")
    public Page<Task> searchTasks(
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Long personId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer priority,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return taskService.searchTasks(companyId, projectId, personId, status, priority, name, page, size);
    }

    @GetMapping("/{id}")
    public Task getById(@PathVariable("id") Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.orElse(null);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable("id") Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return "Đã xóa thành công task id: " + id;
    }

    @GetMapping("/export/excel")
    public void exportToExcel(jakarta.servlet.http.HttpServletResponse response) throws java.io.IOException {
        response.setContentType("application/octet-stream");
        java.text.DateFormat dateFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new java.util.Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=tasks_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        // Lấy nguyên list toàn bộ data (Không bị phân trang)
        java.util.List<Task> listTasks = taskService.getAllTasks();
        
        com.globits.demo.util.TaskExcelExporter excelExporter = new com.globits.demo.util.TaskExcelExporter(listTasks);
        excelExporter.export(response);
    }
}
