import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatDialog, MatDialogConfig } from "@angular/material";
import { EditTaskComponent } from './edit-task/edit-task.component';
import { SearchProjectsComponent } from '../add-project/search-projects/search-projects.component';

@Component({
  selector: 'app-view-task',
  templateUrl: './view-task.component.html',
  styleUrls: ['./view-task.component.css']
})
export class ViewTaskComponent implements OnInit {
  private allTasks: any;
  // private allParentTasks: any;
  private allTasksArray: any[] = [];
  // private allParentTasksArray: any[] = [];
  private project = {
    projectName: "",
    projectId: -1,
    startDate: "",
    endDate: "",
    priority: -1,
    status: ""
  };

  constructor(private dialog: MatDialog, private http: HttpClient) { }

  ngOnInit() {

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
      else {
        this.getAllTasks();
      }
    });
  }

  editTask(task) {
    let dialogRef = this.dialog.open(EditTaskComponent, { data: { taskToEdit: task }, width: "500px", disableClose: true });

    dialogRef.afterClosed().subscribe(result => {
      this.getAllTasks();
    })
  }

  endTask(task) {
    let delObs = this.http.put('server/tasks/delete-task/' + task.taskId, null).subscribe(
      (response) => {
        this.getAllTasks();
      });
  }

  sortByStartDate() {

    var date_sort_desc = function (a, b) {
      if (a.startDate > b.startDate) return 1;
      if (a.startDate < b.startDate) return -1;
      return 0;
    };

    this.allTasksArray.sort(date_sort_desc);
  }

  sortByEndDate() {

    var date_sort_desc = function (a, b) {
      if (a.endDate > b.endDate) return 1;
      if (a.endDate < b.endDate) return -1;
      return 0;
    };

    this.allTasksArray.sort(date_sort_desc);
  }

  sortByPriority() {

    var priority_sort_desc = function (a, b) {
      if (a.priority > b.priority) return 1;
      if (a.priority < b.priority) return -1;
      return 0;
    };

    this.allTasksArray.sort(priority_sort_desc);
  }

  // sortByCompletion() {

  //   var completion_sort_desc = function (a, b) {
  //     if (a.numCompletedTasks > b.numCompletedTasks) return 1;
  //     if (a.numCompletedTasks < b.numCompletedTasks) return -1;
  //     return 0;
  //   };

  //   this.allTasksArray.sort(completion_sort_desc);
  // }

  getAllTasks() {
    this.allTasksArray = [];

    let taskObs = this.http.get('server/tasks/get-tasks-by-projectId/' + this.project.projectId).subscribe(
      (response) => {
        this.allTasks = response;

        this.allTasks.forEach(element => {
          this.allTasksArray.push(element);
        });

        // for (let l = 0; l < this.allTasksArray.length; l++) {
        //   if (this.allTasksArray[l].parent === null) {
        //     this.allTasksArray[l].parent = {
        //       parentId: -1,
        //       parentTask: "",
        //       status: ""
        //     };
        //   }
        // }
      },
      err => console.error(err));

    // let parentTaskObs = this.http.get('server/tasks/get-tasks-by-projectId/' + this.project.projectId).subscribe(
    //   (response) => {
    //     this.allParentTasks = response;

    //     this.allParentTasks.forEach(element => {
    //       this.allParentTasksArray.push(element);
    //     });
    //   });
  }
}
