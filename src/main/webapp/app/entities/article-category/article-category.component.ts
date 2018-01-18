import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ArticleCategory } from './article-category.model';
import { ArticleCategoryService } from './article-category.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-article-category',
    templateUrl: './article-category.component.html'
})
export class ArticleCategoryComponent implements OnInit, OnDestroy {
articleCategories: ArticleCategory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private articleCategoryService: ArticleCategoryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.articleCategoryService.query().subscribe(
            (res: ResponseWrapper) => {
                this.articleCategories = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInArticleCategories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ArticleCategory) {
        return item.id;
    }
    registerChangeInArticleCategories() {
        this.eventSubscriber = this.eventManager.subscribe('articleCategoryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
