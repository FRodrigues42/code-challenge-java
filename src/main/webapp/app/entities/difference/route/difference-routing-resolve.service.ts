import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDifference, Difference } from '../difference.model';
import { DifferenceService } from '../service/difference.service';

@Injectable({ providedIn: 'root' })
export class DifferenceRoutingResolveService implements Resolve<IDifference> {
  constructor(protected service: DifferenceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDifference> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((difference: HttpResponse<Difference>) => {
          if (difference.body) {
            return of(difference.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Difference());
  }
}
