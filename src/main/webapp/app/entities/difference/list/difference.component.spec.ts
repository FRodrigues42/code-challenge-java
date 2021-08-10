import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DifferenceService } from '../service/difference.service';

import { DifferenceComponent } from './difference.component';

describe('Component Tests', () => {
  describe('Difference Management Component', () => {
    let comp: DifferenceComponent;
    let fixture: ComponentFixture<DifferenceComponent>;
    let service: DifferenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DifferenceComponent],
      })
        .overrideTemplate(DifferenceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DifferenceComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(DifferenceService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.differences?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
