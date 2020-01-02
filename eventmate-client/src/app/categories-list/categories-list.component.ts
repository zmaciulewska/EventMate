import { CategoryService } from './../services/category.service';
import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/domain/category';
import { Router } from '../../../node_modules/@angular/router';
import { Page } from '../domain/page';
import { CategorySearchForm } from './category-search-form';

@Component({
  selector: 'app-categories-list',
  templateUrl: './categories-list.component.html',
  styleUrls: ['./categories-list.component.css']
})
export class CategoriesListComponent implements OnInit {

  categories: any;

  currentPage = 1;
  pageSize = 5;
  currentData: Page<Category>;
  errorMessage: string;

  categoryForm: Category;

  config: any;

  searchForm: CategorySearchForm;
  parameterMap: Map<String, Object>;

  constructor(private categoryService: CategoryService,
    private router: Router) { }

  ngOnInit() {
    this.categoryForm = new Category();
    this.prepareSerachForm();
    this.loadCategories();


  }

  prepareSerachForm() {
    console.log('Preparing search form.');
    this.searchForm = new CategorySearchForm();

  }

  prepareSearchCriteria() {
    console.log('Start preparing search criteria');
    this.parameterMap = new Map();
    this.parameterMap.set('code', this.searchForm.code);
    this.parameterMap.set('name', this.searchForm.name);
  }

  loadCategories() {
    console.log('Start loading categories');
    console.log('current page: ' + this.currentPage);
    console.log('pageSize: ' + this.pageSize);
    this.prepareSearchCriteria();
    console.log('parameterMap: ' + this.parameterMap);
    console.log('config: ' + this.config);
    this.categoryService.getAllPaginated((this.currentPage - 1), this.pageSize, this.parameterMap).subscribe(
      data => {
        this.categories = data.content;
        this.currentData = data;
        this.setConfig();
        console.log(this.currentData);
      },
      error => {
        this.errorMessage = error;
        console.log('error: ' + this.errorMessage);
      }
    );
  }

  deleteCategory(id: number) {
    if (confirm('Czy chcesz usunąć tę kategorię?')) {
      console.log('Delete confirmed');
      this.categoryService.delete(id).subscribe(
        res => {
          console.log('Event deleted');
          this.categories = this.categories.filter(item => item.id !== id);
          // this.router.navigate(['/home']);
        },
        error => {
          console.log('Event not deleted');
          // this.isDeleteFailed = true;
          this.errorMessage = error.error.message;
        });
    }
  }

  submit() {
    this.categoryService.add(this.categoryForm)
      .subscribe(data => {
        console.log(data);
        this.categories.push(data);
      },
        error => {
          this.errorMessage = (error);
        }
      );
    this.categoryForm = new Category();
  }

  pageChange(newPage: number) {
    console.log('newPage: ' + newPage);
    this.currentPage = newPage;
    this.loadCategories();
  }

  setConfig() {
    this.config = {
      itemsPerPage: 5,
      currentPage: this.currentPage,
      totalItems: this.currentData.totalElements,
    };
  }


  clearFilters() {
    this.prepareSerachForm();
    this.loadCategories();
  }


}
