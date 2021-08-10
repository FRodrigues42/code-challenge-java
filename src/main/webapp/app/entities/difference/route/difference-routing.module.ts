import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DifferenceComponent } from '../list/difference.component';
import { DifferenceDetailComponent } from '../detail/difference-detail.component';
import { DifferenceUpdateComponent } from '../update/difference-update.component';
import { DifferenceRoutingResolveService } from './difference-routing-resolve.service';

const differenceRoute: Routes = [
  {
    path: '',
    component: DifferenceComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DifferenceDetailComponent,
    resolve: {
      difference: DifferenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DifferenceUpdateComponent,
    resolve: {
      difference: DifferenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DifferenceUpdateComponent,
    resolve: {
      difference: DifferenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(differenceRoute)],
  exports: [RouterModule],
})
export class DifferenceRoutingModule {}
