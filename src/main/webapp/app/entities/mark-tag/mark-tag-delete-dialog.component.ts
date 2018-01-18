import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MarkTag } from './mark-tag.model';
import { MarkTagPopupService } from './mark-tag-popup.service';
import { MarkTagService } from './mark-tag.service';

@Component({
    selector: 'jhi-mark-tag-delete-dialog',
    templateUrl: './mark-tag-delete-dialog.component.html'
})
export class MarkTagDeleteDialogComponent {

    markTag: MarkTag;

    constructor(
        private markTagService: MarkTagService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.markTagService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'markTagListModification',
                content: 'Deleted an markTag'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mark-tag-delete-popup',
    template: ''
})
export class MarkTagDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private markTagPopupService: MarkTagPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.markTagPopupService
                .open(MarkTagDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
