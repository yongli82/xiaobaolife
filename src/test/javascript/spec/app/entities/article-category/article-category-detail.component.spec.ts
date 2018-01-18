/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { XiaobaolifeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ArticleCategoryDetailComponent } from '../../../../../../main/webapp/app/entities/article-category/article-category-detail.component';
import { ArticleCategoryService } from '../../../../../../main/webapp/app/entities/article-category/article-category.service';
import { ArticleCategory } from '../../../../../../main/webapp/app/entities/article-category/article-category.model';

describe('Component Tests', () => {

    describe('ArticleCategory Management Detail Component', () => {
        let comp: ArticleCategoryDetailComponent;
        let fixture: ComponentFixture<ArticleCategoryDetailComponent>;
        let service: ArticleCategoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [XiaobaolifeTestModule],
                declarations: [ArticleCategoryDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ArticleCategoryService,
                    JhiEventManager
                ]
            }).overrideTemplate(ArticleCategoryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticleCategoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticleCategoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ArticleCategory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.articleCategory).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
