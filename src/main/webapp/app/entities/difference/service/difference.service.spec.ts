import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDifference, Difference } from '../difference.model';

import { DifferenceService } from './difference.service';

describe('Service Tests', () => {
  describe('Difference Service', () => {
    let service: DifferenceService;
    let httpMock: HttpTestingController;
    let elemDefault: IDifference;
    let expectedResult: IDifference | IDifference[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DifferenceService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        datetime: currentDate,
        value: 0,
        number: 0,
        occurrences: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            datetime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Difference', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            datetime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datetime: currentDate,
          },
          returnedFromService
        );

        service.create(new Difference()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Difference', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            datetime: currentDate.format(DATE_TIME_FORMAT),
            value: 1,
            number: 1,
            occurrences: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datetime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Difference', () => {
        const patchObject = Object.assign(
          {
            datetime: currentDate.format(DATE_TIME_FORMAT),
            value: 1,
            number: 1,
          },
          new Difference()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            datetime: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Difference', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            datetime: currentDate.format(DATE_TIME_FORMAT),
            value: 1,
            number: 1,
            occurrences: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datetime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Difference', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDifferenceToCollectionIfMissing', () => {
        it('should add a Difference to an empty array', () => {
          const difference: IDifference = { id: 123 };
          expectedResult = service.addDifferenceToCollectionIfMissing([], difference);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(difference);
        });

        it('should not add a Difference to an array that contains it', () => {
          const difference: IDifference = { id: 123 };
          const differenceCollection: IDifference[] = [
            {
              ...difference,
            },
            { id: 456 },
          ];
          expectedResult = service.addDifferenceToCollectionIfMissing(differenceCollection, difference);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Difference to an array that doesn't contain it", () => {
          const difference: IDifference = { id: 123 };
          const differenceCollection: IDifference[] = [{ id: 456 }];
          expectedResult = service.addDifferenceToCollectionIfMissing(differenceCollection, difference);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(difference);
        });

        it('should add only unique Difference to an array', () => {
          const differenceArray: IDifference[] = [{ id: 123 }, { id: 456 }, { id: 49852 }];
          const differenceCollection: IDifference[] = [{ id: 123 }];
          expectedResult = service.addDifferenceToCollectionIfMissing(differenceCollection, ...differenceArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const difference: IDifference = { id: 123 };
          const difference2: IDifference = { id: 456 };
          expectedResult = service.addDifferenceToCollectionIfMissing([], difference, difference2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(difference);
          expect(expectedResult).toContain(difference2);
        });

        it('should accept null and undefined values', () => {
          const difference: IDifference = { id: 123 };
          expectedResult = service.addDifferenceToCollectionIfMissing([], null, difference, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(difference);
        });

        it('should return initial array if no Difference is added', () => {
          const differenceCollection: IDifference[] = [{ id: 123 }];
          expectedResult = service.addDifferenceToCollectionIfMissing(differenceCollection, undefined, null);
          expect(expectedResult).toEqual(differenceCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
