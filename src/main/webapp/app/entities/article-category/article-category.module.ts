import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { XiaobaolifeSharedModule } from '../../shared';
import {
    ArticleCategoryService,
    ArticleCategoryPopupService,
    ArticleCategoryComponent,
    ArticleCategoryDetailComponent,
    ArticleCategoryDialogComponent,
    ArticleCategoryPopupComponent,
    ArticleCategoryDeletePopupComponent,
    ArticleCategoryDeleteDialogComponent,
    articleCategoryRoute,
    articleCategoryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...articleCategoryRoute,
    ...articleCategoryPopupRoute,
];

@NgModule({
    imports: [
        XiaobaolifeSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ArticleCategoryComponent,
        ArticleCategoryDetailComponent,
        ArticleCategoryDialogComponent,
        ArticleCategoryDeleteDialogComponent,
        ArticleCategoryPopupComponent,
        ArticleCategoryDeletePopupComponent,
    ],
    entryComponents: [
        ArticleCategoryComponent,
        ArticleCategoryDialogComponent,
        ArticleCategoryPopupComponent,
        ArticleCategoryDeleteDialogComponent,
        ArticleCategoryDeletePopupComponent,
    ],
    providers: [
        ArticleCategoryService,
        ArticleCategoryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class XiaobaolifeArticleCategoryModule {}
