import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, Subject, tap, throwError } from 'rxjs';
import { Department } from '../models/department';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {
  departmentActionMessage = new Subject<string>();

  constructor(private http: HttpClient) { }

  getAllDepartments() {
    return this.http.get<Department[]>('http://localhost:8080/api/departments/all');
  }

  getDepartments(name: string ='', page: number = 0, size: number = 10): Observable<Department[]> {
    return this.http.get<Department[]>(`http://localhost:8080/api/departments/pages?name=${name}&page=${page}&size=${size}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  getDepartmentById(id: number){
    return this.http.get<Department>(`http://localhost:8080/api/departments/${id}`);
  }

  handleError(errorRes: HttpErrorResponse) {
    console.log(errorRes);
    const errorMessage = 'Department Error';
    return throwError(() => new Error(errorMessage));
  }

  deleteDepartmentById(id: number) {
    return this.http.delete<{message: string}>(`http://localhost:8080/api/departments/delete/${id}`);
  }

  updateDepartment(department: Department) {
    return this.http.post<{message: string}>('http://localhost:8080/api/departments/update', department)
    .pipe(
      catchError(this.handleError),
      tap((data) => {
        return this.departmentActionMessage.next(data.message);
      })
    );
  }

  addDepartment(department: Department) {
    return this.http.post<Department>('http://localhost:8080/api/departments/add', department)
    .pipe(
      catchError(this.handleError),
      tap((data) => {
        return this.departmentActionMessage.next(`Department ${data.name} was successfully added`);
      })
    );
  }
}
