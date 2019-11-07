import { EventService } from './services/event.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AngularDateTimePickerModule } from 'angular2-datetimepicker';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown-angular7';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';
import { AdminComponent } from './admin/admin.component';

import { httpInterceptorProviders } from './auth/auth-interceptor';
import { EventsListComponent } from './events-list/events-list.component';
import { EventDetailsComponent } from './event-details/event-details.component';
import { EventCreateComponent } from './event-create/event-create.component';
import { CategoryService } from './services/category.service';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    UserComponent,
    AdminComponent,
    EventsListComponent,
    EventDetailsComponent,
    EventCreateComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    AngularDateTimePickerModule,
    NgMultiSelectDropDownModule,
    /* MbscModule */
  ],
  providers: [httpInterceptorProviders, EventService, CategoryService],
  bootstrap: [AppComponent]
})
export class AppModule { }
