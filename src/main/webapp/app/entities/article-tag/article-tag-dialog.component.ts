import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ArticleTag } from './article-tag.model';
import { ArticleTagPopupService } from './article-tag-popup.service';
import { ArticleTagService } from './article-tag.service';

@Component({
    selector: 'jhi-article-tag-dialog',
    templateUrl: './article-tag-dialog.component.html'
})
export class ArticleTagDialogComponent implements OnInit {

    articleTag: ArticleTag;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private articleTagService: ArticleTagService,
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
        if (this.articleTag.id !== undefined) {
            this.subscribeToSaveResponse(
                this.articleTagService.update(this.articleTag));
        } else {
            this.subscribeToSaveResponse(
                this.articleTagService.create(this.articleTag));
        }
    }

    private subscribeToSaveResponse(result: Observable<ArticleTag>) {
        result.subscribe((res: ArticleTag) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ArticleTag) {
        this.eventManager.broadcast({ name: 'articleTagListModification', content: 'OK'});
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
    selector: 'jhi-article-tag-popup',
    template: ''
})
export class ArticleTagPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articleTagPopupService: ArticleTagPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.articleTagPopupService
                    .open(ArticleTagDialogComponent as Component, params['id']);
            } else {
                this.articleTagPopupService
                    .open(ArticleTagDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
