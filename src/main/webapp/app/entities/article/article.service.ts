import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Article } from './article.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ArticleService {

    private resourceUrl = SERVER_API_URL + 'api/articles';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(article: Article): Observable<Article> {
        const copy = this.convert(article);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(article: Article): Observable<Article> {
        const copy = this.convert(article);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Article> {
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
     * Convert a returned JSON object to Article.
     */
    private convertItemFromServer(json: any): Article {
        const entity: Article = Object.assign(new Article(), json);
        entity.publishTime = this.dateUtils
            .convertDateTimeFromServer(json.publishTime);
        return entity;
    }

    /**
     * Convert a Article to a JSON which can be sent to the server.
     */
    private convert(article: Article): Article {
        const copy: Article = Object.assign({}, article);

        copy.publishTime = this.dateUtils.toDate(article.publishTime);
        return copy;
    }
}
