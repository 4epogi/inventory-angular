import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthResponseData } from 'src/app/dto/auth-response-data';
import { User } from 'src/app/models/user';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  isLoading: boolean = false;
  error: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  onLogin(form: NgForm) {
    if (!form.valid) {
      return;
    }
    const login: string = form.value.login;
    const password: string = form.value.password;
    console.log(form.value);
    this.isLoading = true;
    this.authService.login(login, password)
      .subscribe({
        next: (value: AuthResponseData) => {
          this.error = '';
          this.isLoading = false;
          this.router.navigate(['/home']);
        },
        error: (error:any) => {
          // console.log(error.status);
          this.error = error.message;
          this.isLoading = false;
        }
      });
    // .subscribe(next?: ((data: User) => {
    //   console.log(data),
    // }),
    //   error: ((error: any) => {

    //   }
    // )
    //   .subscribe(data => {
    //     console.log(data);
    //   },
    //   error => {
    //     console.log(error);
    //   });
    form.reset();
  }

}
