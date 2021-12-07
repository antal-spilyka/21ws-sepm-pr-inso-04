import {Injectable} from '@angular/core';
import {News} from '../dtos/news';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';

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
   * Get all news stored in the system
   *
   * @return observable list of found news.
   */
  getNewNews(): Observable<News[]> {
    console.log('Load all news');
    return this.httpClient.get<News[]>(this.newsBaseUri);
  }

  /**
   * Get News with corresponding id
   *
   * @param id of the news
   * @returns found news
   */
  getNewsById(id: number): Observable<News> {
    console.log(`Get News by Id: ${id}`);
    return this.httpClient.get<News>(this.newsBaseUri + `/${id}`);
  }
}
