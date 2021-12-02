import { Component, OnInit } from '@angular/core';
import {NewsService} from '../../services/news.service';
import {interval, Observable} from 'rxjs';
import {takeWhile, tap} from 'rxjs/operators';
import {News} from '../../dtos/news';

@Component({
  selector: 'app-news-main-page',
  templateUrl: './news-main-page.component.html',
  styleUrls: ['./news-main-page.component.scss']
})
export class NewsMainPageComponent implements OnInit {

  news: News[];
  error = false;
  errorMessage = '';

  constructor(private newsService: NewsService) { }

  ngOnInit(): void {
    this.loadNews();
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  loadNews(): void {
    this.newsService.getAll().subscribe(
      (news: News[]) => {
        this.news = news;

        console.log(this.news);
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );

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

  defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (error.status === 0) {
      // If status is 0, the backend is probably down
      this.errorMessage = 'The backend seems not to be reachable';
    } else if (error.error.message === 'No message available') {
      // If no detailed error message is provided, fall back to the simple error name
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error.message;
    }
  }

}
