package com.fp.code.dao;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyList;
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

import com.fp.code.model.Task;
import com.fp.code.repository.TaskRepository;

public class TestTaskDAO {

	@InjectMocks
	private TaskDAO taskDAO;

	@Mock
	private TaskRepository taskRepo;
	
	@Mock
	private Task task;
	
	@Mock
	private Task updatedTask;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFindTask() {
		when(taskRepo.getOne(anyLong())).thenReturn(task);
		
		Task newTask = taskDAO.findTask(13);
		assertNotNull(newTask);
	}
	
	@Test
	public void testGetTaskCount() {
		when(taskRepo.getTaskCount(anyLong())).thenReturn(anyLong());
		
		Long newLong = taskDAO.getTaskCount(13L);
		
		assertNotNull(newLong);
	}
	
	@Test
	public void testGetCompletedTaskCount() {
		when(taskRepo.getTaskCount(anyLong())).thenReturn(anyLong());
		
		Long newLong = taskDAO.getTaskCount(13L);
		
		assertNotNull(newLong);
	}
	
	@Test
	public void testGetAllTasksByProjectId() {
		when(taskRepo.findAllByProjectId(anyLong())).thenReturn(anyList());
		
		ArrayList<Task> newList = (ArrayList<Task>) taskDAO.getAllTasksByProjectId(13L);
		
		assertNotNull(newList);
	}
	
	@Test
	public void testDeleteTask() {
		doNothing().when(taskRepo).deleteTask(anyLong());
		taskDAO.deleteTask(5L);

		verify(taskRepo, times(1)).deleteTask(5L);
	}
}
