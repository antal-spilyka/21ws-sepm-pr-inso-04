import { Component, OnInit } from '@angular/core';
import {takeWhile, tap} from 'rxjs/operators';
import {debounceTime, distinctUntilChanged, interval, Observable, switchMap} from 'rxjs';
import {EventService} from '../../services/event.service';
import {EventDto} from '../../dtos/eventDto';
import {FormBuilder, Validators} from '@angular/forms';
import {News} from '../../dtos/news';
import {NewsService} from '../../services/news.service';

@Component({
  selector: 'app-add-news',
  templateUrl: './add-news.component.html',
  styleUrls: ['./add-news.component.scss']
})
export class AddNewsComponent implements OnInit {
  error = false;
  errorMessage = '';
  events: Observable<EventDto[]>;
  currentEvent: EventDto;
  form = this.formBuilder.group({
    id: ['',],
    chosenEvent: ['', Validators.required],
    rating: [null, Validators.required],
    fsk: [18, Validators.required],
    shortDescription: ['',],
    longDescription: ['',]
  });

  constructor(
    private eventService: EventService,
    private formBuilder: FormBuilder,
    private newsService: NewsService,
  ) {
    this.loadEvents();
  }

  loadEvents() {
    this.events = this.form.get('chosenEvent').valueChanges.pipe(
      distinctUntilChanged(),
      debounceTime(500),
      switchMap((name: string) => this.eventService.findEvent(name))
    );
  }

  ngOnInit() {
  }

  setChosenEvent(event: EventDto) {
    this.currentEvent = event;
    document.getElementById('testing').innerText = this.currentEvent.toString();
  }

  setRating(rating: number) {
    this.form.controls.rating.setValue(rating);
  }

  goBack() {
    history.back();
  }

  save() {
    const newsRequest = {event: this.currentEvent, rating: this.form.controls.rating.value, fsk: this.form.controls.fsk.value,
    shortDescription: this.form.controls.shortDescription.value, longDescription: this.form.controls.longDescription.value,
      createDate: new Date()} as News;
    console.log(newsRequest);
    this.newsService.save(newsRequest).subscribe({
      next: () => {
        console.log('News was created successfully: ' + newsRequest.toString());
        alert('News were added!');
      },
      error: (error) => {
        console.log(`could not create news due to: ${error}`);
        this.error = true;
        this.errorMessage = error;
      }
    });
  }

  // region horizontal scroll

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

  // endregion
}
