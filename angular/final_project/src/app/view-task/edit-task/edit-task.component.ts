import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormControl, FormGroup } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observer, throwError } from "rxjs";
import { Observable } from "rxjs";
import { catchError } from "rxjs/operators";
import { ViewTaskComponent } from '../view-task.component';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-edit-task',
  templateUrl: './edit-task.component.html',
  styleUrls: ['./edit-task.component.css']
})

export class EditTaskComponent implements OnInit {
  private dateForm: FormGroup;
  private priority;
  private parentTask;
  private parentTaskNum;
  private noParentTask = false;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private http: HttpClient, public dialogRef: MatDialogRef<ViewTaskComponent>) {
    this.priority = this.data.taskToEdit.priority;
    if (this.data.taskToEdit.parent === null) {
      this.data.taskToEdit.parent = {
        parentTask: "(No Parent)"
      }
      this.parentTask = this.data.taskToEdit.parent.parentTask;
    }
    else {
      this.parentTask = this.data.taskToEdit.parent.parentTask;
    }

    // console.log("This is the value of this.parentTask: ");
    // console.log(this.parentTask);
  }

  ngOnInit() {

    this.dateForm = new FormGroup({
      startDate: new FormControl(this.addDays(this.data.taskToEdit.startDate, 1)),
      endDate: new FormControl(this.addDays(this.data.taskToEdit.endDate, 1)),
    });
  }

  closeDialog() {
    this.dialogRef.close();
  }

  // handleUpdateError(errorResponse: HttpErrorResponse, scope: any) {
  //   if (errorResponse.error instanceof ErrorEvent) {
  //     console.error("Client side error: ", errorResponse.error.message);
  //   }
  //   else {
  //     this.noParentTask = true;
  //     console.error("Server side error: ", errorResponse);
  //   }

  //   this.parentTaskNum = null;
  //   return throwError("That parent task name does not exist. Please try another or reenter '(No Parent)'.");
  // }

  updateTask() {
    this.noParentTask = false;
    let taskId;
    // let projectId;

    if (this.parentTask === "(No Parent)") {
      this.parentTaskNum = null;

      this.performUpdate();
    }
    else {
      let scope = this;
      this.http.get('server/parent-tasks/get-pt-by-name/' + this.parentTask).subscribe (
        // .pipe(catchError((err, scope) => this.handleUpdateError(err, scope))).subscribe(
          (response) => {
            this.parentTaskNum = response;

            if (this.parentTaskNum === null) {
              this.noParentTask = true;
            }
            else {
              this.performUpdate();
            }

            // console.log("Here is this.parentTask: ");
            // console.log(this.parentTask);

            // console.log("Here is this.parentTaskNum: ");
            // console.log(this.parentTaskNum);

            
            // performUpdate() code used to go here.
          });
    }
  }

  performUpdate() {
    let projectId;
    
    this.http.get('server/projects/' + this.data.taskToEdit.projectTasks.projectName).subscribe(
      (response) => {
        projectId = response;
        console.log("Here is the projectId: ");
        console.log(projectId);

        var taskToUpdate = {
          taskId: this.data.taskToEdit.taskId,
          parent: this.parentTaskNum,
          projectTasks: projectId,
          task: this.data.taskToEdit.task,
        
          startDate: this.dateForm.get("startDate").value,
          endDate: this.dateForm.get("endDate").value,
          priority: this.priority,
          status: this.data.taskToEdit.status,
        };

        console.log("Here is the new version of taskToUpdate: ");
        console.log(taskToUpdate);

        let updateObs = this.http.put('server/tasks/update-task', taskToUpdate).subscribe(
          (response) => {
            // console.log("Here is this.data.taskToEdit: ");
            // console.log(this.data.taskToEdit);

            // console.log("Here is taskToUpdate: ");
            // console.log(taskToUpdate);
          });
      });
    this.closeDialog();
  }

  addDays(dateToChange, days): Date {
    var result = new Date(dateToChange);
    result.setDate(result.getDate() + days);
    return result;
  }
}
