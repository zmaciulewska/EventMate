import { CategoryService } from './../services/category.service';
import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/domain/category';
import { Router } from '../../../node_modules/@angular/router';

@Component({
  selector: 'app-categories-list',
  templateUrl: './categories-list.component.html',
  styleUrls: ['./categories-list.component.css']
})
export class CategoriesListComponent implements OnInit {

  categories: Category[];
  errorMessage: string;

  categoryForm: Category;

  constructor(private categoryService: CategoryService,
    private router: Router) { }

  ngOnInit() {
    this.categoryForm = new Category();
    this.categoryService.getAll().subscribe(
      data => {
        this.categories = data;
      },
      error => {
        this.errorMessage = error;
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

}
