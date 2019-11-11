import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Cost } from '../domain/cost';

@Injectable({
  providedIn: 'root'
})
export class CostService {

  private baseUrl = 'http://localhost:8080/api/v1/costs';

  constructor(private http: HttpClient) { }
  /*
    getAll() {
      return this.http.get<Cost[]>(this.baseUrl);
    } */

  getOneById(id: number) {
    console.log('service: ' + id);
    return this.http.get<Cost>(this.baseUrl + '/' + id);
  }
}
