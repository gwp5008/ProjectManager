package com.fp.code.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fp.code.model.ParentTask;
import com.fp.code.service.ParentTaskService;

@RestController
@RequestMapping("/parent-tasks")
public class ParentTaskController {

	@Autowired
	ParentTaskService parentTaskService;
	
	@PostMapping()
	public void create(@RequestBody ParentTask parentTask) {
		parentTaskService.saveParentTask(parentTask);
	}

	@GetMapping("/{id}")
	public ParentTask getOne(@PathVariable("id") long id) {
		return parentTaskService.findParent(id);
	}

	@GetMapping()
	public List<ParentTask> getAll() {
		return parentTaskService.getAll();
	}
	
//	@GetMapping("/get-by-name/{parentName}")
//	public long getParentNumberByName(@PathVariable("parentName") String parentName) {
//		return parentTaskService.getParentNumberByName(parentName);
//	}
	
	@GetMapping("/get-pt-by-name/{parentName}")
	public ParentTask getParentByName(@PathVariable("parentName") String parentName) {
		return parentTaskService.getParentByName(parentName);
	}
}
