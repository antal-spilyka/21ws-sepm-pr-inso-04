import {Injectable} from '@angular/core';
import {News} from '../dtos/news';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {SimpleSeenNewsDto} from '../dtos/simpleSeenNewsDto';
import {SimpleNewsDto} from "../dtos/simpleNewsDto";

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  private newsBaseUri: string = this.globals.backendUri + '/news';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Create news, which can be shown later.
   *
   * @param news which will be added to the datastore.
   */
  save(news: News): Observable<string> {
    console.log(`Save new news ${news} in the datastore`);
    return this.httpClient.post(this.newsBaseUri, news, {responseType: 'text'});
  }

  /**
   * Get all unseen news stored in the system.
   *
   * @param email from the loggedIn User.
   * @return observable list of found news.
   */
  getNewNews(email: string): Observable<SimpleNewsDto[]> {
    console.log('Load all new news');
    return this.httpClient.get<SimpleNewsDto[]>(this.newsBaseUri+ '/' + email + '/new');
  }

  /**
   * Get all seen news stored in the system.
   *
   * @param email from the loggedIn User.
   * @return observable list of found news.
   */
  getOldNews(email: string): Observable<SimpleNewsDto[]> {
    console.log('Load all old news');
    return this.httpClient.get<SimpleNewsDto[]>(this.newsBaseUri+ '/' + email + '/old');
  }

  /**
   * Get News with corresponding id
   *
   * @param id of the news
   * @returns found news
   */
  readNews(simpleSeenNewsDto: SimpleSeenNewsDto): Observable<News> {
    console.log(`Read News`);
    return this.httpClient.post<News>(this.newsBaseUri + `/read`, simpleSeenNewsDto);
  }
}
