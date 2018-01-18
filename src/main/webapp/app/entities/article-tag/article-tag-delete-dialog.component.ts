import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ArticleTag } from './article-tag.model';
import { ArticleTagPopupService } from './article-tag-popup.service';
import { ArticleTagService } from './article-tag.service';

@Component({
    selector: 'jhi-article-tag-delete-dialog',
    templateUrl: './article-tag-delete-dialog.component.html'
})
export class ArticleTagDeleteDialogComponent {

    articleTag: ArticleTag;

    constructor(
        private articleTagService: ArticleTagService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.articleTagService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'articleTagListModification',
                content: 'Deleted an articleTag'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-article-tag-delete-popup',
    template: ''
})
export class ArticleTagDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articleTagPopupService: ArticleTagPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.articleTagPopupService
                .open(ArticleTagDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
