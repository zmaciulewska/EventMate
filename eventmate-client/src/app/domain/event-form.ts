import { CostForm } from './cost-form';

export class EventForm {
    title: string;
    description: string;
    localization: string;
    startDate: string;
    endDate: string;
    common: boolean;
    continous: boolean;
    siteUrl: string;
    costs: CostForm[];
    categoryIds: number[];
}
