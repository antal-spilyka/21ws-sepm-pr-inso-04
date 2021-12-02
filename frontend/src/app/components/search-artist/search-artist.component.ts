import { Component, OnInit } from '@angular/core';
import {ArtistService} from '../../services/artist.service';
import {Artist} from '../../dtos/artist';

@Component({
  selector: 'app-search-artist',
  templateUrl: './search-artist.component.html',
  styleUrls: ['./search-artist.component.scss']
})
export class SearchArtistComponent implements OnInit {
  artistName: string;
  artistList: Artist[] = [];
  error = false;
  errorMessage: string;
  constructor(private artistService: ArtistService) { }

  ngOnInit(): void {
  }

   onSubmit() {
    this.artistService.searchArtist(this.artistName).subscribe(
      {
        next: artists => {
          console.log(this.artistList);
          this.artistList = artists;
          console.log(this.artistList);
        }, error: error => this.handleError(error)
      }
    );
  }
  vanishError(): void {
    this.errorMessage = null;
    this.error = false;
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
