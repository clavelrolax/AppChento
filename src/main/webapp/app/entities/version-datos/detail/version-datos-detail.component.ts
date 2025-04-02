import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe } from 'app/shared/date';
import { IVersionDatos } from '../version-datos.model';

@Component({
  selector: 'jhi-version-datos-detail',
  templateUrl: './version-datos-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatePipe],
})
export class VersionDatosDetailComponent {
  versionDatos = input<IVersionDatos | null>(null);

  previousState(): void {
    window.history.back();
  }
}
