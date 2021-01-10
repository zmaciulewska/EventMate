import { UserService } from './../services/user.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '../../../node_modules/@angular/router';
import { User } from '../domain/user';
import { Page } from '../domain/page';
import { UserSearchForm } from './user-search-form';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {

  users: User[];
  isError = false;
  errorMessage: string;

  currentPage = 1;
  pageSize = 5;
  currentData: Page<User>;
  config: any;

  searchForm: UserSearchForm;
  parameterMap: Map<String, Object>;

  constructor(private userService: UserService,
    private router: Router) { }

  ngOnInit() {
    this.prepareSerachForm();
    this.loadUsers();
  }

  prepareSerachForm() {
    this.searchForm = new UserSearchForm();
  }

  prepareSearchCriteria() {
    this.parameterMap = new Map();
    this.parameterMap.set('username', this.searchForm.username);
    this.parameterMap.set('email', this.searchForm.email);
  }

  pageChange(newPage: number) {
    console.log('newPage: ' + newPage);
    this.currentPage = newPage;
    this.loadUsers();
  }

  setConfig() {
    this.config = {
      itemsPerPage: 5,
      currentPage: this.currentPage,
      totalItems: this.currentData.totalElements,
    };
  }


  clearFilters() {
    this.prepareSerachForm();
    this.loadUsers();
  }

  loadUsers() {
    this.prepareSearchCriteria();
    this.userService.getAllPaginated((this.currentPage - 1), this.pageSize, this.parameterMap).subscribe(
      data => {
        this.users = data.content;
        this.currentData = data;
        this.setConfig();
        console.log(this.currentData);
      },
      error => {
        this.isError = true;
        this.errorMessage = error.error.message;
      });
  }


  deleteUser(id: number) {
    if (confirm('Czy chcesz usunąć tego użytkownika?')) {
      console.log('Delete confirmed');
      this.userService.delete(id).subscribe(
        res => {
          console.log('User deleted');
          this.users = this.users.filter(item => item.id !== id);
          // this.router.navigate(['/home']);
        },
        error => {
          console.log('User not deleted');
          // this.isDeleteFailed = true;
          this.isError = true;
          this.errorMessage = error.error.message;
        });
    }
  }
}
