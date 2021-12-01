import { Component, OnInit } from '@angular/core';
import {EventInquiry} from '../../dtos/eventInquiry';

@Component({
  selector: 'app-search-events',
  templateUrl: './search-events.component.html',
  styleUrls: ['./search-events.component.scss']
})
export class SearchEventsComponent implements OnInit {
  searchEvent: EventInquiry = {
  name: '', duration: null, content: '', dateTime: null, categoryName: '', roomId: null,
  artistId: null, description: null,
};
  constructor() { }

  ngOnInit(): void {
  }

}
