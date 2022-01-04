import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {PerformanceDetail} from '../../dtos/performance-detail';

@Component({
  selector: 'app-book-performance',
  templateUrl: './book-performance.component.html',
  styleUrls: ['./book-performance.component.scss']
})
export class BookPerformanceComponent implements OnInit {
  performance?: PerformanceDetail = {
    artist: undefined,
    duration: 0,
    eventDto: undefined,
    hall: undefined,
    id: 0,
    name: '',
    startTime: undefined,
    tickets: undefined,
  };

  constructor(private router: Router) {
    this.performance = this.router.getCurrentNavigation().extras.state.performance;
  }

  ngOnInit(): void {
  }
}
