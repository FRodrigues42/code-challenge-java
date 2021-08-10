import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDifference } from '../difference.model';

@Component({
  selector: 'jhi-difference-detail',
  templateUrl: './difference-detail.component.html',
})
export class DifferenceDetailComponent implements OnInit {
  difference: IDifference | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ difference }) => {
      this.difference = difference;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
