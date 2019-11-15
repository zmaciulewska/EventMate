import { NG_VALIDATORS, Validator, ValidationErrors, FormGroup } from '@angular/forms';

// custom validator to check if start date is before end date
export function DateCorrect(startDate: string, endDate: string) {
    return (formGroup: FormGroup) => {
        const startDateValue = formGroup.controls[startDate];
        const endDateValue = formGroup.controls[endDate];

        // return null if controls haven't initialised yet
        if (!startDateValue || !endDateValue) {
            return null;
        }

        // return null if another validator has already found an error on the matchingControl
        if (endDateValue.errors && !endDateValue.errors.mustMatch) {
            return null;
        }

        // set error on matchingControl if validation fails
        if (startDateValue.value !== endDateValue.value) {
            endDateValue.setErrors({ mustMatch: true });
        } else {
            endDateValue.setErrors(null);
        }
    };

}
