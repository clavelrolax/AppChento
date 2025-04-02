import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IVersionDatos } from '../version-datos.model';
import { VersionDatosService } from '../service/version-datos.service';

@Component({
  templateUrl: './version-datos-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class VersionDatosDeleteDialogComponent {
  versionDatos?: IVersionDatos;

  protected versionDatosService = inject(VersionDatosService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.versionDatosService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
