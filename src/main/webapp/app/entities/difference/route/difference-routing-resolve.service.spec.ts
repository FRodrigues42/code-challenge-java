jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDifference, Difference } from '../difference.model';
import { DifferenceService } from '../service/difference.service';

import { DifferenceRoutingResolveService } from './difference-routing-resolve.service';

describe('Service Tests', () => {
  describe('Difference routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DifferenceRoutingResolveService;
    let service: DifferenceService;
    let resultDifference: IDifference | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DifferenceRoutingResolveService);
      service = TestBed.inject(DifferenceService);
      resultDifference = undefined;
    });

    describe('resolve', () => {
      it('should return IDifference returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDifference = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDifference).toEqual({ id: 123 });
      });

      it('should return new IDifference if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDifference = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDifference).toEqual(new Difference());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Difference })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDifference = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDifference).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
