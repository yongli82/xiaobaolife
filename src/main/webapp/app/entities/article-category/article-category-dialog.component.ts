import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ArticleCategory } from './article-category.model';
import { ArticleCategoryPopupService } from './article-category-popup.service';
import { ArticleCategoryService } from './article-category.service';

@Component({
    selector: 'jhi-article-category-dialog',
    templateUrl: './article-category-dialog.component.html'
})
export class ArticleCategoryDialogComponent implements OnInit {

    articleCategory: ArticleCategory;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private articleCategoryService: ArticleCategoryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.articleCategory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.articleCategoryService.update(this.articleCategory));
        } else {
            this.subscribeToSaveResponse(
                this.articleCategoryService.create(this.articleCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<ArticleCategory>) {
        result.subscribe((res: ArticleCategory) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ArticleCategory) {
        this.eventManager.broadcast({ name: 'articleCategoryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-article-category-popup',
    template: ''
})
export class ArticleCategoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articleCategoryPopupService: ArticleCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.articleCategoryPopupService
                    .open(ArticleCategoryDialogComponent as Component, params['id']);
            } else {
                this.articleCategoryPopupService
                    .open(ArticleCategoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
