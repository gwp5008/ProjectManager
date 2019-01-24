package com.fp.code.dao;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fp.code.model.ParentTask;
import com.fp.code.model.Project;
import com.fp.code.repository.ProjectRepository;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

public class TestProjectDAO {
	@InjectMocks
	private ProjectDAO projectDAO;

	@Mock
	private ProjectRepository projectRepository;

	@Mock
	private Project project;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindProject() {
		when(projectRepository.findByProjectName(anyString())).thenReturn(project);
		
		Project newProject = projectRepository.findByProjectName("some project");
		assertNotNull(newProject);
	}
	
	@Test
	public void testGetAll() {
		when(projectRepository.findAll()).thenReturn(new ArrayList<Project>());
		
		List<Project> projectList = projectDAO.getAllProjects();
		assertNotNull(projectList);
	}
}
