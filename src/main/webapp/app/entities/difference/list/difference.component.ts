import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDifference } from '../difference.model';
import { DifferenceService } from '../service/difference.service';
import { DifferenceDeleteDialogComponent } from '../delete/difference-delete-dialog.component';

@Component({
  selector: 'jhi-difference',
  templateUrl: './difference.component.html',
})
export class DifferenceComponent implements OnInit {
  differences?: IDifference[];
  isLoading = false;

  constructor(protected differenceService: DifferenceService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.differenceService.query().subscribe(
      (res: HttpResponse<IDifference[]>) => {
        this.isLoading = false;
        this.differences = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDifference): number {
    return item.id!;
  }

  delete(difference: IDifference): void {
    const modalRef = this.modalService.open(DifferenceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.difference = difference;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
