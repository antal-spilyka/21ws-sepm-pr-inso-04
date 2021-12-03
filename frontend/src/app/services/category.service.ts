import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Category} from '../dtos/category';
import {Globals} from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private messageBaseUri: string = this.globals.backendUri + '/categories';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Finds category by name.
   *
   * @param searchName to search for
   * @returns Observable List of Category matching query
   */
  findCategory(searchName: string): Observable<Category[]> {
    let params = new HttpParams();
    params = params.set('name', searchName);
    return this.httpClient.get<Category[]>(this.messageBaseUri, {params});
  }

  /**
   * Persists new category.
   *
   * @param category to be persisted
   * @returns Observable
   */
  createCategory(category: Category): Observable<Category> {
    return this.httpClient.post<Category>(this.messageBaseUri, category);
  }
}
