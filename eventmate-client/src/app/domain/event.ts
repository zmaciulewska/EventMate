export class Event {
    id: number;
    title: string;
    description: string;
    localization: string;
    startDate: Date;
    endDate: Date;
    common: boolean;
    administratorId: number;
    reporterId: number;
    continous: boolean;
    siteUrl: string;
    creationDate: Date;
    removalDate: Date;
    costIds: number[];
    categoryIds: number[];
}
