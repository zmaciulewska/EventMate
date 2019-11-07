import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Category } from '../domain/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {


  private baseUrl = 'http://localhost:8080/api/v1/categories';

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get<Category[]>(this.baseUrl);
  }

  getOneById(id: number) {
    return this.http.get<Category>(this.baseUrl + '/' + id);
  }

}
