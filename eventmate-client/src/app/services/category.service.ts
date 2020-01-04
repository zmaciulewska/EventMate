import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Category } from '../domain/category';
import { Page } from '../domain/page';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {


  private baseUrl = 'http://localhost:8080/api/v1/categories';

  private requestUrl: string;
  private params: HttpParams;
  private paramUrl: string;

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get<Category[]>(this.baseUrl + '/list');
  }

  getAllPaginated(page: number, size: number, searchParameters: Map<String, Object>) {
    this.params = new HttpParams();
    console.log(searchParameters);
    this.paramUrl = '';

    searchParameters.forEach((value: Object, key: string) => {
      if (value !== undefined) {
        this.params.set(key, value.toString());
        console.log(value.toString());
        this.paramUrl = this.paramUrl + '&' + key + '=' + value.toString();
      }
    });
    this.requestUrl = this.baseUrl + '?page=' + page + '&size=' + size + this.paramUrl;
    console.log(this.requestUrl);
    return this.http.get<Page<Category>>(this.requestUrl);
  }

  getOneById(id: number) {
    return this.http.get<Category>(this.baseUrl + '/' + id);
  }

  delete(id: number) {
    return this.http.delete(this.baseUrl + '/' + id);
  }

  update(id: number, category: Category) {
    return this.http.put(this.baseUrl + '/' + id, category);
  }

  add(category: Category) {
    return this.http.post(this.baseUrl, category);
  }


}
