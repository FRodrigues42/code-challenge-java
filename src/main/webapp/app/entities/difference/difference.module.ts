import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DifferenceComponent } from './get/difference.component';

@NgModule({
  imports: [SharedModule],
  declarations: [DifferenceComponent],
})
export class DifferenceModule {}
