import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FileDto } from '../dtos/fileDto';
import { Globals } from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  private messageBaseUri: string = this.globals.backendUri + '/files';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  saveFile(image: File): Observable<FileDto> {
    console.log(image);
    const formData = new FormData();
    formData.append('file', image);
    const headers = new HttpHeaders({
      reportProgress: 'true',
      responseType: 'text'
    });
    return this.httpClient.post<FileDto>(this.messageBaseUri, formData,  {
      headers
    });
  }
}
