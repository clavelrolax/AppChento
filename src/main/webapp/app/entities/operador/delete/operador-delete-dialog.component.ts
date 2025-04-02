import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IOperador } from '../operador.model';
import { OperadorService } from '../service/operador.service';

@Component({
  templateUrl: './operador-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class OperadorDeleteDialogComponent {
  operador?: IOperador;

  protected operadorService = inject(OperadorService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.operadorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
