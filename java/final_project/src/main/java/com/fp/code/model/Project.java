package com.fp.code.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "project")
//@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private Long projectId;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "priority")
	private int priority;
	
	@Column(name = "status")
	private String status;

//	@JsonManagedReference
// @JsonBackReference(value = "project-tasks")
	@JsonIgnore
	@OneToMany(mappedBy = "projectTasks", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<Task> tasks;

//	@JsonIgnore
//	@OneToMany(mappedBy = "projectUsers", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
//			CascadeType.REFRESH })
//	private List<Users> users;

	public Project() {
		super();
	}

	public Project(Long projectId, String project, Date startDate, Date endDate, int priority) {
		super();
		this.projectId = projectId;
		this.projectName = project;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getProjectId() {
		return projectId;
	}

//	public List<Users> getUsers() {
//		return users;
//	}
//
//	public void setUsers(List<Users> users) {
//		this.users = users;
//	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String project) {
		this.projectName = project;
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

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
}
