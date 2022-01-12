import { Component, OnInit } from '@angular/core';
import {ArtistService} from '../../services/artist.service';
import {Artist} from '../../dtos/artist';
import {Router} from '@angular/router';


@Component({
  selector: 'app-search-artist',
  templateUrl: './search-artist.component.html',
  styleUrls: ['./search-artist.component.scss']
})
export class SearchArtistComponent implements OnInit {
  artistName: string;
  submitted  = false;
  artistList: Artist[] = [];
  pageCounter = 0;
  error = false;
  errorMessage: string;
  constructor(private artistService: ArtistService, private router: Router) { }

  ngOnInit(): void {
  }
  loadPerformances(artist: Artist){
    if(artist.id){
      this.router.navigateByUrl(`/artists/${artist.id}/performances`);
    }
  }
   onSubmit(newSearch = true) {
    if(newSearch){
      this.artistList = [];
      this.pageCounter = 0;
    }
    this.artistService.searchArtist(this.artistName, this.pageCounter).subscribe(
      {
        next: artists => {
          this.submitted = true;
          //console.log(this.artistList);
          this.artistList = this.artistList.concat(artists);
          console.log(this.artistList);
        }, error: error => this.handleError(error)
      }
    );
  }
  moreItems(){
    this.pageCounter = this.pageCounter+1;
    this.onSubmit(false);
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
