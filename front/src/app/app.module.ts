import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './auth/login/login.component';
import { SpinnerLoadingComponent } from './shared/spinner-loading/spinner-loading.component';
import { HomeComponent } from './home/home.component';
import { DepartmentComponent } from './departments/department/department.component';
import { EditDepartmentComponent } from './departments/edit-department/edit-department.component';
import { AddDepartmentComponent } from './departments/add-department/add-department.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    SpinnerLoadingComponent,
    HomeComponent,
    DepartmentComponent,
    EditDepartmentComponent,
    AddDepartmentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
