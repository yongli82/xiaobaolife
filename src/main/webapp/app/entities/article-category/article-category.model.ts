import { BaseEntity } from './../../shared';

export class ArticleCategory implements BaseEntity {
    constructor(
        public id?: number,
        public categoryCode?: string,
        public articleUuId?: string,
        public addTime?: any,
    ) {
    }
}
