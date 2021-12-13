import { Component, OnInit } from '@angular/core';
import {News} from '../../dtos/news';
import {NewsService} from '../../services/news.service';
import {Router} from '@angular/router';
import jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-old-news',
  templateUrl: './old-news.component.html',
  styleUrls: ['./old-news.component.scss']
})
export class OldNewsComponent implements OnInit {

  news: News[];
  error = false;
  errorMessage = '';

  constructor(private newsService: NewsService, private router: Router) { }

  ngOnInit(): void {
    this.loadNews();
  }

  loadNews(): void {
    this.newsService.getOldNews(this.getEmail()).subscribe(
      (news: News[]) => {
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
      val.event.startTime = new Date(val.event.startTime);
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

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  redirect(news: News) {
    console.log(news);
    if(news.id) {
      this.router.navigateByUrl(`/news/${news.id}`);
    }
  }

}
