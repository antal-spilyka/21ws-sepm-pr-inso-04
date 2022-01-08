import {Injectable} from '@angular/core';
import {Globals} from '../global/globals';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Artist} from '../dtos/artist';

@Injectable({
  providedIn: 'root'
})
export class ArtistService {

  private messageBaseUri: string = this.globals.backendUri + '/artists';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Finds artist by name.
   *
   * @param searchName to search for
   * @returns Observable List of Artists matching query
   */
  findArtist(searchName: string): Observable<Artist[]> {
    let params = new HttpParams();
    params = params.set('misc', searchName);
    return this.httpClient.get<Artist[]>(this.messageBaseUri, {params});
  }
  /**
   * Searches for artist by name.
   *
   * @param searchName to search for
   * @returns Observable List of Artists matching query
   */
  searchArtist(searchName: string): Observable<Artist[]> {
    let params = new HttpParams();
    if (searchName && searchName !== '') {
      params = params.set('misc', searchName.trim());
    }
    return this.httpClient.get<Artist[]>(this.messageBaseUri + '/search', {params});
  }

  /**
   * Persists new artist.
   *
   * @param artist to be persisted
   * @returns Observable
   */
  createArtist(artist: Artist): Observable<Artist> {
    console.log(artist);
    return this.httpClient.post<Artist>(this.messageBaseUri, artist);
  }
}
