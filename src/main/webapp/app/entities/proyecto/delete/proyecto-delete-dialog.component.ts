import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IProyecto } from '../proyecto.model';
import { ProyectoService } from '../service/proyecto.service';

@Component({
  templateUrl: './proyecto-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ProyectoDeleteDialogComponent {
  proyecto?: IProyecto;

  protected proyectoService = inject(ProyectoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.proyectoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
