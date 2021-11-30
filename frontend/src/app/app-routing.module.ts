import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './guards/auth.guard';
import {MessageComponent} from './components/message/message.component';
import {AddNewsComponent} from './components/add-news/add-news.component';
import {CreateEventComponent} from './components/create-event/create-event.component';
import {RegisterComponent} from './components/register/register.component';
import {SearchComponent} from './components/search/search.component';
import {SearchArtistComponent} from './components/search-artist/search-artist.component';
import {SearchLocationComponent} from './components/search-location/search-location.component';
import {SearchEventsComponent} from './components/search-events/search-events.component';
import {SearchTimeComponent} from './components/search-time/search-time.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'message', component: MessageComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'message', component: MessageComponent},
  {path: 'news/add', component: AddNewsComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'createEvent', canActivate: [AuthGuard], component: CreateEventComponent},
  {path: 'search', component: SearchComponent},
  {path: 'search-artist', component: SearchArtistComponent},
  {path: 'search-location', component: SearchLocationComponent},
  {path: 'search-event', component: SearchEventsComponent},
  {path: 'search-time', component: SearchTimeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
