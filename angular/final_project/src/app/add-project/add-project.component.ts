import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogConfig } from "@angular/material";
import { HttpClient } from '@angular/common/http';
import { SearchUsersComponent } from '../add-user/search-users/search-users.component';

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent implements OnInit {
  private projectId;
  private update = false;
  private taskNumber;
  private allProjectsJSON: any;
  private allProjectsArray: any[];
  private matchingProjects: any[];
  private addProjectName = "";
  private searchProjectName = "";
  private showTheDates = false;
  private dateForm: FormGroup;
  private priority = 0;
  private formatLabel(priority: number | null) {
    if (!priority) {
      return 0;
    }
    return priority;
  }
  private dateCheck;
  stDate: any
  private fullName = {
    name: "",
    id: -1
  };
  private project;
  private errorMessages: string[];
  private errorsExist: boolean;

  constructor(private dialog: MatDialog, private http: HttpClient) {
  }

  ngOnInit() {
    this.dateForm = new FormGroup({
      startDate: new FormControl(new Date()),
      endDate: new FormControl(new Date(new Date().getTime() + 24 * 60 * 60 * 1000)),
    });

    this.getAllProjects();
    this.matchingProjects = this.allProjectsArray;
    // console.log(this.matchingProjects);
  }

  handleSelected($event) {
    if ($event.target.checked === true) {
      this.showTheDates = true;
    }
    else {
      this.showTheDates = false;
    }
    this.dateCheck = !this.dateCheck;
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

      // console.log("Here is the fullName id after closing the dialog: " + this.fullName.id);
    });
  }

  getAllProjects() {
    this.allProjectsArray = [];

    let getObs = this.http.get('server/projects').subscribe(
      (response) => {
        this.allProjectsJSON = response;

        this.allProjectsJSON.forEach(element => {
          this.allProjectsArray.push(element);
        });
        this.addTaskNumbers();
      },
      err => console.error(err));
  }

  addTaskNumbers() {
    var taskNum;
    var completedTaskNum;

    this.allProjectsArray.forEach(element => {
      let Obs = this.http.get('server/projects/task-count/' + element.projectId).subscribe(
        (response) => {
          taskNum = response;

          element["numTasks"] = taskNum;
        }
      )
    })
    this.allProjectsArray.forEach(element => {
      let Obs = this.http.get('server/projects/completed-task-count/' + element.projectId).subscribe(
        (response) => {
          completedTaskNum = response;

          element["numCompletedTasks"] = completedTaskNum;
        }
      )
    })
  }

  addProject() {
    this.errorMessages = [];
    this.errorsExist = false;

    this.project = {
      projectName: this.addProjectName,
      startDate: this.dateForm.get("startDate").value,
      endDate: this.dateForm.get("endDate").value,
      priority: this.priority,
      status: "active"
    };

    this.checkForError();

    if (this.errorsExist === false) {
      let postProject = this.http.post('server/projects/save', this.project).subscribe((response) => {
        let getProject = this.http.get('server/projects/' + this.project.projectName).subscribe(
          (response) => {
            this.projectId = response;
            this.projectId = this.projectId.projectId;
  
            // console.log("Here is the project id and user id: " + this.projectId + ", " + this.fullName.id);
            let putUser = this.http.put('server/users/add-project-manager/' + this.projectId + '/' + this.fullName.id, null).subscribe();

            this.reset();
          });
        this.getAllProjects();
        this.matchingProjects = this.allProjectsArray;
      });
    }
  }

  checkForError() {
    const NO_PROJECT_NAME = "You must specify a project name.";
    const IMPOSSIBLE_DATES = "Project start date cannot be after project end date.";
    const NO_PRIORITY = "You must specify a project priority.";
    const NO_MANAGER = "You must specify a project manager.";

    var startDate = new Date(this.dateForm.get("startDate").value).getTime();
    var endDate = new Date(this.dateForm.get("endDate").value).getTime();

    if (this.addProjectName === "") {
      this.errorsExist = true;
      this.errorMessages.push(NO_PROJECT_NAME);
    }
    if (startDate > endDate) {
      this.errorsExist = true;
      this.errorMessages.push(IMPOSSIBLE_DATES);
    }
    if (this.priority === 0) {
      this.errorsExist = true;
      this.errorMessages.push(NO_PRIORITY);
    }
    if (this.fullName.name === "") {
      this.errorsExist = true;
      this.errorMessages.push(NO_MANAGER);
    }
  }

  reset() {
    this.addProjectName = "";
    this.dateForm.get("startDate").setValue(new Date());
    this.dateForm.get("endDate").setValue(new Date(new Date().getTime() + 24 * 60 * 60 * 1000));
    this.dateCheck = false;
    this.showTheDates = false;
    this.fullName = {
      name: "",
      id: -1
    }
    this.priority = 0;
  }

  getTaskCount(theProject): number {
    let getObs = this.http.get('server/projects/task-count', theProject).subscribe(
      (response) => {
        this.taskNumber = response;
      },
      err => console.error(err));
    return this.taskNumber;
  }

  sortBySearchTerm() {
    this.matchingProjects = [];

    for (let l = 0; l < this.allProjectsArray.length; l++) {
      if (this.allProjectsArray[l].projectName.includes(this.searchProjectName)) {
        this.matchingProjects.push(this.allProjectsArray[l])
      }
      else if (this.searchProjectName === "") {
        this.matchingProjects = this.allProjectsArray;
        break;
      }
    }
  }

  sortByStartDate() {
    this.matchingProjects = this.allProjectsArray;

    var date_sort_desc = function (a, b) {
      if (a.startDate > b.startDate) return 1;
      if (a.startDate < b.startDate) return -1;
      return 0;
    };

    this.matchingProjects.sort(date_sort_desc);
  }

  sortByEndDate() {
    this.matchingProjects = this.allProjectsArray;

    var date_sort_desc = function (a, b) {
      if (a.endDate > b.endDate) return 1;
      if (a.endDate < b.endDate) return -1;
      return 0;
    };

    this.matchingProjects.sort(date_sort_desc);
  }

  sortByPriority() {
    this.matchingProjects = this.allProjectsArray;

    var priority_sort_desc = function (a, b) {
      if (a.priority > b.priority) return 1;
      if (a.priority < b.priority) return -1;
      return 0;
    };

    this.matchingProjects.sort(priority_sort_desc);
  }

  sortByCompletion() {
    this.matchingProjects = this.allProjectsArray;

    var completion_sort_desc = function (a, b) {
      if (a.numCompletedTasks > b.numCompletedTasks) return 1;
      if (a.numCompletedTasks < b.numCompletedTasks) return -1;
      return 0;
    };

    this.matchingProjects.sort(completion_sort_desc);
  }

  removeProject(projectName) {
    var index;

    // console.log(this.matchingProjects);

    for (let l = 0; l < this.matchingProjects.length; l++) {
      if (this.matchingProjects[l].projectName === projectName) {
        index = l;
        // console.log(index);
      }
    }
    this.matchingProjects[index].status = "inactive";

    let obs = this.http.put('server/projects/remove-project/' + this.matchingProjects[index].projectId, null).subscribe(
      // res => console.log(res),
      // err => console.log(err));
    );
  }

  setUpdateFields(projectId) {
    var index;
    var user;
    this.projectId = projectId;

    for (let l = 0; l < this.matchingProjects.length; l++) {
      if (this.matchingProjects[l].projectId === projectId) {
        index = l;
      }
    }
    // console.log(this.matchingProjects);
    this.addProjectName = this.matchingProjects[index].projectName;
    this.dateForm.get("startDate").setValue(this.addDays(this.matchingProjects[index].startDate, 1));
    this.dateForm.get("endDate").setValue(this.addDays(this.matchingProjects[index].endDate, 1));

    this.dateCheck = true;
    this.showTheDates = true;
    this.priority = this.matchingProjects[index].priority;
    // console.log(this.projectId);
    let getUser = this.http.get('server/users/project/' + this.projectId).subscribe(
      (response) => {
        user = response;

        this.fullName = {
          name: user.firstName + ", " + user.lastName,
          id: user.userId
        }
      })
    this.update = true;
  }

  addDays(dateToChange, days): Date {
    var result = new Date(dateToChange);
    result.setDate(result.getDate() + days);
    return result;
  }

  updateProject() {
    this.project = {
      projectId: this.projectId,
      projectName: this.addProjectName,
      startDate: this.dateForm.get("startDate").value,
      endDate: this.dateForm.get("endDate").value,
      priority: this.priority,
      status: "active"
    };
    let putProject = this.http.put('server/projects/update-project', this.project).subscribe(
      (response) => {
        this.getAllProjects();
        this.matchingProjects = this.allProjectsArray;
      }
    );
    this.reset();

    this.update = false;
  }
}
