import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { TokenStorageService } from '../auth/token-storage.service';
import { AuthService } from '../auth/auth.service';
import { User } from '../domain/user';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  board: string;
  errorMessage: string;

  currentUser: User;
  userId: number;

  constructor(private userService: UserService,
    private tokenStorage: TokenStorageService,
    private authService: AuthService
  ) { }

  ngOnInit() {

    this.authService.getCurrentUser().subscribe(
      data => {
        this.currentUser = data;
        console.log('current usr: ' + this.currentUser);
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    );
    /* this.userService.getUserBoard().subscribe(
      data => {
        this.board = data;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    ); */
  }
}
