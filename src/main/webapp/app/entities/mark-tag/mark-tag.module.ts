import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { XiaobaolifeSharedModule } from '../../shared';
import {
    MarkTagService,
    MarkTagPopupService,
    MarkTagComponent,
    MarkTagDetailComponent,
    MarkTagDialogComponent,
    MarkTagPopupComponent,
    MarkTagDeletePopupComponent,
    MarkTagDeleteDialogComponent,
    markTagRoute,
    markTagPopupRoute,
} from './';

const ENTITY_STATES = [
    ...markTagRoute,
    ...markTagPopupRoute,
];

@NgModule({
    imports: [
        XiaobaolifeSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MarkTagComponent,
        MarkTagDetailComponent,
        MarkTagDialogComponent,
        MarkTagDeleteDialogComponent,
        MarkTagPopupComponent,
        MarkTagDeletePopupComponent,
    ],
    entryComponents: [
        MarkTagComponent,
        MarkTagDialogComponent,
        MarkTagPopupComponent,
        MarkTagDeleteDialogComponent,
        MarkTagDeletePopupComponent,
    ],
    providers: [
        MarkTagService,
        MarkTagPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class XiaobaolifeMarkTagModule {}
