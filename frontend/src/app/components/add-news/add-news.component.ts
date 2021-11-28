import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {map, startWith, takeWhile, tap} from 'rxjs/operators';
import {interval} from 'rxjs';

@Component({
  selector: 'app-add-news',
  templateUrl: './add-news.component.html',
  styleUrls: ['./add-news.component.scss']
})
export class AddNewsComponent implements OnInit {
  myControl = new FormControl();
  events: any;
  stars: number;
  items: number[] = [1,2,3,4,5,6,7,8,9];

  constructor() { }

  ngOnInit(): void {
    this.events = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => this.filter(value)),
    );
  }

  private filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.events.filter(event => event.toLowerCase().includes(filterValue));
  }

  setStars(stars: number) {
    this.stars = stars;
  }

  scrollLeft(el: Element) {
    const animTimeMs = 400;
    const pixelsToMove = 315;
    const stepArray = [0.001, 0.021, 0.136, 0.341, 0.341, 0.136, 0.021, 0.001];
    interval(animTimeMs / 8)
      .pipe(
        takeWhile(value => value < 8),
        tap(value => el.scrollLeft -= (pixelsToMove * stepArray[value])),
      )
      .subscribe();
  }

  scrollRight(el: Element) {
    const animTimeMs = 400;
    const pixelsToMove = 315;
    const stepArray = [0.001, 0.021, 0.136, 0.341, 0.341, 0.136, 0.021, 0.001];
    interval(animTimeMs / 8)
      .pipe(
        takeWhile(value => value < 8),
        tap(value => el.scrollLeft += (pixelsToMove * stepArray[value])),
      )
      .subscribe();
  }
}
