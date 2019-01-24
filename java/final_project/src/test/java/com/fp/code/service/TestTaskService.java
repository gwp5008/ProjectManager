package com.fp.code.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.fp.code.dao.TaskDAO;
import com.fp.code.model.Task;

public class TestTaskService {

	@InjectMocks
	private TaskService taskService;

	@Mock
	private TaskDAO taskDAO;

	@Mock
	private Task task;
	
	@Mock 
	ArrayList<Task> allTasks;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetLastId() {
		when(taskDAO.getLastId()).thenReturn(5L);
		
		Long lastId = taskService.getLastId();
		
		assertNotNull(lastId);
	}
	
	@Test
	public void testFindTask() {
		when(taskDAO.findTask(anyLong())).thenReturn(task);
		
		Task newTask = taskService.findTask(5L);
		
		assertNotNull(newTask);
	}
	
	@Test
	public void testGetTaskCount() {
		when(taskDAO.getTaskCount(anyLong())).thenReturn(5L);
		
		Long taskCount = taskService.getTaskCount(5L);
		
		assertNotNull(taskCount);
	}
	
	@Test
	public void testGetCompletedTaskCount() {
		when(taskDAO.getCompletedTaskCount(anyLong())).thenReturn(5L);
		
		Long taskCount = taskService.getCompletedTaskCount(5L);
		
		assertNotNull(taskCount);
	}
	
	@Test
	public void testGetAllTasksByProjectId() {
		when(taskDAO.getAllTasksByProjectId(anyLong())).thenReturn(allTasks);
		
		ArrayList<Task> newTaskList = (ArrayList<Task>) taskService.getAllTasksByProjectId(5L);
		
		assertNotNull(newTaskList);
	}
	
	@Test
	public void testSaveTask() {
		doNothing().when(taskDAO).saveTask(task);
		taskService.saveTask(task);
		
		verify(taskDAO, times(1)).saveTask(task);
	}
	
	@Test
	public void testUpdate() {
		doNothing().when(taskDAO).update(task);
		taskService.update(task);
		
		verify(taskDAO, times(1)).update(task);
	}

	@Test
	public void testDeleteTask() {
		doNothing().when(taskDAO).deleteTask(anyLong());
		taskService.deleteTask(5L);
		
		verify(taskDAO, times(1)).deleteTask(5L);
	}
}
