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
import {MessageComponent} from './components/message/message.component';
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
import {CreateActualEventComponent} from './components/create-event/create-actual-event/create-actual-event.component';
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


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    MessageComponent,
    EditUserComponent,
    EditEmailDialogComponent,
    EditPasswordDialogComponent,
    UserListComponent,
    AddNewsComponent,
    CreateEventComponent,
    CreateEventPlaceComponent,
    CreateArtistComponent,
    CreateActualEventComponent,
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
    MatButtonModule,
    MatTooltipModule,
    MatAutocompleteModule,
    MatInputModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSlideToggleModule,
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
