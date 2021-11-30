import { Component, OnInit } from '@angular/core';
import {Search} from '../../dtos/search';
import {ArtistService} from '../../services/artist.service';
import {Artist} from '../../dtos/artist';

@Component({
  selector: 'app-search-artist',
  templateUrl: './search-artist.component.html',
  styleUrls: ['./search-artist.component.scss']
})
export class SearchArtistComponent implements OnInit {
  searchQuery: Search = {
    artistName: null, denotationLocation: null, street: null, city: null,
    country: null, denotationType: null, duration: null, type: null,
    date: null, time: null, price: null
  };

  artistName: string;
  artistList: Artist[] = [];
  private error = false;
  private errorMessage: string;
  constructor(private artistService: ArtistService) { }

  ngOnInit(): void {
  }

   onSubmit() {
    this.artistService.findArtist(this.artistName).subscribe(
      {
        next: artists => {
          console.log(this.artistList);
          this.artistList = artists;
          console.log(this.artistList);
        }, error: error => this.handleError(error)
      }
    );
  }

  private handleError(error: any) {
    console.log(error);
    this.error = true;
    if (error.status === 0) {
      this.errorMessage = 'Cannot reach the backend';
    } else if (error.error.message === 'No message available') {
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error.message;
    }
  }
}
