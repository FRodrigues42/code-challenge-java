import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DifferenceDetailComponent } from './difference-detail.component';

describe('Component Tests', () => {
  describe('Difference Management Detail Component', () => {
    let comp: DifferenceDetailComponent;
    let fixture: ComponentFixture<DifferenceDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DifferenceDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ difference: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DifferenceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DifferenceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load difference on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.difference).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
