import { Component, OnInit } from '@angular/core';
import {Address} from '../../dtos/address';

@Component({
  selector: 'app-search-location',
  templateUrl: './search-location.component.html',
  styleUrls: ['./search-location.component.scss']
})
export class SearchLocationComponent implements OnInit {
  searchAddress: Address = {
    id: null, city: '', state: '', zip: null, country: '', description: '', street: '',
};
  constructor() { }

  ngOnInit(): void {
  }

}
