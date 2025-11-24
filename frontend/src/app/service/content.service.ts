import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ContentResponse {
  content: string;
}

@Injectable({
  providedIn: 'root'
})
export class ContentService {

  private apiUrl = 'http://localhost:8080/api/content';

  constructor(private http: HttpClient) {}

  generateContent(text: string): Observable<ContentResponse> {
    return this.http.post<ContentResponse>(this.apiUrl, { text });
  }

}

