import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { XiaobaolifeSharedModule } from '../../shared';
import {
    ArticleTagService,
    ArticleTagPopupService,
    ArticleTagComponent,
    ArticleTagDetailComponent,
    ArticleTagDialogComponent,
    ArticleTagPopupComponent,
    ArticleTagDeletePopupComponent,
    ArticleTagDeleteDialogComponent,
    articleTagRoute,
    articleTagPopupRoute,
} from './';

const ENTITY_STATES = [
    ...articleTagRoute,
    ...articleTagPopupRoute,
];

@NgModule({
    imports: [
        XiaobaolifeSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ArticleTagComponent,
        ArticleTagDetailComponent,
        ArticleTagDialogComponent,
        ArticleTagDeleteDialogComponent,
        ArticleTagPopupComponent,
        ArticleTagDeletePopupComponent,
    ],
    entryComponents: [
        ArticleTagComponent,
        ArticleTagDialogComponent,
        ArticleTagPopupComponent,
        ArticleTagDeleteDialogComponent,
        ArticleTagDeletePopupComponent,
    ],
    providers: [
        ArticleTagService,
        ArticleTagPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class XiaobaolifeArticleTagModule {}
