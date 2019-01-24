package com.fp.code.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fp.code.dao.ProjectDAO;
import com.fp.code.model.Project;

@Service
@Transactional
public class ProjectService {
	
	@Autowired
	ProjectDAO projectDAO;

	public void saveProject(Project project) {
		projectDAO.saveProject(project);
	}

	public Project findProject(String projectName) {
		return projectDAO.findProject(projectName);
	}

	public List<Project> getAllProjects() {
		return projectDAO.getAllProjects();
	}
	
	public void removeProject(int project) {
		projectDAO.removeProject(project);
	}
	
	public void updateProject(Project project) {
		projectDAO.updateProject(project);
	}
}
