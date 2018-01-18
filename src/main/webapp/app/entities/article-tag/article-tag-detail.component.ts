import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ArticleTag } from './article-tag.model';
import { ArticleTagService } from './article-tag.service';

@Component({
    selector: 'jhi-article-tag-detail',
    templateUrl: './article-tag-detail.component.html'
})
export class ArticleTagDetailComponent implements OnInit, OnDestroy {

    articleTag: ArticleTag;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private articleTagService: ArticleTagService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInArticleTags();
    }

    load(id) {
        this.articleTagService.find(id).subscribe((articleTag) => {
            this.articleTag = articleTag;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInArticleTags() {
        this.eventSubscriber = this.eventManager.subscribe(
            'articleTagListModification',
            (response) => this.load(this.articleTag.id)
        );
    }
}
