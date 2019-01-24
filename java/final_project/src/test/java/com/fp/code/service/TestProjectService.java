package com.fp.code.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fp.code.dao.ProjectDAO;
import com.fp.code.model.Project;

public class TestProjectService {

	@InjectMocks
	private ProjectService projectService;

	@Mock
	private ProjectDAO projectDAO;

	@Mock
	private Project project;
	
	@Mock
	private ArrayList<Project> allProjects;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFindProject() {
		when(projectDAO.findProject(anyString())).thenReturn(project);
		
		Project newProject = projectService.findProject("project");
		
		assertNotNull(newProject);
	}
	
	@Test
	public void testGetAllProjects() {
		when(projectDAO.getAllProjects()).thenReturn(allProjects);
		
		ArrayList<Project> projects = (ArrayList<Project>) projectService.getAllProjects();
		
		assertNotNull(projects);
	}
	
	@Test
	public void testSaveProject() {
		doNothing().when(projectDAO).saveProject(project);
		projectService.saveProject(project);
		
		verify(projectDAO, times(1)).saveProject(project);
	}
	
	@Test
	public void testRemoveProject() {
		doNothing().when(projectDAO).removeProject(anyInt());
		projectService.removeProject(5);
		
		verify(projectDAO, times(1)).removeProject(5);
	}

	@Test
	public void testUpdateProject() {
		doNothing().when(projectDAO).updateProject(project);
		projectService.updateProject(project);
		
		verify(projectDAO, times(1)).updateProject(project);
	}
}
