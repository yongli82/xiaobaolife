import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MarkTagComponent } from './mark-tag.component';
import { MarkTagDetailComponent } from './mark-tag-detail.component';
import { MarkTagPopupComponent } from './mark-tag-dialog.component';
import { MarkTagDeletePopupComponent } from './mark-tag-delete-dialog.component';

export const markTagRoute: Routes = [
    {
        path: 'mark-tag',
        component: MarkTagComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xiaobaolifeApp.markTag.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mark-tag/:id',
        component: MarkTagDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xiaobaolifeApp.markTag.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const markTagPopupRoute: Routes = [
    {
        path: 'mark-tag-new',
        component: MarkTagPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xiaobaolifeApp.markTag.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mark-tag/:id/edit',
        component: MarkTagPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xiaobaolifeApp.markTag.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mark-tag/:id/delete',
        component: MarkTagDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xiaobaolifeApp.markTag.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
