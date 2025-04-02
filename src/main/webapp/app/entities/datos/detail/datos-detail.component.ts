import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IDatos } from '../datos.model';

@Component({
  selector: 'jhi-datos-detail',
  templateUrl: './datos-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class DatosDetailComponent {
  datos = input<IDatos | null>(null);

  previousState(): void {
    window.history.back();
  }
}
