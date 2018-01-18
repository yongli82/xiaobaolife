import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { MarkTag } from './mark-tag.model';
import { MarkTagService } from './mark-tag.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-mark-tag',
    templateUrl: './mark-tag.component.html'
})
export class MarkTagComponent implements OnInit, OnDestroy {
markTags: MarkTag[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private markTagService: MarkTagService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.markTagService.query().subscribe(
            (res: ResponseWrapper) => {
                this.markTags = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMarkTags();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MarkTag) {
        return item.id;
    }
    registerChangeInMarkTags() {
        this.eventSubscriber = this.eventManager.subscribe('markTagListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
