import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { XiaobaolifeArticleModule } from './article/article.module';
import { XiaobaolifeCategoryModule } from './category/category.module';
import { XiaobaolifeMarkTagModule } from './mark-tag/mark-tag.module';
import { XiaobaolifeArticleCategoryModule } from './article-category/article-category.module';
import { XiaobaolifeArticleTagModule } from './article-tag/article-tag.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        XiaobaolifeArticleModule,
        XiaobaolifeCategoryModule,
        XiaobaolifeMarkTagModule,
        XiaobaolifeArticleCategoryModule,
        XiaobaolifeArticleTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class XiaobaolifeEntityModule {}
