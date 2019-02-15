package com.fp.code.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "task")
// @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class,
// property = "@id")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	private Long taskId;

	@Column(name = "task")
	private String task;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "priority")
	private int priority;

	@Column(name = "status")
	private String status;

//	 @JsonManagedReference
//	@JsonBackReference
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "parent_id")
	private ParentTask parent;

//	 @JsonManagedReference(value = "project-tasks")
//	@JsonBackReference
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "project_id")
	private Project projectTasks;

	// @JsonIgnore
	// @OneToMany(mappedBy = "task", cascade = { CascadeType.PERSIST,
	// CascadeType.MERGE, CascadeType.DETACH,
	// CascadeType.REFRESH })
	// private List<Users> users;

	public Task() {

	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ParentTask getParent() {
		return parent;
	}

	public void setParent(ParentTask parent) {
		this.parent = parent;
	}

	public Long getTaskId() {
		return taskId;
	}

	public Project getProjectTasks() {
		return projectTasks;
	}

	public void setProjectTasks(Project projectTasks) {
		this.projectTasks = projectTasks;
	}

	// public List<Users> getUsers() {
	// return users;
	// }
	//
	// public void setUsers(List<Users> users) {
	// this.users = users;
	// }
}
