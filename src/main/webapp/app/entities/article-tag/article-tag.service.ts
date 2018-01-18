import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ArticleTag } from './article-tag.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ArticleTagService {

    private resourceUrl = SERVER_API_URL + 'api/article-tags';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(articleTag: ArticleTag): Observable<ArticleTag> {
        const copy = this.convert(articleTag);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(articleTag: ArticleTag): Observable<ArticleTag> {
        const copy = this.convert(articleTag);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ArticleTag> {
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
     * Convert a returned JSON object to ArticleTag.
     */
    private convertItemFromServer(json: any): ArticleTag {
        const entity: ArticleTag = Object.assign(new ArticleTag(), json);
        entity.addTime = this.dateUtils
            .convertDateTimeFromServer(json.addTime);
        return entity;
    }

    /**
     * Convert a ArticleTag to a JSON which can be sent to the server.
     */
    private convert(articleTag: ArticleTag): ArticleTag {
        const copy: ArticleTag = Object.assign({}, articleTag);

        copy.addTime = this.dateUtils.toDate(articleTag.addTime);
        return copy;
    }
}
