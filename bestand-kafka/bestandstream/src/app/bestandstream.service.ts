import {Injectable} from '@angular/core';

import 'rxjs/add/operator/toPromise';

import {HttpClient} from "@angular/common/http";
import {tap} from "rxjs/operators";
import {Observable} from "rxjs/Observable";


@Injectable()

export class BestandStreamService {
  constructor(private http: HttpClient) {
  }

  getVollePulle(): Observable<String> {
    return this.http.get<String>("/pubstart/")
      .pipe(
        tap(string => console.log(`vollepulle`))
      );
  }

  getVolle1000(): Observable<String> {
    return this.http.get<String>("/pubslow/")
      .pipe(
        tap(string => console.log(`volle1000`))
      );
  }

  getLetzerMongo(): Observable<String> {
    return this.http.get<String>("/mongolast/")  // /mongolast/ oder http://localhost:8081/last
      .pipe(
        tap(string => console.log(`mongolast`))
      );
  }
}
