import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe } from 'app/shared/date';
import { IOperador } from '../operador.model';

@Component({
  selector: 'jhi-operador-detail',
  templateUrl: './operador-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatePipe],
})
export class OperadorDetailComponent {
  operador = input<IOperador | null>(null);

  previousState(): void {
    window.history.back();
  }
}
