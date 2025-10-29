// New Angular service to call /api/hello
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({providedIn: 'root'})
export class HelloService {
  private url = 'http://localhost:8080/api/hello';

  constructor(private http: HttpClient) {}

  getHello(): Observable<string> {
    console.log('HelloService: calling ' + this.url);
    return this.http.get(this.url, { responseType: 'text' });
  }
}

