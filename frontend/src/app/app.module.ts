import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './components/header/header.component';
import {FooterComponent} from './components/footer/footer.component';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {httpInterceptorProviders} from './interceptors';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatSelectModule} from '@angular/material/select';
import {MatIconModule} from '@angular/material/icon';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button';
import {MatTooltipModule} from '@angular/material/tooltip';
import {AddNewsComponent} from './components/add-news/add-news.component';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {UserListComponent} from './components/user-list/user-list.component';
import {MatInputModule} from '@angular/material/input';
import {CreateEventComponent} from './components/create-event/create-event.component';
import {CreateEventPlaceComponent} from './components/create-event/create-event-place/create-event-place.component';
import {CreateArtistComponent} from './components/create-event/create-artist/create-artist.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {RegisterComponent} from './components/register/register.component';
import {EditUserComponent} from './components/edit-user/edit-user.component';
import {MatCardModule} from '@angular/material/card';
import {MatExpansionModule} from '@angular/material/expansion';
import {EditEmailDialogComponent} from './components/edit-user/edit-email-dialog/edit-email-dialog.component';
import {MatDialogModule} from '@angular/material/dialog';
import {EditPasswordDialogComponent} from './components/edit-user/edit-password-dialog/edit-password-dialog.component';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import { SearchComponent } from './components/search/search.component';
import { SearchArtistComponent } from './components/search-artist/search-artist.component';
import { SearchLocationComponent } from './components/search-location/search-location.component';
import { SearchEventsComponent } from './components/search-events/search-events.component';
import { SearchTimeComponent } from './components/search-time/search-time.component';
import { NewsMainPageComponent } from './components/news-main-page/news-main-page.component';
import {MatGridListModule} from '@angular/material/grid-list';
import {CreateHallplanComponent} from './components/create-hallplan/create-hallplan.component';
import {HallplanElementComponent} from './components/create-hallplan/components/hallplan-element/hallplan-element.component';
import {MatRadioModule} from '@angular/material/radio';
import { EditPaymentInformationDialogComponent } from
    './components/edit-user/edit-payment-information-dialog/edit-payment-information-dialog.component';
import {MatTableModule} from '@angular/material/table';
import { NewsViewComponent } from './components/news-view/news-view.component';
import { NgImageSliderModule } from 'ng-image-slider';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {AddSectionDialogComponent} from './components/create-hallplan/components/add-section-dialog/add-section-dialog.component';
import {ColorPickerModule} from 'ngx-color-picker';
import {HallDetailComponent} from './components/hall-detail/hall-detail.component';
import { ArtistPerformancesComponent } from './components/artist-performances/artist-performances.component';
import { LocationPerformancesComponent } from './components/location-performances/location-performances.component';
import { EventPerformancesComponent } from './components/event-performances/event-performances.component';
import { PerformanceDetailedComponent } from './components/performance-detailed/performance-detailed.component';
import { OldNewsComponent } from './components/old-news/old-news.component';
import {
  RemoveSectionDialogComponent
} from './components/create-hallplan/components/remove-section-dialog/remove-section-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    EditUserComponent,
    EditEmailDialogComponent,
    EditPasswordDialogComponent,
    UserListComponent,
    AddNewsComponent,
    CreateEventComponent,
    CreateEventPlaceComponent,
    CreateArtistComponent,
    CreateHallplanComponent,
    HallplanElementComponent,
    NewsMainPageComponent,
    SearchComponent,
    SearchArtistComponent,
    SearchLocationComponent,
    SearchEventsComponent,
    SearchTimeComponent,
    EditPaymentInformationDialogComponent,
    AddSectionDialogComponent,
    HallDetailComponent,
    NewsViewComponent,
    ArtistPerformancesComponent,
    LocationPerformancesComponent,
    EventPerformancesComponent,
    PerformanceDetailedComponent,
    NewsViewComponent,
    RemoveSectionDialogComponent,
    OldNewsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatSelectModule,
    MatButtonModule,
    MatCardModule,
    MatExpansionModule,
    MatDialogModule,
    MatTooltipModule,
    MatAutocompleteModule,
    MatInputModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSlideToggleModule,
    MatGridListModule,
    MatRadioModule,
    MatGridListModule,
    NgImageSliderModule,
    MatProgressSpinnerModule,
    MatTableModule,
    ColorPickerModule
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
