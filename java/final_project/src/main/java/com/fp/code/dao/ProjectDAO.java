package com.fp.code.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fp.code.exceptions.NotFoundException;
import com.fp.code.model.Project;
import com.fp.code.repository.ProjectRepository;

@Component
public class ProjectDAO {

	@Autowired
	ProjectRepository projectRepository;

	public void saveProject(Project project) {
		projectRepository.save(project);
	}

	public Project findProject(String projectName) {
//		return projectRepository.findByProjectName(projectName).orElseThrow(() -> new NotFoundException("Project not found"));
		return projectRepository.findByProjectName(projectName);
	}

	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}
	
	public void removeProject(int projectId) {
//		Project project = projectRepository.findByProjectId(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
		Project project = projectRepository.findByProjectId(Long.valueOf(projectId));
		project.setStatus("inactive");
		saveProject(project);
	}
	
	public void updateProject(Project project) {
		saveProject(project);
	}
}
