import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ArticleCategory } from './article-category.model';
import { ArticleCategoryPopupService } from './article-category-popup.service';
import { ArticleCategoryService } from './article-category.service';

@Component({
    selector: 'jhi-article-category-delete-dialog',
    templateUrl: './article-category-delete-dialog.component.html'
})
export class ArticleCategoryDeleteDialogComponent {

    articleCategory: ArticleCategory;

    constructor(
        private articleCategoryService: ArticleCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.articleCategoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'articleCategoryListModification',
                content: 'Deleted an articleCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-article-category-delete-popup',
    template: ''
})
export class ArticleCategoryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articleCategoryPopupService: ArticleCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.articleCategoryPopupService
                .open(ArticleCategoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
