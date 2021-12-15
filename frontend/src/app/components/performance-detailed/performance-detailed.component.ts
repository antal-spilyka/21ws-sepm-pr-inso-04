import { Component, OnInit } from '@angular/core';
import {Performance} from '../../dtos/performance';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-performance-detailed',
  templateUrl: './performance-detailed.component.html',
  styleUrls: ['./performance-detailed.component.scss']
})
export class PerformanceDetailedComponent implements OnInit {
  performance: Performance = {
    artist: undefined,
    duration: 0,
    eventDto: undefined,
    hall: undefined,
    id: 0,
    name: '',
    startTime: undefined
  };
  constructor(private activatedRoute: ActivatedRoute) {
    this.performance = JSON.parse(activatedRoute.snapshot.params['performance']);
  }

  ngOnInit(): void {
  }

}
