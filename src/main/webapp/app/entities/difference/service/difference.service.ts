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
  protected resourceUrl = this.applicationConfigService.getEndpointFor('difference');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  get(n: number): Observable<EntityResponseType> {
    return this.http
      .get<IDifference>(`${this.resourceUrl}?number=${n}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
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
