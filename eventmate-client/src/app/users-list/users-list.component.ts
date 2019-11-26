import { UserService } from './../services/user.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '../../../node_modules/@angular/router';
import { User } from '../domain/user';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {

  users: User[];
  errorMessage: string;

  constructor(private userService: UserService,
    private router: Router) { }

  ngOnInit() {

    this.userService.getAll().subscribe(
      data => {
        this.users = data;
        console.log(this.users);
      },
      error => {
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
          this.errorMessage = error.error.message;
        });
    }
  }
}
