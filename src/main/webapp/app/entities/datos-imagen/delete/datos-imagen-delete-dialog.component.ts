import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDatosImagen } from '../datos-imagen.model';
import { DatosImagenService } from '../service/datos-imagen.service';

@Component({
  templateUrl: './datos-imagen-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DatosImagenDeleteDialogComponent {
  datosImagen?: IDatosImagen;

  protected datosImagenService = inject(DatosImagenService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.datosImagenService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
