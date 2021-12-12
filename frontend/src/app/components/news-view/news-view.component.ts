import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { News } from 'src/app/dtos/news';
import { NewsService } from 'src/app/services/news.service';
import {SimpleSeenNewsDto} from "../../dtos/simpleSeenNewsDto";
import jwt_decode from "jwt-decode";

@Component({
  selector: 'app-news-view',
  templateUrl: './news-view.component.html',
  styleUrls: ['./news-view.component.scss']
})
export class NewsViewComponent implements OnInit {

  news: News;
  images: Array<object> = [];
  durationDHM: string;
  activeStar = '../../../assets/activeStar.svg';
  inActiveStar = '../../../assets/inActiveStar.svg';
  error = false;

  constructor(private route: ActivatedRoute, private newsService: NewsService, private router: Router) { }

  ngOnInit(): void {
    let id: number;
    this.route.paramMap.subscribe( paramMap => {
      id = Number(paramMap.get('id'));
    });
    const simpleNewsDto: SimpleSeenNewsDto = {
      newsId: id, userEmail: this.getEmail()
    };
    this.newsService.readNews(simpleNewsDto).subscribe({
      next: (news) => {
        this.news = news;
        console.log(this.news);
        this.durationDHM = this.minutesToDhms(news.event.duration);
        if(this.news.pictures) {
          if(this.news.pictures.length === 0) {
            this.images.push(
              {
                image: '../../../assets/noImage.jpg',
                thumbImage: '../../../assets/noImage.jpg'
              }
            );
          } else {
            this.news.pictures.map( picture => {
              this.images.push(
                {
                  image: picture.url,
                  thumbImage: picture.url
                }
              );
            });
          }
        }
      },
      error: error => {
        this.error = true;
      }
    });
  }

  getEmail(): string {
    const decoded: any = jwt_decode(localStorage.getItem('authToken'));
    return decoded.sub;
  }

  minutesToDhms(minutes: number) {
    console.log(minutes);
    const d = Math.floor(minutes / (60*24));
    const h = Math.floor(minutes % (60*24) / 60);
    const m = Math.floor(minutes % 60);

    const dDisplay = d > 0 ? d + (d === 1 ? ' day ' : ' days ') : '';
    const hDisplay = h > 0 ? h + (h === 1 ? ' hour ' : ' hours ') : '';
    const mDisplay = m > 0 ? m + (m === 1 ? ' minute ' : ' minutes ') : '';
    return (dDisplay + hDisplay + mDisplay).substring(1);
  }

  vanishError() {
    this.error = false;
  }

  redirect() {
    this.router.navigateByUrl('/');
  }

}
