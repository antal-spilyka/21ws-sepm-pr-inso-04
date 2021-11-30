import { Component, OnInit } from '@angular/core';
import {Search} from '../../dtos/search';

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
  constructor() { }

  ngOnInit(): void {
  }

}
