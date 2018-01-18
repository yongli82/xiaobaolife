import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ArticleCategoryComponent } from './article-category.component';
import { ArticleCategoryDetailComponent } from './article-category-detail.component';
import { ArticleCategoryPopupComponent } from './article-category-dialog.component';
import { ArticleCategoryDeletePopupComponent } from './article-category-delete-dialog.component';

export const articleCategoryRoute: Routes = [
    {
        path: 'article-category',
        component: ArticleCategoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xiaobaolifeApp.articleCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'article-category/:id',
        component: ArticleCategoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xiaobaolifeApp.articleCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const articleCategoryPopupRoute: Routes = [
    {
        path: 'article-category-new',
        component: ArticleCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xiaobaolifeApp.articleCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'article-category/:id/edit',
        component: ArticleCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xiaobaolifeApp.articleCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'article-category/:id/delete',
        component: ArticleCategoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xiaobaolifeApp.articleCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
