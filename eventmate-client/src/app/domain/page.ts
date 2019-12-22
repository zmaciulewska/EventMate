export class Page<T> {
    totalElements: number;      // total number of items
    totalPages: number;
    size: number;
    number: number;
    last: boolean;
    first: boolean;
    content: Array<T>;  // items for the current page
  }
