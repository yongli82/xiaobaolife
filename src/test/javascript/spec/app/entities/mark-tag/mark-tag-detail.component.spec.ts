/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { XiaobaolifeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarkTagDetailComponent } from '../../../../../../main/webapp/app/entities/mark-tag/mark-tag-detail.component';
import { MarkTagService } from '../../../../../../main/webapp/app/entities/mark-tag/mark-tag.service';
import { MarkTag } from '../../../../../../main/webapp/app/entities/mark-tag/mark-tag.model';

describe('Component Tests', () => {

    describe('MarkTag Management Detail Component', () => {
        let comp: MarkTagDetailComponent;
        let fixture: ComponentFixture<MarkTagDetailComponent>;
        let service: MarkTagService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [XiaobaolifeTestModule],
                declarations: [MarkTagDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarkTagService,
                    JhiEventManager
                ]
            }).overrideTemplate(MarkTagDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarkTagDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarkTagService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarkTag(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.markTag).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
