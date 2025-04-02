import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDatos } from '../datos.model';
import { DatosService } from '../service/datos.service';

@Component({
  templateUrl: './datos-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DatosDeleteDialogComponent {
  datos?: IDatos;

  protected datosService = inject(DatosService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.datosService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
