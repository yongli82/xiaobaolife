import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MarkTag } from './mark-tag.model';
import { MarkTagPopupService } from './mark-tag-popup.service';
import { MarkTagService } from './mark-tag.service';

@Component({
    selector: 'jhi-mark-tag-dialog',
    templateUrl: './mark-tag-dialog.component.html'
})
export class MarkTagDialogComponent implements OnInit {

    markTag: MarkTag;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private markTagService: MarkTagService,
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
        if (this.markTag.id !== undefined) {
            this.subscribeToSaveResponse(
                this.markTagService.update(this.markTag));
        } else {
            this.subscribeToSaveResponse(
                this.markTagService.create(this.markTag));
        }
    }

    private subscribeToSaveResponse(result: Observable<MarkTag>) {
        result.subscribe((res: MarkTag) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MarkTag) {
        this.eventManager.broadcast({ name: 'markTagListModification', content: 'OK'});
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
    selector: 'jhi-mark-tag-popup',
    template: ''
})
export class MarkTagPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private markTagPopupService: MarkTagPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.markTagPopupService
                    .open(MarkTagDialogComponent as Component, params['id']);
            } else {
                this.markTagPopupService
                    .open(MarkTagDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
