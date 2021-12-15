import { Component, OnInit } from '@angular/core';
import {NewsService} from '../../services/news.service';
import {News} from '../../dtos/news';
import { Router } from '@angular/router';
import jwt_decode from 'jwt-decode';
import {SimpleNewsDto} from "../../dtos/simpleNewsDto";

@Component({
  selector: 'app-news-main-page',
  templateUrl: './news-main-page.component.html',
  styleUrls: ['./news-main-page.component.scss']
})
export class NewsMainPageComponent implements OnInit {

  news: SimpleNewsDto[];
  error = false;
  errorMessage = '';

  constructor(private newsService: NewsService, private router: Router) { }

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
    this.newsService.getNewNews(this.getEmail()).subscribe(
      (news: SimpleNewsDto[]) => {
        this.news = news;
        console.log(this.news);
        this.formatDate();
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  getEmail(): string {
    const decoded: any = jwt_decode(localStorage.getItem('authToken'));
    return decoded.sub;
  }

  formatDate(): void {
    for (const val of this.news) {
      val.createDate = new Date(val.createDate);
      val.eventDate = new Date(val.eventDate);
    }
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

  redirect(news: SimpleNewsDto) {
    console.log(news);
    if(news.id) {
      this.router.navigateByUrl(`/news/${news.id}`);
    }
  }

  redirectToOldNews() {
    this.router.navigateByUrl(`/oldNews`);
  }

}
