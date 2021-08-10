import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'difference',
        data: { pageTitle: 'codeChallengeJavaApp.difference.home.title' },
        loadChildren: () => import('./difference/difference.module').then(m => m.DifferenceModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
