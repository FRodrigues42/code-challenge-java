import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDifference, getDifferenceIdentifier } from '../difference.model';

export type EntityResponseType = HttpResponse<IDifference>;
export type EntityArrayResponseType = HttpResponse<IDifference[]>;

@Injectable({ providedIn: 'root' })
export class DifferenceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/differences');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(difference: IDifference): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(difference);
    return this.http
      .post<IDifference>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(difference: IDifference): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(difference);
    return this.http
      .put<IDifference>(`${this.resourceUrl}/${getDifferenceIdentifier(difference) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(difference: IDifference): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(difference);
    return this.http
      .patch<IDifference>(`${this.resourceUrl}/${getDifferenceIdentifier(difference) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDifference>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDifference[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDifferenceToCollectionIfMissing(
    differenceCollection: IDifference[],
    ...differencesToCheck: (IDifference | null | undefined)[]
  ): IDifference[] {
    const differences: IDifference[] = differencesToCheck.filter(isPresent);
    if (differences.length > 0) {
      const differenceCollectionIdentifiers = differenceCollection.map(differenceItem => getDifferenceIdentifier(differenceItem)!);
      const differencesToAdd = differences.filter(differenceItem => {
        const differenceIdentifier = getDifferenceIdentifier(differenceItem);
        if (differenceIdentifier == null || differenceCollectionIdentifiers.includes(differenceIdentifier)) {
          return false;
        }
        differenceCollectionIdentifiers.push(differenceIdentifier);
        return true;
      });
      return [...differencesToAdd, ...differenceCollection];
    }
    return differenceCollection;
  }

  protected convertDateFromClient(difference: IDifference): IDifference {
    return Object.assign({}, difference, {
      datetime: difference.datetime?.isValid() ? difference.datetime.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.datetime = res.body.datetime ? dayjs(res.body.datetime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((difference: IDifference) => {
        difference.datetime = difference.datetime ? dayjs(difference.datetime) : undefined;
      });
    }
    return res;
  }
}
