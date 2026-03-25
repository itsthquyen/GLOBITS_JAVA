package com.globits.demo.repository;

import com.globits.demo.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t " +
           "LEFT JOIN t.project prj " +
           "LEFT JOIN prj.company cmp " +
           "LEFT JOIN t.person per " +
           "WHERE (:companyId IS NULL OR cmp.id = :companyId) AND " +
           "(:projectId IS NULL OR prj.id = :projectId) AND " +
           "(:personId IS NULL OR per.id = :personId) AND " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:priority IS NULL OR t.priority = :priority) AND " +
           "(:name IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Task> searchTasks(
            @Param("companyId") Long companyId,
            @Param("projectId") Long projectId,
            @Param("personId") Long personId,
            @Param("status") Integer status,
            @Param("priority") Integer priority,
            @Param("name") String name,
            Pageable pageable);
}
