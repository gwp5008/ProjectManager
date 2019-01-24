import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from "@angular/common/http";
import { MaterialModule } from "./material/material.module";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import { ViewTaskComponent } from './view-task/view-task.component';
import { AddTaskComponent } from './add-task/add-task.component';
import { AddProjectComponent } from './add-project/add-project.component';
import { AddUserComponent } from './add-user/add-user.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { AgGridModule } from "ag-grid-angular";
import { SearchParentTasksComponent } from './add-task/search-parent-tasks/search-parent-tasks.component';
import { EditTaskComponent } from './view-task/edit-task/edit-task.component';
import { AppRoutingModule } from './/app-routing.module';
import { SearchUsersComponent } from './add-user/search-users/search-users.component';
import { SearchProjectsComponent } from './add-project/search-projects/search-projects.component';

@NgModule({
  declarations: [
    AppComponent,
    ViewTaskComponent,
    AddTaskComponent,
    AddProjectComponent,
    AddUserComponent,
    SearchUsersComponent,
    SearchProjectsComponent,
    SearchParentTasksComponent,
    EditTaskComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MaterialModule,
    BrowserAnimationsModule,
    AgGridModule.withComponents([AppComponent]),
    AppRoutingModule
  ],
  entryComponents: [
    SearchUsersComponent,
    SearchProjectsComponent,
    SearchParentTasksComponent,
    EditTaskComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
