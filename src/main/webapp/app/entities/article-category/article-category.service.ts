import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ArticleCategory } from './article-category.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ArticleCategoryService {

    private resourceUrl = SERVER_API_URL + 'api/article-categories';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(articleCategory: ArticleCategory): Observable<ArticleCategory> {
        const copy = this.convert(articleCategory);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(articleCategory: ArticleCategory): Observable<ArticleCategory> {
        const copy = this.convert(articleCategory);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ArticleCategory> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to ArticleCategory.
     */
    private convertItemFromServer(json: any): ArticleCategory {
        const entity: ArticleCategory = Object.assign(new ArticleCategory(), json);
        entity.addTime = this.dateUtils
            .convertDateTimeFromServer(json.addTime);
        return entity;
    }

    /**
     * Convert a ArticleCategory to a JSON which can be sent to the server.
     */
    private convert(articleCategory: ArticleCategory): ArticleCategory {
        const copy: ArticleCategory = Object.assign({}, articleCategory);

        copy.addTime = this.dateUtils.toDate(articleCategory.addTime);
        return copy;
    }
}
