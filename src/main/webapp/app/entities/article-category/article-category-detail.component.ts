import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ArticleCategory } from './article-category.model';
import { ArticleCategoryService } from './article-category.service';

@Component({
    selector: 'jhi-article-category-detail',
    templateUrl: './article-category-detail.component.html'
})
export class ArticleCategoryDetailComponent implements OnInit, OnDestroy {

    articleCategory: ArticleCategory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private articleCategoryService: ArticleCategoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInArticleCategories();
    }

    load(id) {
        this.articleCategoryService.find(id).subscribe((articleCategory) => {
            this.articleCategory = articleCategory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInArticleCategories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'articleCategoryListModification',
            (response) => this.load(this.articleCategory.id)
        );
    }
}
