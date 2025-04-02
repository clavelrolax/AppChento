import { Component, inject, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DataUtils } from 'app/core/util/data-util.service';
import { IDatosImagen } from '../datos-imagen.model';

@Component({
  selector: 'jhi-datos-imagen-detail',
  templateUrl: './datos-imagen-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class DatosImagenDetailComponent {
  datosImagen = input<IDatosImagen | null>(null);

  protected dataUtils = inject(DataUtils);

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
