import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe } from 'app/shared/date';
import { IProyecto } from '../proyecto.model';

@Component({
  selector: 'jhi-proyecto-detail',
  templateUrl: './proyecto-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatePipe],
})
export class ProyectoDetailComponent {
  proyecto = input<IProyecto | null>(null);

  previousState(): void {
    window.history.back();
  }
}
