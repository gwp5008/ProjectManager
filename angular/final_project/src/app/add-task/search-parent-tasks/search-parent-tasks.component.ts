import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { AddTaskComponent } from '../add-task.component';

@Component({
  selector: 'app-search-parent-tasks',
  templateUrl: './search-parent-tasks.component.html',
  styleUrls: ['./search-parent-tasks.component.css']
})
export class SearchParentTasksComponent implements OnInit {
  private parentTask;
  private selectedRows;
  private tasksToDelete: number[];
  private result;
  private gridApi;
  private gridColumnApi;
  private rowSelection;
  private rowData;
  private filteredRowData;
  private searchText: string = "";
  private columnDefs = [
    { headerName: 'Parent Task Name', field: 'parentTask', hide: false },
    { field: 'parentId', hide: true },
    { field: 'status', hide: true }
  ];
  constructor(private http: HttpClient, public dialogRef: MatDialogRef<AddTaskComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {

    this.rowSelection = "single";
  }

  closeDialog() {
    this.dialogRef.close(this.parentTask);
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
      if (this.result[l].parentTask.includes(this.searchText)) {

      }
      else if (this.searchText == "") {
        console.log("breaking out");
        break;
      }

      else {
        this.tasksToDelete.push(l);
        this.deleteMsg(this.tasksToDelete);
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

    this.parentTask = {
      parentTask: this.selectedRows[0].parentTask,
      parentId: this.selectedRows[0].parentId,
      status: this.selectedRows[0].status
    };

    // console.log(this.parentTask);
  }

  ngOnInit() {
    this.result = [];
    this.getAllParents();
  }

  getAllParents() {

    let obs = this.http.get('server/parent-tasks').subscribe(
      (response) => {
        this.rowData = response;

        this.tasksToDelete = [this.rowData.keys.length];
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
