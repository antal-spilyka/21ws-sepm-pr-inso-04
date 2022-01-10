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
import {NewsViewComponent} from './components/news-view/news-view.component';
import {HallDetailComponent} from './components/hall-detail/hall-detail.component';
import {ArtistPerformancesComponent} from './components/artist-performances/artist-performances.component';
import {EventPerformancesComponent} from './components/event-performances/event-performances.component';
import {LocationPerformancesComponent} from './components/location-performances/location-performances.component';
import {PerformanceDetailedComponent} from './components/performance-detailed/performance-detailed.component';
import {OldNewsComponent} from './components/old-news/old-news.component';
import {CreateEventPlacesComponent} from './components/create-event-places/create-event-places.component';
import {BookPerformanceComponent} from './components/book-performance/book-performance.component';
import {OrdersComponent} from './components/orders/orders.component';
import {AddUserComponent} from './components/add-user/add-user.component';

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
  {path: 'eventPlaces/:id/halls/add', component: CreateHallplanComponent},
  {path: 'halls/:hallId', component: HallDetailComponent},
  {path: 'search-time', component: SearchTimeComponent},
  {path: 'news/:id', canActivate: [AuthGuard], component: NewsViewComponent},
  {path: 'artists/:id/performances', component: ArtistPerformancesComponent},
  {path: 'events/:id/performances', component: EventPerformancesComponent},
  {path: 'locations/:id/performances', component: LocationPerformancesComponent},
  {path: 'performances/book', canActivate: [AuthGuard], component: BookPerformanceComponent},
  {path: 'performances/:id', component: PerformanceDetailedComponent},
  {path: 'news/:id', canActivate: [AuthGuard], component: NewsViewComponent},
  {path: 'oldNews', canActivate: [AuthGuard], component: OldNewsComponent},
  {path: 'eventPlaces/add', canActivate: [AuthGuard], component: CreateEventPlacesComponent},
  {path: 'orders', canActivate: [AuthGuard], component: OrdersComponent},
  {path: 'users/add', canActivate: [AuthGuard], component: AddUserComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
