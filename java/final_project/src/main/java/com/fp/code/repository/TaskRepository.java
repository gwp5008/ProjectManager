package com.fp.code.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fp.code.model.Task;
import com.fp.code.model.Users;

public interface TaskRepository extends JpaRepository<Task, Long>{

	@Query(value = "select count(*) from task where project_id = ?1", nativeQuery = true)
	Long getTaskCount(Long projectId);
	
	@Query(value = "select count(*) from task where project_id = ?1 and status = 'complete'", nativeQuery = true)
	Long getCompletedTaskCount(Long projectId);
	
	@Query(value = "select task_id from task order by task_id desc limit 1", nativeQuery = true)
	Long getLastId();
	
//	List<Task> findAllByProjectTasks(Long projectId);
	
	@Query(value = "select * from task t where project_id = ?1", nativeQuery = true)
	List<Task> findAllByProjectId(Long projectId);
	
	@Modifying
	@Query(value = "update task t set t.status = 'inactive' where t.task_id = ?1", nativeQuery = true)
	public void deleteTask(long taskId);
}
