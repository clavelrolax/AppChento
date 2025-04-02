import { Component, ElementRef, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IVersionDatos } from 'app/entities/version-datos/version-datos.model';
import { VersionDatosService } from 'app/entities/version-datos/service/version-datos.service';
import { DatosImagenService } from '../service/datos-imagen.service';
import { IDatosImagen } from '../datos-imagen.model';
import { DatosImagenFormGroup, DatosImagenFormService } from './datos-imagen-form.service';

@Component({
  selector: 'jhi-datos-imagen-update',
  templateUrl: './datos-imagen-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DatosImagenUpdateComponent implements OnInit {
  isSaving = false;
  datosImagen: IDatosImagen | null = null;

  versionDatosCollection: IVersionDatos[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected datosImagenService = inject(DatosImagenService);
  protected datosImagenFormService = inject(DatosImagenFormService);
  protected versionDatosService = inject(VersionDatosService);
  protected elementRef = inject(ElementRef);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DatosImagenFormGroup = this.datosImagenFormService.createDatosImagenFormGroup();

  compareVersionDatos = (o1: IVersionDatos | null, o2: IVersionDatos | null): boolean =>
    this.versionDatosService.compareVersionDatos(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ datosImagen }) => {
      this.datosImagen = datosImagen;
      if (datosImagen) {
        this.updateForm(datosImagen);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('appylbApp.error', { ...err, key: `error.file.${err.key}` })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector(`#${idInput}`)) {
      this.elementRef.nativeElement.querySelector(`#${idInput}`).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const datosImagen = this.datosImagenFormService.getDatosImagen(this.editForm);
    if (datosImagen.id !== null) {
      this.subscribeToSaveResponse(this.datosImagenService.update(datosImagen));
    } else {
      this.subscribeToSaveResponse(this.datosImagenService.create(datosImagen));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDatosImagen>>): void {
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

  protected updateForm(datosImagen: IDatosImagen): void {
    this.datosImagen = datosImagen;
    this.datosImagenFormService.resetForm(this.editForm, datosImagen);

    this.versionDatosCollection = this.versionDatosService.addVersionDatosToCollectionIfMissing<IVersionDatos>(
      this.versionDatosCollection,
      datosImagen.versionDatos,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.versionDatosService
      .query({ 'datosImagenId.specified': 'false' })
      .pipe(map((res: HttpResponse<IVersionDatos[]>) => res.body ?? []))
      .pipe(
        map((versionDatos: IVersionDatos[]) =>
          this.versionDatosService.addVersionDatosToCollectionIfMissing<IVersionDatos>(versionDatos, this.datosImagen?.versionDatos),
        ),
      )
      .subscribe((versionDatos: IVersionDatos[]) => (this.versionDatosCollection = versionDatos));
  }
}
