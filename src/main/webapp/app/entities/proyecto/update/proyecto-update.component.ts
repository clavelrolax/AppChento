import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IOperador } from 'app/entities/operador/operador.model';
import { OperadorService } from 'app/entities/operador/service/operador.service';
import { IProyecto } from '../proyecto.model';
import { ProyectoService } from '../service/proyecto.service';
import { ProyectoFormGroup, ProyectoFormService } from './proyecto-form.service';

@Component({
  selector: 'jhi-proyecto-update',
  templateUrl: './proyecto-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProyectoUpdateComponent implements OnInit {
  isSaving = false;
  proyecto: IProyecto | null = null;

  operadorsSharedCollection: IOperador[] = [];

  protected proyectoService = inject(ProyectoService);
  protected proyectoFormService = inject(ProyectoFormService);
  protected operadorService = inject(OperadorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ProyectoFormGroup = this.proyectoFormService.createProyectoFormGroup();

  compareOperador = (o1: IOperador | null, o2: IOperador | null): boolean => this.operadorService.compareOperador(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proyecto }) => {
      this.proyecto = proyecto;
      if (proyecto) {
        this.updateForm(proyecto);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const proyecto = this.proyectoFormService.getProyecto(this.editForm);
    if (proyecto.id !== null) {
      this.subscribeToSaveResponse(this.proyectoService.update(proyecto));
    } else {
      this.subscribeToSaveResponse(this.proyectoService.create(proyecto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProyecto>>): void {
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

  protected updateForm(proyecto: IProyecto): void {
    this.proyecto = proyecto;
    this.proyectoFormService.resetForm(this.editForm, proyecto);

    this.operadorsSharedCollection = this.operadorService.addOperadorToCollectionIfMissing<IOperador>(
      this.operadorsSharedCollection,
      proyecto.operador,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.operadorService
      .query()
      .pipe(map((res: HttpResponse<IOperador[]>) => res.body ?? []))
      .pipe(
        map((operadors: IOperador[]) =>
          this.operadorService.addOperadorToCollectionIfMissing<IOperador>(operadors, this.proyecto?.operador),
        ),
      )
      .subscribe((operadors: IOperador[]) => (this.operadorsSharedCollection = operadors));
  }
}
