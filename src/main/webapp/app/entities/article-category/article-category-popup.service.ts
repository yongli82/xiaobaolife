import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ArticleCategory } from './article-category.model';
import { ArticleCategoryService } from './article-category.service';

@Injectable()
export class ArticleCategoryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private articleCategoryService: ArticleCategoryService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.articleCategoryService.find(id).subscribe((articleCategory) => {
                    articleCategory.addTime = this.datePipe
                        .transform(articleCategory.addTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.articleCategoryModalRef(component, articleCategory);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.articleCategoryModalRef(component, new ArticleCategory());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    articleCategoryModalRef(component: Component, articleCategory: ArticleCategory): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.articleCategory = articleCategory;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
