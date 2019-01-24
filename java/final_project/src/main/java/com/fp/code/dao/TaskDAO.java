package com.fp.code.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import com.fp.code.exceptions.NotFoundException;
import com.fp.code.model.Task;
import com.fp.code.repository.TaskRepository;

@Component
public class TaskDAO {

	@Autowired
	TaskRepository taskRepository;

	public long getLastId() {
		return taskRepository.getLastId();
	}

	public void saveTask(Task task) {
		taskRepository.save(task);
	}

	public Task findTask(long task_id) {
		return taskRepository.getOne(task_id);
	}

	public void update(Task updatedTask) {
		Task oldTask = findTask(updatedTask.getTaskId());
		oldTask.setEndDate(updatedTask.getEndDate());
		oldTask.setParent(updatedTask.getParent());
		oldTask.setPriority(updatedTask.getPriority());
		oldTask.setProjectTasks(updatedTask.getProjectTasks());
		oldTask.setStartDate(updatedTask.getStartDate());
		oldTask.setStatus(updatedTask.getStatus());
		oldTask.setTask(updatedTask.getTask());
		// oldTask.setTasks(updatedTask.getTasks());

		saveTask(oldTask);
	}

	public Long getTaskCount(Long projectId) {
		return taskRepository.getTaskCount(projectId);
	}

	public Long getCompletedTaskCount(Long projectId) {
		return taskRepository.getCompletedTaskCount(projectId);
	}
	
	public List<Task> getAllTasksByProjectId(@PathVariable("projectId") long projectId) {
		return taskRepository.findAllByProjectId(projectId);
	}
	
	public void updateTask(Task task) {
		saveTask(task);
	}
	
	public void deleteTask(long taskId) {
		taskRepository.deleteTask(taskId);
	}
}
