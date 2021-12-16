import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-hall-detail',
  templateUrl: './hall-detail.component.html',
})
export class HallDetailComponent implements OnInit {
  hallId: number;

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.hallId = params['hallId'];
    });
  }
}
