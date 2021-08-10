import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DifferenceComponent } from './list/difference.component';
import { DifferenceDetailComponent } from './detail/difference-detail.component';
import { DifferenceUpdateComponent } from './update/difference-update.component';
import { DifferenceDeleteDialogComponent } from './delete/difference-delete-dialog.component';
import { DifferenceRoutingModule } from './route/difference-routing.module';

@NgModule({
  imports: [SharedModule, DifferenceRoutingModule],
  declarations: [DifferenceComponent, DifferenceDetailComponent, DifferenceUpdateComponent, DifferenceDeleteDialogComponent],
  entryComponents: [DifferenceDeleteDialogComponent],
})
export class DifferenceModule {}
