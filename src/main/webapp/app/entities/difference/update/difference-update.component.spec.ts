jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DifferenceService } from '../service/difference.service';
import { IDifference, Difference } from '../difference.model';

import { DifferenceUpdateComponent } from './difference-update.component';

describe('Component Tests', () => {
  describe('Difference Management Update Component', () => {
    let comp: DifferenceUpdateComponent;
    let fixture: ComponentFixture<DifferenceUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let differenceService: DifferenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DifferenceUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DifferenceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DifferenceUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      differenceService = TestBed.inject(DifferenceService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const difference: IDifference = { id: 456 };

        activatedRoute.data = of({ difference });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(difference));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Difference>>();
        const difference = { id: 123 };
        jest.spyOn(differenceService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ difference });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: difference }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(differenceService.update).toHaveBeenCalledWith(difference);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Difference>>();
        const difference = new Difference();
        jest.spyOn(differenceService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ difference });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: difference }));
        saveSubject.complete();

        // THEN
        expect(differenceService.create).toHaveBeenCalledWith(difference);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Difference>>();
        const difference = { id: 123 };
        jest.spyOn(differenceService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ difference });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(differenceService.update).toHaveBeenCalledWith(difference);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
