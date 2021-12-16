import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './guards/auth.guard';
import {AddNewsComponent} from './components/add-news/add-news.component';
import {CreateEventComponent} from './components/create-event/create-event.component';
import {RegisterComponent} from './components/register/register.component';
import {SearchComponent} from './components/search/search.component';
import {SearchArtistComponent} from './components/search-artist/search-artist.component';
import {SearchLocationComponent} from './components/search-location/search-location.component';
import {SearchEventsComponent} from './components/search-events/search-events.component';
import {SearchTimeComponent} from './components/search-time/search-time.component';
import {EditUserComponent} from './components/edit-user/edit-user.component';
import {UserListComponent} from './components/user-list/user-list.component';
import {NewsMainPageComponent} from './components/news-main-page/news-main-page.component';
import {CreateHallplanComponent} from './components/create-hallplan/create-hallplan.component';
import { NewsViewComponent } from './components/news-view/news-view.component';
import {HallDetailComponent} from './components/hall-detail/hall-detail.component';

const routes: Routes = [
  {path: '', component: NewsMainPageComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'edit', component: EditUserComponent},
  {path: 'users', canActivate: [AuthGuard], component: UserListComponent},
  {path: 'news/add', component: AddNewsComponent},
  {path: 'events/add', canActivate: [AuthGuard], component: CreateEventComponent},
  {path: 'search', component: SearchComponent},
  {path: 'search-artist', component: SearchArtistComponent},
  {path: 'search-location', component: SearchLocationComponent},
  {path: 'search-event', component: SearchEventsComponent},
  {path: 'locations/:id/halls/add', component: CreateHallplanComponent},
  {path: 'halls/:hallId', component: HallDetailComponent},
  {path: 'search-time', component: SearchTimeComponent},
  {path: 'news/:id', canActivate: [AuthGuard], component: NewsViewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
