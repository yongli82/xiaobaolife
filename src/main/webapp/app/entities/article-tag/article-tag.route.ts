import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ArticleTagComponent } from './article-tag.component';
import { ArticleTagDetailComponent } from './article-tag-detail.component';
import { ArticleTagPopupComponent } from './article-tag-dialog.component';
import { ArticleTagDeletePopupComponent } from './article-tag-delete-dialog.component';

export const articleTagRoute: Routes = [
    {
        path: 'article-tag',
        component: ArticleTagComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xiaobaolifeApp.articleTag.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'article-tag/:id',
        component: ArticleTagDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xiaobaolifeApp.articleTag.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const articleTagPopupRoute: Routes = [
    {
        path: 'article-tag-new',
        component: ArticleTagPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xiaobaolifeApp.articleTag.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'article-tag/:id/edit',
        component: ArticleTagPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xiaobaolifeApp.articleTag.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'article-tag/:id/delete',
        component: ArticleTagDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xiaobaolifeApp.articleTag.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
