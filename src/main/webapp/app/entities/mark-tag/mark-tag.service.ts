import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { MarkTag } from './mark-tag.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MarkTagService {

    private resourceUrl = SERVER_API_URL + 'api/mark-tags';

    constructor(private http: Http) { }

    create(markTag: MarkTag): Observable<MarkTag> {
        const copy = this.convert(markTag);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(markTag: MarkTag): Observable<MarkTag> {
        const copy = this.convert(markTag);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MarkTag> {
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
     * Convert a returned JSON object to MarkTag.
     */
    private convertItemFromServer(json: any): MarkTag {
        const entity: MarkTag = Object.assign(new MarkTag(), json);
        return entity;
    }

    /**
     * Convert a MarkTag to a JSON which can be sent to the server.
     */
    private convert(markTag: MarkTag): MarkTag {
        const copy: MarkTag = Object.assign({}, markTag);
        return copy;
    }
}
