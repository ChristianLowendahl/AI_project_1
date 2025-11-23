import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface QuestionsResponse {
  questions: string;
}

@Injectable({
  providedIn: 'root'
})
export class QuestionsService {

  // Matches the current QuestionsController mapping: POST /api/questions
  private apiUrl = 'http://localhost:8080/api/questions';

  constructor(private http: HttpClient) {}

  generateQuestions(text: string): Observable<QuestionsResponse> {
    return this.http.post<QuestionsResponse>(this.apiUrl, { text });
  }

}
