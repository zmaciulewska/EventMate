import { Showcase } from './showcase';
import { Authority } from './authority';

export class User {
    id: number;
    username: string;
    password: string;
    email: string;
    // authorities: Authority[];
    showcase: Showcase;
}
