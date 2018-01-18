import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ArticleTag } from './article-tag.model';
import { ArticleTagService } from './article-tag.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-article-tag',
    templateUrl: './article-tag.component.html'
})
export class ArticleTagComponent implements OnInit, OnDestroy {
articleTags: ArticleTag[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private articleTagService: ArticleTagService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.articleTagService.query().subscribe(
            (res: ResponseWrapper) => {
                this.articleTags = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInArticleTags();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ArticleTag) {
        return item.id;
    }
    registerChangeInArticleTags() {
        this.eventSubscriber = this.eventManager.subscribe('articleTagListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
