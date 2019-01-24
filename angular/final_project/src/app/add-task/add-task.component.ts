import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from "@angular/material";
import { HttpClient } from '@angular/common/http';
import { SearchParentTasksComponent } from './search-parent-tasks/search-parent-tasks.component';
import { FormControl, FormGroup } from '@angular/forms';
import { SearchUsersComponent } from '../add-user/search-users/search-users.component';
import { SearchProjectsComponent } from '../add-project/search-projects/search-projects.component';

@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.css']
})
export class AddTaskComponent implements OnInit {
  private taskName;
  private showTheDates = false;
  private dateForm: FormGroup;
  private dateCheck;
  private parentCheck: boolean = false;
  private errorMessages: string[];
  private errorsExist: boolean;
  private fullName = {
    name: "",
    id: -1
  };
  private project = {
    projectName: "",
    projectId: -1,
    startDate: "",
    endDate: "",
    priority: -1,
    status: ""
  };
  private parentTask = {
    parentTask: "",
    parentId: -1,
    status: ""
  }
  private priority = 0;
  private formatLabel(priority: number | null) {
    if (!priority) {
      return 0;
    }
    return priority;
  }

  constructor(private dialog: MatDialog, private http: HttpClient) { }

  ngOnInit() {
    this.dateForm = new FormGroup({
      startDate: new FormControl(new Date()),
      endDate: new FormControl(new Date(new Date().getTime() + 24 * 60 * 60 * 1000)),
    });
  }

  addTask() {
    var parentTask;
    var task;
    var lastTask = 0;

    this.errorMessages = [];
    this.errorsExist = false;

    if (this.parentCheck === false) {

      task = {
        task: this.taskName,
        startDate: this.dateForm.get("startDate").value,
        endDate: this.dateForm.get("endDate").value,
        priority: this.priority,
        status: "active"
      };

      this.checkForError();

      if (this.errorsExist === false) {
        if (this.parentTask.parentTask === "" && this.project.projectName !== "") {
          let postObs = this.http.post('server/tasks/' + this.project.projectName, task).subscribe(
            (response) => {
              this.reset();
            });
        }
        else {
          let obs = this.http.post('server/tasks/' + this.parentTask.parentId + "/" + this.project.projectName, task).subscribe(
            (response) => {
              this.reset();

              let getObs = this.http.get('server/tasks/').subscribe(
                (response) => {
                  console.log("This is the value of the latest task id: " + response);
                  let putObs = this.http.put('server/users/assign-task-manager/' + this.fullName.id + '/' + response, null).subscribe();
                });
            }
          );
        }
      }
    }

    else {
      parentTask = {
        parentTask: this.taskName,
        status: "active"
      };

      this.checkForError();

      if (this.errorsExist === false) {
        let obs = this.http.post('server/parent-tasks', parentTask).subscribe(
          (response) => {
            this.reset();
          }
        );
      }
    }
  }

  reset() {
    this.clearParentTask();
    this.clearProject();
    this.taskName = "";
    this.dateForm.get("startDate").setValue(new Date());
    this.dateForm.get("endDate").setValue(new Date(new Date().getTime() + 24 * 60 * 60 * 1000));
    this.priority = 0;
    this.fullName.name = "";
  }


  checkForError() {
    const NO_PROJECT_ID = "You must select a project.";
    const NO_TASK_NAME = "You must specify a task name.";
    const IMPOSSIBLE_DATES = "Task start date cannot be after project end date.";
    const NO_PRIORITY = "You must specify a project priority.";
    const NO_USER = "You must select a user to manage the task.";

    var startDate = new Date(this.dateForm.get("startDate").value).getTime();
    var endDate = new Date(this.dateForm.get("endDate").value).getTime();

    if (this.parentCheck === false) {
      if (this.project.projectName === "") {
        this.errorsExist = true;
        this.errorMessages.push(NO_PROJECT_ID);
      }
      if (startDate > endDate) {
        this.errorsExist = true;
        this.errorMessages.push(IMPOSSIBLE_DATES);
      }
      if (this.priority === 0) {
        this.errorsExist = true;
        this.errorMessages.push(NO_PRIORITY);
      }
      if (this.taskName === undefined) {
        this.errorsExist = true;
        this.errorMessages.push(NO_TASK_NAME);
      }
      if (this.fullName.name === "") {
        this.errorsExist = true;
        this.errorMessages.push(NO_USER);
      }
    }
    else {
      if (this.taskName === "") {
        this.errorsExist = true;
        this.errorMessages.push(NO_TASK_NAME);
      }
    }
  }

  clearProject() {
    this.project = {
      projectName: "",
      projectId: -1,
      startDate: "",
      endDate: "",
      priority: -1,
      status: ""
    }
  }

  searchProjects() {
    let dialogRef = this.dialog.open(SearchProjectsComponent, { data: {}, width: "500px" });

    dialogRef.afterClosed().subscribe(result => {

      this.project = result;

      if (this.project === undefined) {
        this.clearProject();
      }
    });
  }

  clearParentTask() {
    this.parentTask = {
      parentTask: "",
      parentId: -1,
      status: ""
    }
  }

  searchParentTasks() {
    let dialogRef = this.dialog.open(SearchParentTasksComponent, { data: {}, width: "500px" });

    dialogRef.afterClosed().subscribe(result => {

      this.parentTask = result;

      if (this.parentTask === undefined) {
        this.clearParentTask();
      }
    });
    console.log(this.parentTask);
  }

  searchUsers() {
    let dialogRef = this.dialog.open(SearchUsersComponent, { data: {}, width: "500px" });

    dialogRef.afterClosed().subscribe(result => {

      this.fullName = result;

      if (this.fullName === undefined) {
        this.fullName = {
          name: "",
          id: -1
        }
      }
    });
  }

  handleParent($event) {
    if ($event.target.checked === true) {
      this.parentCheck = true;
    }
    else {
      this.parentCheck = false;
    }
  }

  handleDates($event) {
    if ($event.target.checked === true) {
      this.showTheDates = true;
    }
    else {
      this.showTheDates = false;
    }
    this.dateCheck = !this.dateCheck;
  }
}
