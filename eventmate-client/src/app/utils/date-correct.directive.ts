import { Directive, Input } from '@angular/core';
import { NG_VALIDATORS, Validator, FormGroup, ValidationErrors } from '@angular/forms';
import { DateCorrect } from './date-correct.validator';

@Directive({
    // tslint:disable-next-line:directive-selector
    selector: '[dateCorrect]',
    providers: [{ provide: NG_VALIDATORS, useExisting: DateCorrectDirective, multi: true }]
})
export class DateCorrectDirective implements Validator {
    @Input('dateCorrect') date: string[] = [];

    validate(formGroup: FormGroup): ValidationErrors {
        return DateCorrect(this.date[0], this.date[1])(formGroup);
    }
}
