import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MarkTag } from './mark-tag.model';
import { MarkTagService } from './mark-tag.service';

@Component({
    selector: 'jhi-mark-tag-detail',
    templateUrl: './mark-tag-detail.component.html'
})
export class MarkTagDetailComponent implements OnInit, OnDestroy {

    markTag: MarkTag;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private markTagService: MarkTagService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarkTags();
    }

    load(id) {
        this.markTagService.find(id).subscribe((markTag) => {
            this.markTag = markTag;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarkTags() {
        this.eventSubscriber = this.eventManager.subscribe(
            'markTagListModification',
            (response) => this.load(this.markTag.id)
        );
    }
}
