import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { AddTaskComponent } from 'src/app/add-task/add-task.component';

@Component({
  selector: 'app-search-projects',
  templateUrl: './search-projects.component.html',
  styleUrls: ['./search-projects.component.css']
})
export class SearchProjectsComponent implements OnInit {
  private project;
  private selectedRows;
  private projectsToDelete: number[];
  private result;
  private gridApi;
  private gridColumnApi;
  private rowSelection;
  private rowData;
  private filteredRowData;
  private searchText: string = "";
  private columnDefs = [
    { headerName: 'Project Name', field: 'projectName', hide: false },
    { headerName: 'Start Date', field: 'startDate', hide: false },
    { headerName: 'End Date', field: 'endDate', hide: false },
    { field: 'priority', hide: true },
    { field: 'status', hide: true },
    { field: 'projectId', hide: true }
  ];
  constructor(private http: HttpClient, public dialogRef: MatDialogRef<AddTaskComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {

    this.rowSelection = "single";
  }

  closeDialog() {
    this.dialogRef.close(this.project);
  }

  onGridReady(params) {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
  }

  filterRowData() {
    if (this.result.length != this.rowData.length) {
      this.rowData.forEach(element => {
        this.result.push(element);
      });
    }

    console.log("search: ", this.searchText);
    for (let l = 0; l < this.result.length; l++) {
      if (this.result[l].projectName.includes(this.searchText)) {

      }
      else if (this.searchText == "") {
        console.log("breaking out");
        break;
      }

      else {
        this.projectsToDelete.push(l);
        this.deleteMsg(this.projectsToDelete);
      }
    };

    console.log("showing data");
    this.gridApi.setRowData(this.result);
  }

  deleteMsg(elements: number[]) {
    for (let l = 0; l < elements.length; l++) {
      if (l !== -1) {
        this.result.splice(l, 1);
      }
    }
  }

  onSelectionChanged() {
    this.selectedRows = this.gridApi.getSelectedRows();

    this.project = {
      projectName: this.selectedRows[0].projectName,
      projectId: this.selectedRows[0].projectId,
      startDate: this.selectedRows[0].startDate,
      endDate: this.selectedRows[0].endDate,
      priority: this.selectedRows[0].priority,
      status: this.selectedRows[0].status
    };
  }

  ngOnInit() {
    this.result = [];
    this.getAllProjects();
  }

  getAllProjects() {

    let obs = this.http.get('server/projects').subscribe(
      (response) => {
        this.rowData = response;

        this.projectsToDelete = [this.rowData.keys.length];
        console.log(this.rowData);
        this.rowData.forEach(element => {
          this.result.push(element);
        })
        console.log("Here is the result array: ");
        console.log(this.result);
      },
      err => console.error(err));
  }
}
