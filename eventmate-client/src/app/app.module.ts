import { DateFormatPipe } from './utils/DateFormatPipe';
import { EventService } from './services/event.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AngularDateTimePickerModule } from 'angular2-datetimepicker';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown-angular7';
import { CustomFormsModule } from 'ng2-validation';

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
import { DateCorrectDirective } from './utils/date-correct.directive';
import { ShowErrorComponent } from './utils/show-error.component';
import { EventEditComponent } from './event-edit/event-edit.component';
import { EventOfferCreateComponent } from './event-offer-create/event-offer-create.component';
import { EventOfferListComponent } from './event-offer-list/event-offer-list.component';
import { CategoriesListComponent } from './categories-list/categories-list.component';
import { UsersListComponent } from './users-list/users-list.component';
import { UserDetailsComponent } from './user-details/user-details.component';



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
    EventCreateComponent,
    DateCorrectDirective,
    ShowErrorComponent,
    EventEditComponent,
    EventOfferCreateComponent,
    EventOfferListComponent,
    CategoriesListComponent,
    UsersListComponent,
    UserDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    AngularDateTimePickerModule,
    NgMultiSelectDropDownModule,
    CustomFormsModule
    /* ReactiveFormsModule */
    /* MbscModule */
  ],
  providers: [httpInterceptorProviders, EventService, CategoryService, DateFormatPipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
