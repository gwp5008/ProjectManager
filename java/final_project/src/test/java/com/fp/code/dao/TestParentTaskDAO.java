package com.fp.code.dao;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fp.code.model.ParentTask;
import com.fp.code.repository.ParentTaskRepository;

public class TestParentTaskDAO {
	@InjectMocks
	private ParentTaskDAO ptDAO;

	@Mock
	private ParentTaskRepository ptRepo;
	
	@Mock
	private ParentTask pt;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindParent() {
		when(ptRepo.getOne(anyLong())).thenReturn(pt);
		
		ParentTask newPT = ptDAO.findParent(13);
		assertNotNull(newPT);
	}

	@Test
	public void testGetAll() {
		when(ptRepo.findAll()).thenReturn(new ArrayList<ParentTask>());
		
		List<ParentTask> ptList = ptDAO.getAll();
		assertNotNull(ptList);
	}
	
	@Test
	public void testFindParentByName() {
		when(ptRepo.getParentNumberByName(anyString())).thenReturn(anyLong());

		long someLong = ptDAO.getParentNumberByName("name");

		assertNotNull(someLong);
	}
	
	@Test 
	public void testGetParentByName() {
		when(ptRepo.findByParentTask(anyString())).thenReturn(pt);
		
		ParentTask newPT = ptDAO.getParentByName("name");
		assertNotNull(newPT);
	}
	
	
}
