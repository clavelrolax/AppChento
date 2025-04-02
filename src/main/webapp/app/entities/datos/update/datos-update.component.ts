import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IVersionDatos } from 'app/entities/version-datos/version-datos.model';
import { VersionDatosService } from 'app/entities/version-datos/service/version-datos.service';
import { IDatos } from '../datos.model';
import { DatosService } from '../service/datos.service';
import { DatosFormGroup, DatosFormService } from './datos-form.service';

@Component({
  selector: 'jhi-datos-update',
  templateUrl: './datos-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DatosUpdateComponent implements OnInit {
  isSaving = false;
  datos: IDatos | null = null;

  versionDatosCollection: IVersionDatos[] = [];

  protected datosService = inject(DatosService);
  protected datosFormService = inject(DatosFormService);
  protected versionDatosService = inject(VersionDatosService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DatosFormGroup = this.datosFormService.createDatosFormGroup();

  compareVersionDatos = (o1: IVersionDatos | null, o2: IVersionDatos | null): boolean =>
    this.versionDatosService.compareVersionDatos(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ datos }) => {
      this.datos = datos;
      if (datos) {
        this.updateForm(datos);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const datos = this.datosFormService.getDatos(this.editForm);
    if (datos.id !== null) {
      this.subscribeToSaveResponse(this.datosService.update(datos));
    } else {
      this.subscribeToSaveResponse(this.datosService.create(datos));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDatos>>): void {
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

  protected updateForm(datos: IDatos): void {
    this.datos = datos;
    this.datosFormService.resetForm(this.editForm, datos);

    this.versionDatosCollection = this.versionDatosService.addVersionDatosToCollectionIfMissing<IVersionDatos>(
      this.versionDatosCollection,
      datos.versionDatos,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.versionDatosService
      .query({ 'datosId.specified': 'false' })
      .pipe(map((res: HttpResponse<IVersionDatos[]>) => res.body ?? []))
      .pipe(
        map((versionDatos: IVersionDatos[]) =>
          this.versionDatosService.addVersionDatosToCollectionIfMissing<IVersionDatos>(versionDatos, this.datos?.versionDatos),
        ),
      )
      .subscribe((versionDatos: IVersionDatos[]) => (this.versionDatosCollection = versionDatos));
  }
}
