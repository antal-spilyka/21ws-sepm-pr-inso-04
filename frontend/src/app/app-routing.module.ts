import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './guards/auth.guard';
import {MessageComponent} from './components/message/message.component';
import {AddNewsComponent} from './components/add-news/add-news.component';
import { CreateEventComponent } from './components/create-event/create-event.component';
import {RegisterComponent} from './components/register/register.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'message', component: MessageComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'message', component: MessageComponent},
  {path: 'news/add', component: AddNewsComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'createEvent', canActivate: [AuthGuard], component: CreateEventComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
