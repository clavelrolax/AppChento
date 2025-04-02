import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IProyecto } from 'app/entities/proyecto/proyecto.model';
import { ProyectoService } from 'app/entities/proyecto/service/proyecto.service';
import { IVersionDatos } from '../version-datos.model';
import { VersionDatosService } from '../service/version-datos.service';
import { VersionDatosFormGroup, VersionDatosFormService } from './version-datos-form.service';

@Component({
  selector: 'jhi-version-datos-update',
  templateUrl: './version-datos-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class VersionDatosUpdateComponent implements OnInit {
  isSaving = false;
  versionDatos: IVersionDatos | null = null;

  proyectosSharedCollection: IProyecto[] = [];

  protected versionDatosService = inject(VersionDatosService);
  protected versionDatosFormService = inject(VersionDatosFormService);
  protected proyectoService = inject(ProyectoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: VersionDatosFormGroup = this.versionDatosFormService.createVersionDatosFormGroup();

  compareProyecto = (o1: IProyecto | null, o2: IProyecto | null): boolean => this.proyectoService.compareProyecto(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ versionDatos }) => {
      this.versionDatos = versionDatos;
      if (versionDatos) {
        this.updateForm(versionDatos);
      }

      this.loadRelationshipsOptions();
    });

    this.activatedRoute.queryParamMap.subscribe(params => {
      const proyectoIdParam = params.get('proyectoId');
      if (proyectoIdParam) {
        const proyectoId = +proyectoIdParam;

        this.proyectoService.find(proyectoId).subscribe(response => {
          const proyecto = response.body;
          if (proyecto) {
            this.editForm.patchValue({ proyecto });

            // Asegúrate de que esté en la lista del combo
            this.proyectosSharedCollection = this.proyectoService.addProyectoToCollectionIfMissing(
              this.proyectosSharedCollection,
              proyecto,
            );

            // Desactivar el combo si quieres que no se pueda cambiar
            this.editForm.get('proyecto')?.disable();
          }
        });
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const versionDatos = this.versionDatosFormService.getVersionDatos(this.editForm);
    if (versionDatos.id !== null) {
      this.subscribeToSaveResponse(this.versionDatosService.update(versionDatos));
    } else {
      this.subscribeToSaveResponse(this.versionDatosService.create(versionDatos));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVersionDatos>>): void {
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

  protected updateForm(versionDatos: IVersionDatos): void {
    this.versionDatos = versionDatos;
    this.versionDatosFormService.resetForm(this.editForm, versionDatos);

    this.proyectosSharedCollection = this.proyectoService.addProyectoToCollectionIfMissing<IProyecto>(
      this.proyectosSharedCollection,
      versionDatos.proyecto,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.proyectoService
      .query()
      .pipe(map((res: HttpResponse<IProyecto[]>) => res.body ?? []))
      .pipe(
        map((proyectos: IProyecto[]) =>
          this.proyectoService.addProyectoToCollectionIfMissing<IProyecto>(proyectos, this.versionDatos?.proyecto),
        ),
      )
      .subscribe((proyectos: IProyecto[]) => (this.proyectosSharedCollection = proyectos));
  }
}
