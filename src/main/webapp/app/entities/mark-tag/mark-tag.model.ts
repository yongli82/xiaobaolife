import { BaseEntity } from './../../shared';

export const enum RecordStatus {
    'VALID',
    'INVALID'
}

export class MarkTag implements BaseEntity {
    constructor(
        public id?: number,
        public uuid?: string,
        public name?: string,
        public recordStatus?: RecordStatus,
    ) {
    }
}
