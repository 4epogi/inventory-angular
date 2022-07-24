import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Department } from 'src/app/models/department';
import { ErrorResponse } from 'src/app/models/error-response';
import { DepartmentService } from '../department.service';

@Component({
  selector: 'app-department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.css'],
})
export class DepartmentComponent implements OnInit, OnDestroy {
  departments: Department[];
  successMessage: string = '';
  error: string = '';
  private id: number;
  private departmentActionMessage: Subscription;


  constructor(private departmentService: DepartmentService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getAllDepartments();
    this.departmentActionMessage = this.departmentService.departmentActionMessage.subscribe({
      next: (value: string) => {
        this.successMessage = value;
        this.getAllDepartments();
      }
    });
  }

  ngOnDestroy() :void {
    this.departmentActionMessage.unsubscribe();
  }

  deleteDepartment(id: number) {
    if (confirm('Are you sure?')) {
      this.departmentService.deleteDepartmentById(id).subscribe({
        next: (value: any) => {
          this.successMessage = value.data;
          this.getAllDepartments();
        },
        error: (error: any) => {
          this.error = error.error.message;
          // console.log(error.status);
        },
      });
    }
  }

  private getAllDepartments() {
    this.departmentService.getAllDepartments().subscribe({
      next: (value: any) => {
        this.departments = value.data;
      },
      error: (error: HttpErrorResponse) => {
        // console.log(error.status);
        this.error = error.error.message;
      },
    });
  }

  updateDepartment(id: number) {
    this.router.navigate(['edit', id], {relativeTo: this.route});
  }

  addDepartment() {
    console.log('add department');
    this.router.navigate(['/departments/add']);
  }

  onChangeDepartments(result: any) {
    this.getAllDepartments();
    console.log(result);
    this.successMessage = result;
  }
}
