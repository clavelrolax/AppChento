import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IOperador } from '../operador.model';
import { OperadorService } from '../service/operador.service';
import { OperadorFormGroup, OperadorFormService } from './operador-form.service';

@Component({
  selector: 'jhi-operador-update',
  templateUrl: './operador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OperadorUpdateComponent implements OnInit {
  isSaving = false;
  operador: IOperador | null = null;

  protected operadorService = inject(OperadorService);
  protected operadorFormService = inject(OperadorFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OperadorFormGroup = this.operadorFormService.createOperadorFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operador }) => {
      this.operador = operador;
      if (operador) {
        this.updateForm(operador);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const operador = this.operadorFormService.getOperador(this.editForm);
    if (operador.id !== null) {
      this.subscribeToSaveResponse(this.operadorService.update(operador));
    } else {
      this.subscribeToSaveResponse(this.operadorService.create(operador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperador>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(operador: IOperador): void {
    this.operador = operador;
    this.operadorFormService.resetForm(this.editForm, operador);
  }
}
