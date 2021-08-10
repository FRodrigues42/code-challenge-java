import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDifference } from '../difference.model';
import { DifferenceService } from '../service/difference.service';

@Component({
  templateUrl: './difference-delete-dialog.component.html',
})
export class DifferenceDeleteDialogComponent {
  difference?: IDifference;

  constructor(protected differenceService: DifferenceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.differenceService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
