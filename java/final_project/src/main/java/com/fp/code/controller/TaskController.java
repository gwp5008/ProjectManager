package com.fp.code.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fp.code.model.ParentTask;
import com.fp.code.model.Project;
import com.fp.code.model.Task;
import com.fp.code.model.Users;
import com.fp.code.service.ParentTaskService;
import com.fp.code.service.ProjectService;
import com.fp.code.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	TaskService taskService;
	
	@Autowired
	ParentTaskService parentTaskService;
	
	@Autowired
	ProjectService projectService;
	
	@PostMapping("/{projectName}")
	@ResponseStatus(HttpStatus.OK)
	public void create(@RequestBody Task task, @PathVariable("projectName") String projectName) {
		Project project = projectService.findProject(projectName);
		task.setProjectTasks(project);
		taskService.saveTask(task);
	}
	
	@PostMapping("/{parentId}/{projectName}")
	@ResponseStatus(HttpStatus.OK)
	public void create(@RequestBody Task task, @PathVariable("parentId") long parentId, @PathVariable("projectName") String projectName) {
		ParentTask parentTask = parentTaskService.findParent(parentId);
		Project project = projectService.findProject(projectName);
		task.setParent(parentTask);
		task.setProjectTasks(project);
		taskService.saveTask(task);
	}

	@GetMapping("/{id}")
	public Task get(@PathVariable("id") long id) {
		return taskService.findTask(id);
	}
	
	@GetMapping()
	public long getLastId() {
		return taskService.getLastId();
	}
	
	@GetMapping("/get-tasks-by-projectId/{projectId}")
	public List<Task> getAllTasksByProjectId(@PathVariable("projectId") long projectId) {
		return taskService.getAllTasksByProjectId(projectId);
	}
	
	@PutMapping("/update-task") 
	public void updateTask(@RequestBody Task task) {
		taskService.updateTask(task);
	}
	
	@PutMapping("/delete-task/{taskId}")
	public void deleteTask(@PathVariable("taskId") long taskId) {
		taskService.deleteTask(taskId);
	}
}
