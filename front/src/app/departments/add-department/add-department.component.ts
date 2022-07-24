import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Department } from 'src/app/models/department';
import { DepartmentService } from '../department.service';

@Component({
  selector: 'app-add-department',
  templateUrl: './add-department.component.html',
  styleUrls: ['./add-department.component.css']
})
export class AddDepartmentComponent implements OnInit {
  error: string = '';
  department: Department = {id: 0, name: ''};
  
  constructor(private departmentService: DepartmentService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onAdd(form: NgForm) {
    if (!form.valid) {
      this.error = 'Form is not valid';
      return;
    }
    this.departmentService.addDepartment(this.department).subscribe({
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
