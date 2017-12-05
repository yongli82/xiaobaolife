import { BaseEntity } from './../../shared';

export const enum RecordStatus {
    'VALID',
    'INVALID'
}

export class Article implements BaseEntity {
    constructor(
        public id?: number,
        public uuid?: string,
        public title?: string,
        public subTitle?: string,
        public authorName?: string,
        public introduction?: string,
        public coverImageBig?: string,
        public coverImageSmall?: string,
        public content?: string,
        public publishTime?: any,
        public memo?: string,
        public recordStatus?: RecordStatus,
    ) {
    }
}
