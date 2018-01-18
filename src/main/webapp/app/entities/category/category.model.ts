import { BaseEntity } from './../../shared';

export const enum RecordStatus {
    'VALID',
    'INVALID'
}

export class Category implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public description?: string,
        public coverImageBig?: string,
        public coverImageSmall?: string,
        public memo?: string,
        public recordStatus?: RecordStatus,
    ) {
    }
}
