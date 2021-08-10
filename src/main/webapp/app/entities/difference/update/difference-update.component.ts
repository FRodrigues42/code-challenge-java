import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDifference, Difference } from '../difference.model';
import { DifferenceService } from '../service/difference.service';

@Component({
  selector: 'jhi-difference-update',
  templateUrl: './difference-update.component.html',
})
export class DifferenceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    datetime: [],
    value: [null, [Validators.required]],
    number: [null, [Validators.required, Validators.min(1), Validators.max(100)]],
    occurrences: [],
  });

  constructor(protected differenceService: DifferenceService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ difference }) => {
      if (difference.id === undefined) {
        const today = dayjs().startOf('day');
        difference.datetime = today;
      }

      this.updateForm(difference);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const difference = this.createFromForm();
    if (difference.id !== undefined) {
      this.subscribeToSaveResponse(this.differenceService.update(difference));
    } else {
      this.subscribeToSaveResponse(this.differenceService.create(difference));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDifference>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(difference: IDifference): void {
    this.editForm.patchValue({
      id: difference.id,
      datetime: difference.datetime ? difference.datetime.format(DATE_TIME_FORMAT) : null,
      value: difference.value,
      number: difference.number,
      occurrences: difference.occurrences,
    });
  }

  protected createFromForm(): IDifference {
    return {
      ...new Difference(),
      id: this.editForm.get(['id'])!.value,
      datetime: this.editForm.get(['datetime'])!.value ? dayjs(this.editForm.get(['datetime'])!.value, DATE_TIME_FORMAT) : undefined,
      value: this.editForm.get(['value'])!.value,
      number: this.editForm.get(['number'])!.value,
      occurrences: this.editForm.get(['occurrences'])!.value,
    };
  }
}
