import { EventDetailsComponent } from './event-details/event-details.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';
import { AdminComponent } from './admin/admin.component';
import { EventsListComponent } from './events-list/events-list.component';

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EventCreateComponent } from './event-create/event-create.component';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'user',
    component: UserComponent
  },
  {
    path: 'admin',
    component: AdminComponent
  },
  {
    path: 'auth/login',
    component: LoginComponent
  },
  {
    path: 'signup',
    component: RegisterComponent
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'events',
    component: EventsListComponent,
  },
  {
    path: 'events/details/:id',
    component: EventDetailsComponent

  },
  {
    path: 'events/add',
    component: EventCreateComponent,
  }
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
