import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { Difference, IDifference } from '../difference.model';
import { DifferenceService } from '../service/difference.service';

@Component({
  selector: 'jhi-difference',
  templateUrl: './difference.component.html',
})
export class DifferenceComponent implements OnInit {
  differences?: IDifference[];
  isLoading = false;
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    datetime: [],
    value: [null, [Validators.required]],
    number: [null, [Validators.required, Validators.min(1), Validators.max(100)]],
    occurrences: [],
  });

  constructor(protected differenceService: DifferenceService, protected modalService: NgbModal, protected fb: FormBuilder) {}

  get(): void {
    this.isSaving = true;
    const difference = this.createFromForm();

    if (difference.number != null) {
      this.subscribeToSaveResponse(this.differenceService.get(difference.number));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDifference>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      value => this.onSaveSuccess(value.body),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(result: IDifference): void {
    this.differences?.push(result);
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  load(n: number): void {
    this.isLoading = true;

    this.differenceService.get(n).subscribe(
      (res: HttpResponse<IDifference[]>) => {
        this.isLoading = false;
        this.differences = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {}

  trackNumber(index: number, item: IDifference): number {
    return item.number!;
  }

  protected createFromForm(): IDifference {
    return {
      ...new Difference(),
      datetime: this.editForm.get(['datetime'])!.value ? dayjs(this.editForm.get(['datetime'])!.value, DATE_TIME_FORMAT) : undefined,
      value: this.editForm.get(['value'])!.value,
      number: this.editForm.get(['number'])!.value,
      occurrences: this.editForm.get(['occurrences'])!.value,
    };
  }
}
