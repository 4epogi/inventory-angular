import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Department } from 'src/app/models/department';
import { ErrorResponse } from 'src/app/models/error-response';
import { DepartmentService } from '../department.service';

@Component({
  selector: 'app-edit-department',
  templateUrl: './edit-department.component.html',
  styleUrls: ['./edit-department.component.css'],
})
export class EditDepartmentComponent implements OnInit, OnDestroy {
 
  department: Department = {id: 0, name: ''};
  error: string = '';
  errorTime: string = '';
  updateResult: string;
  private subscriptionId: Subscription;

  constructor(
    private departmentService: DepartmentService,
    private router: Router,
    private route: ActivatedRoute
  ) {}
  ngOnDestroy(): void {
    this.subscriptionId.unsubscribe();
  }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    console.log(id);
    this.subscriptionId = this.route.params
      .subscribe(
        (params: Params) => {
          this.departmentService.getDepartmentById(params['id']).subscribe({
            next: (value: any) => {
              this.department = value.data;
              this.error = '';
            },
            error: (errorResponse: HttpErrorResponse) => {
              this.error = errorResponse.error.message;
              this.errorTime = errorResponse.error.timeStamp;
            },
          });
        }
      );
    this.departmentService.getDepartmentById(id).subscribe({
      next: (value: any) => {
        this.department = value.data;
        console.log(this.department);
      },
      error: (errorResponse: HttpErrorResponse) => {
        this.error = errorResponse.error.message;
        this.errorTime = errorResponse.error.timeStamp;
      },
    });
  }

  onUpdate(form: NgForm) {
    if (!form.valid) {
      return;
    }
    this.department.name = form.value.name;
    this.departmentService.updateDepartment(this.department).subscribe({
      next: (value: any) => {
        this.error = '';
        this.router.navigate(['/departments']);
      },
      error: (error: HttpErrorResponse) => {
        this.error = error.error.message;
      },
    });
  }
}
