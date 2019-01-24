package com.fp.code.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.fp.code.dao.TaskDAO;
import com.fp.code.model.Task;

@Service
@Transactional
public class TaskService {

	@Autowired
	TaskDAO taskDAO;

	public long getLastId() {
		return taskDAO.getLastId();
	}

	public void saveTask(Task task) {
		taskDAO.saveTask(task);
	}

	public Task findTask(long task_id) {
		return taskDAO.findTask(task_id);
	}

	public void update(Task updatedTask) {
		taskDAO.update(updatedTask);
	}

	public Long getTaskCount(Long projectId) {
		return taskDAO.getTaskCount(projectId);
	}

	public Long getCompletedTaskCount(Long projectId) {
		return taskDAO.getCompletedTaskCount(projectId);
	}
	
	public List<Task> getAllTasksByProjectId(@PathVariable("projectId") long projectId) {
		return taskDAO.getAllTasksByProjectId(projectId);
	}
	
	public void updateTask(Task task) {
		taskDAO.updateTask(task);
	}
	
	public void deleteTask(long taskId) {
		taskDAO.deleteTask(taskId);
	}
}
