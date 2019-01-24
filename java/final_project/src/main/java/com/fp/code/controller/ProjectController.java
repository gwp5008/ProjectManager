package com.fp.code.controller;

import java.util.List;

import javax.ws.rs.Produces;

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

import com.fp.code.model.Project;
import com.fp.code.service.ProjectService;
import com.fp.code.service.TaskService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@Autowired
	TaskService taskService;

	@PostMapping("/save")
//	@Consumes("MediaType.APPLICATION_JSON")
//	@ResponseStatus(HttpStatus.OK)
	public void createProject(@RequestBody Project project) {
//		return Math.toIntExact(productService.saveProject(project));
		projectService.saveProject(project);
	}

//	@Produces("MediaType.APPLICATION_JSON")
//	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{name}")
	public Project getProject(@PathVariable("name") String name) {
		return projectService.findProject(name);
	}

	@GetMapping()
	public List<Project> getAll() {
		return projectService.getAllProjects();
	}
	
	@PutMapping("/remove-project/{projectId}")
	public void removeProject(@PathVariable int projectId) {
		projectService.removeProject(projectId);
	}
	
	@PutMapping("/update-project") 
	public void updateProject(@RequestBody Project project) {
		projectService.updateProject(project);
	}

	@GetMapping("/task-count/{id}")
	public Long getTaskCount(@PathVariable("id") Long id) {
		return taskService.getTaskCount(id);
	}

	@GetMapping("/completed-task-count/{id}")
	public Long getCompletedTaskCount(@PathVariable("id") Long id) {
		return taskService.getCompletedTaskCount(id);
	}
}
