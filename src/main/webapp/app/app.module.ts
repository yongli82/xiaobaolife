import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { XiaobaolifeSharedModule, UserRouteAccessService } from './shared';
import { XiaobaolifeAppRoutingModule} from './app-routing.module';
import { XiaobaolifeHomeModule } from './home/home.module';
import { XiaobaolifeAdminModule } from './admin/admin.module';
import { XiaobaolifeAccountModule } from './account/account.module';
import { XiaobaolifeEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        XiaobaolifeAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        XiaobaolifeSharedModule,
        XiaobaolifeHomeModule,
        XiaobaolifeAdminModule,
        XiaobaolifeAccountModule,
        XiaobaolifeEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class XiaobaolifeAppModule {}
