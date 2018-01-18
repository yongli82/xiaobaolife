import { BaseEntity } from './../../shared';

export class ArticleTag implements BaseEntity {
    constructor(
        public id?: number,
        public tagUuId?: string,
        public articleUuId?: string,
        public addTime?: any,
    ) {
    }
}
