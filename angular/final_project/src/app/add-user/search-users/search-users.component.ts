import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { AddProjectComponent } from 'src/app/add-project/add-project.component';

@Component({
  selector: 'app-search-users',
  templateUrl: './search-users.component.html',
  styleUrls: ['./search-users.component.css']
})
export class SearchUsersComponent implements OnInit {
  // private fullName = "";
  private fullName: Object;
  private selectedRows;
  private usersToDelete: number[];
  private result;
  private gridApi;
  private gridColumnApi;
  private rowSelection;
  private rowData;
  private filteredRowData;
  private searchText: string = "";
  private columnDefs = [
    { headerName: 'First Name', field: 'firstName', hide: false },
    { headerName: 'Last Name', field: 'lastName', hide: false },
    { field: 'employeeId', hide: true },
    { field: 'userId', hide: true },
    { field: 'projectUsers', hide: true },
    { field: 'task', hide: true }
  ];

  constructor(private http: HttpClient, public dialogRef: MatDialogRef<AddProjectComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {

    this.rowSelection = "single";
  }

  closeDialog() {
    this.dialogRef.close(this.fullName);
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
      if (this.result[l].firstName.includes(this.searchText) || this.result[l].lastName.includes(this.searchText)) {

      }
      else if (this.searchText == "") {
        console.log("breaking out");
        break;
      }

      else {
        this.usersToDelete.push(l);
        this.deleteMsg(this.usersToDelete);
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

    this.fullName = {
      name: this.selectedRows[0].firstName + ", " + this.selectedRows[0].lastName,
      id: this.selectedRows[0].userId
    };

    console.log(this.fullName);
  }

  ngOnInit() {
    this.result = [];
    this.getAllUsers();
  }

  getAllUsers() {

    let obs = this.http.get('server/users').subscribe(
      (response) => {
        this.rowData = response;

        this.usersToDelete = [this.rowData.keys.length];
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
