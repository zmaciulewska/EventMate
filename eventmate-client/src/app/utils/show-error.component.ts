import { Component, Input } from '@angular/core';
import { AbstractControlDirective, AbstractControl } from '@angular/forms';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'show-errors',
  template: `
   <div *ngIf="shouldShowErrors()">
        <div *ngFor="let error of listOfErrors()">
            <p class="text-label"> {{error}}</p>
        </div>
    </div>
  `,
})
/*
<ul *ngIf="shouldShowErrors()">
      <li style="list-style-type: none;" *ngFor="let error of listOfErrors()">
      {{error}}
      </li>
    </ul> */
export class ShowErrorComponent {

  private static readonly errorMessages = {
    'required': () => 'Wymagane pole',
    /* 'range': () => 'Value is out of the range', */
    'minlength': (params) => 'Minimalna liczba znaków ' + params.requiredLength,
    'maxlength': (params) => 'Maksymalna dozwolona liczba znaków' + params.requiredLength,
    'pattern': (params) => 'Wymagany wzór: ' + params.requiredPattern,
    'years': (params) => params.message,
    'countryCity': (params) => params.message,
    'uniqueName': (params) => params.message,
    'telephoneNumbers': (params) => params.message,
    'telephoneNumber': (params) => params.message
  };

  @Input()
  private control: AbstractControlDirective | AbstractControl;

  shouldShowErrors(): boolean {
    return this.control &&
      this.control.errors &&
      (this.control.dirty || this.control.touched);
  }

  listOfErrors(): string[] {
    return Object.keys(this.control.errors)
      .map(field => this.getMessage(field, this.control.errors[field]));
  }

  private getMessage(type: string, params: any) {
    return ShowErrorComponent.errorMessages[type](params);
  }

}
