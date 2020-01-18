import { Injectable } from '@angular/core';
import { HttpClient } from '../../../node_modules/@angular/common/http';
import { Statistic } from '../domain/statistic';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  private baseUrl = 'http://localhost:8080/api/v1/statistics';

  constructor(private http: HttpClient) { }

  getEventsNumberPerMonth() {
    return this.http.get<Statistic>(this.baseUrl + '/events-number-per-month' );
  }

  getCategories() {
    return this.http.get<Statistic>(this.baseUrl + '/categories' );
  }
}
