export class EventOffer {
    id: number;
    ownerId: number;
    eventId: number;
    creationDate: Date;

    prefferedDate: Date;
    prefferedGender: string;
    prefferedMinAge: number;
    prefferedMaxAge: number;
    prefferedLocalization: string; // not used
    description: string;
}
