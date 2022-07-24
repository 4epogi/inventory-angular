import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { AddDepartmentComponent } from './departments/add-department/add-department.component';
import { DepartmentComponent } from './departments/department/department.component';
import { EditDepartmentComponent } from './departments/edit-department/edit-department.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent},
  {path: 'departments', component: DepartmentComponent,
    children: [
      {path: 'edit/:id', component: EditDepartmentComponent},
      {path: 'add', component: AddDepartmentComponent}
    ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
