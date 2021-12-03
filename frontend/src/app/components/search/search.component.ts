import { Component, OnInit } from '@angular/core';
import {Search} from '../../dtos/search';
import {Time} from '@angular/common';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
  searchQuery: Search = {
    artistName: null, denotationLocation: null, street: null, city: null,
    country: null, denotationType: null, duration: null, type: null,
    date: null, time: null, price: null
  };
  constructor() { }

  ngOnInit(): void {
  }

}
