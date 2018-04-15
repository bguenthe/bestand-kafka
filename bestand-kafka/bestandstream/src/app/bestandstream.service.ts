import {Injectable} from '@angular/core';

import 'rxjs/add/operator/toPromise';

import {HttpClient} from "@angular/common/http";
import {catchError, tap} from "rxjs/operators";
import {of} from "rxjs/observable/of";
import {Observable} from "rxjs/Observable";


@Injectable()

export class BestandStreamService {
  constructor(private http: HttpClient) {
  }

  getVollePulle(): Observable<String[]> {
    return this.http.get<String[]>("/pubstart/")
      .pipe(
        tap(string => console.log(`vollepulle`)),
        catchError(this.handleError('getVollePulle', []))
      );
  }

  getVolle1000(): Observable<String[]> {
    return this.http.get<String[]>("/pubslow/")
      .pipe(
        tap(string => console.log(`volle1000`)),
        catchError(this.handleError('getVolle1000', []))
      );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error); // log to console instead
      console.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
