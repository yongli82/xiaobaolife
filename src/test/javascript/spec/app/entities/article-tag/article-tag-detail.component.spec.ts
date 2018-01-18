/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { XiaobaolifeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ArticleTagDetailComponent } from '../../../../../../main/webapp/app/entities/article-tag/article-tag-detail.component';
import { ArticleTagService } from '../../../../../../main/webapp/app/entities/article-tag/article-tag.service';
import { ArticleTag } from '../../../../../../main/webapp/app/entities/article-tag/article-tag.model';

describe('Component Tests', () => {

    describe('ArticleTag Management Detail Component', () => {
        let comp: ArticleTagDetailComponent;
        let fixture: ComponentFixture<ArticleTagDetailComponent>;
        let service: ArticleTagService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [XiaobaolifeTestModule],
                declarations: [ArticleTagDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ArticleTagService,
                    JhiEventManager
                ]
            }).overrideTemplate(ArticleTagDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticleTagDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticleTagService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ArticleTag(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.articleTag).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
