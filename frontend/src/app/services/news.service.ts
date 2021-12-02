import { Injectable } from '@angular/core';
import {News} from '../dtos/news';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  private newsBaseUri: string = this.globals.backendUri + '/news';

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  /**
   * Create news, which can be shown later.
   *
   * @param news which will be added to the datastore.
   */
  save(news: News): Observable<string> {
    console.log(`Save new news ${news} in the datastore`);
    return this.httpClient.post(this.newsBaseUri, news, {responseType: 'text'});
  }
}
