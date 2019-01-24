import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  private firstName = "";
  private lastName = "";
  private employeeId = "";
  private allUsersArray: any[];
  private matchingUsers: any[];
  private allUsersJSON: any;
  private searchUserName = "";
  private update = false;
  private user;
  private userId;
  private errorsExist: boolean;
  private errorMessages: string[];

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.getAllUsers();

    this.matchingUsers = this.allUsersArray;
    console.log(this.matchingUsers);
  }

  reset() {
    this.firstName = "";
    this.lastName = "";
    this.employeeId = "";
  }

  checkForError() {
    const NO_FIRST_NAME = "You must specify a first name.";
    const NO_LAST_NAME = "You must specify a last name.";
    const NO_EMPLOYEE_ID = "You must specify an employee id.";

    if (this.firstName === "") {
      this.errorsExist = true;
      this.errorMessages.push(NO_FIRST_NAME);
    }
    if (this.lastName === "") {
      this.errorsExist = true;
      this.errorMessages.push(NO_LAST_NAME);
    }
    if (this.employeeId === "") {
      this.errorsExist = true;
      this.errorMessages.push(NO_EMPLOYEE_ID);
    }
  }

  saveUser() {
    this.errorMessages = [];
    this.errorsExist = false;
    var user = {
      firstName: this.firstName,
      lastName: this.lastName,
      employeeId: this.employeeId,
      status: "active"
    }
    this.checkForError();

    if (this.errorsExist === false) {
      let obs = this.http.post('server/users', user).subscribe(
        (response) => {
          this.getAllUsers();
          this.matchingUsers = this.allUsersArray;
          this.reset();
        }
      );
    }
  }

  getAllUsers() {
    this.allUsersArray = [];

    let getObs = this.http.get('server/users').subscribe(
      (response) => {
        this.allUsersJSON = response;

        this.allUsersJSON.forEach(element => {
          this.allUsersArray.push(element);
        });
      },
      err => console.error(err));
  }

  sortBySearchTerm() {
    this.matchingUsers = [];

    for (let l = 0; l < this.allUsersArray.length; l++) {
      if (this.allUsersArray[l].firstName.includes(this.searchUserName) || this.allUsersArray[l].lastName.includes(this.searchUserName)) {
        this.matchingUsers.push(this.allUsersArray[l])
      }
      else if (this.searchUserName === "") {
        this.matchingUsers = this.allUsersArray;
        break;
      }
    }
  }

  setUpdateFields(userId) {
    var index;


    for (let l = 0; l < this.matchingUsers.length; l++) {
      if (this.matchingUsers[l].userId === userId) {
        index = l;
      }
    }
    this.userId = userId;
    this.firstName = this.matchingUsers[index].firstName;
    this.lastName = this.matchingUsers[index].lastName;
    this.employeeId = this.matchingUsers[index].employeeId;

    this.update = true;
  }

  updateUser() {
    this.user = {
      userId: this.userId,
      firstName: this.firstName,
      lastName: this.lastName,
      employeeId: this.employeeId
    }

    let putUser = this.http.put('server/users/update-user', this.user).subscribe(
      (response) => {
        this.getAllUsers();
        this.matchingUsers = this.allUsersArray;
      }
    );
    this.reset();

    this.update = false;
  }

  removeUser(userId) {
    var index;

    for (let l = 0; l < this.matchingUsers.length; l++) {
      if (this.matchingUsers[l].userId === userId) {
        index = l;
      }
    }
    this.matchingUsers[index].status = "inactive";

    let obs = this.http.put('server/users/remove-user/' + this.matchingUsers[index].userId, null).subscribe(
      // res => console.log(res),
      // err => console.log(err));
    );
  }

  sortByFirstName() {
    this.matchingUsers = this.allUsersArray;

    var fn_sort_desc = function (a, b) {
      if (a.firstName.localeCompare(b.firstName) === 1) return 1;
      else if (a.firstName.localeCompare(b.firstName) === -1) return -1;
      return 0;
    };
    this.matchingUsers.sort(fn_sort_desc);
  }

  sortByLastName() {
    this.matchingUsers = this.allUsersArray;

    var ln_sort_desc = function (a, b) {
      if (a.lastName.localeCompare(b.lastName) === 1) return 1;
      else if (a.lastName.localeCompare(b.lastName) === -1) return -1;
      return 0;
    };

    this.matchingUsers.sort(ln_sort_desc);
  }

  sortByEmployeeId() {
    this.matchingUsers = this.allUsersArray;

    var eId_sort_desc = function (a, b) {
      if (a.employeeId > b.employeeId) return 1;
      else if (a.employeeId < b.employeeId) return -1;
      return 0;
    };

    this.matchingUsers.sort(eId_sort_desc);
  }
}
