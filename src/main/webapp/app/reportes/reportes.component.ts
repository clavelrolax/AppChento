import { CommonModule } from '@angular/common';
import { Component, inject, NgZone, OnInit, signal, WritableSignal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DEFAULT_SORT_DATA, SORT } from 'app/config/navigation.constants';
import { IDatosImagen } from 'app/entities/datos-imagen/datos-imagen.model';
import { DatosImagenService } from 'app/entities/datos-imagen/service/datos-imagen.service';
import { IDatos } from 'app/entities/datos/datos.model';
import { DatosService, EntityArrayResponseType } from 'app/entities/datos/service/datos.service';
import { IOperador } from 'app/entities/operador/operador.model';
import { OperadorService } from 'app/entities/operador/service/operador.service';
import { IProyecto } from 'app/entities/proyecto/proyecto.model';
import { ProyectoService } from 'app/entities/proyecto/service/proyecto.service';
import { VersionDatosService } from 'app/entities/version-datos/service/version-datos.service';
import { IVersionDatos } from 'app/entities/version-datos/version-datos.model';
import { FormatMediumDatePipe } from 'app/shared/date';
import SharedModule from 'app/shared/shared.module';
import { SortByDirective, SortDirective, SortService, sortStateSignal } from 'app/shared/sort';
import { Observable, tap } from 'rxjs';

@Component({
  selector: 'jhi-reportes',
  imports: [RouterModule, FormsModule, SharedModule],
  templateUrl: './reportes.component.html',
  styleUrl: './reportes.component.scss',
})
export default class ReportesComponent implements OnInit {
  isLoading = false;

  public readonly router = inject(Router);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  sortState = sortStateSignal({});

  proyectos = signal<IProyecto[]>([]);
  operadores = signal<IOperador[]>([]);
  versionDatos = signal<IVersionDatos[]>([]);

  //protected datos: WritableSignal<IVersionDatos | null> = signal(null);

  selectedProyectoId: number | undefined;
  selectedOperadorId: number | undefined;
  selectedVersionId: number | undefined;

  protected proyectoService = inject(ProyectoService);
  protected operadorService = inject(OperadorService);
  protected versionDatosService = inject(VersionDatosService);

  protected datosService = inject(DatosService);
  protected datosImagenService = inject(DatosImagenService);

  protected datos: WritableSignal<IDatos | null> = signal(null);
  protected datosImagen: WritableSignal<IDatosImagen | null> = signal(null);

  protected operador: WritableSignal<IOperador | null> = signal(null);
  protected proyecto: WritableSignal<IProyecto | null> = signal(null);
  protected veersion: WritableSignal<IVersionDatos | null> = signal(null);

  ngOnInit(): void {
    // Initialization logic here
    this.operadorService.query().subscribe(res => this.operadores.set(res.body ?? []));
    //  this.proyectoService.query().subscribe(res => this.proyectos.set(res.body ?? []));
  }

  loadProyectos(): void {
    this.queryBackendProyectos().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccessProyect(res);
      },
    });
  }

  loadVersiones(): void {
    this.queryBackendVersionDatos().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccessVersionDatos(res);
      },
      error(err) {
        console.error('Error loading version data:', err);
      },
    });
  }

  loadData(): void {
    this.queryBackendLlenarDatos().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccessDatos(res);
      },
    });

    this.queryBackendLlenarDatosImagen().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccessDatosImagen(res);
      },
    });
  }

  protected queryBackendProyectos(): Observable<EntityArrayResponseType> {
    this.isLoading = true;

    console.log('selectedOperadorId', this.selectedOperadorId);

    if (this.selectedOperadorId != null) {
      const todosLosOperadores = this.operadores(); // Proyectos previamente cargados
      this.operador.set(todosLosOperadores.filter(p => p.id === this.selectedOperadorId)[0]);

      console.log('operador', this.operador());
    }

    // Si todavía no se ha seleccionado un proyecto, no cargamos VersionDatos aún
    if (this.selectedOperadorId == null) {
      this.proyectos.set([]);
      this.isLoading = false;
      return new Observable<EntityArrayResponseType>(observer => {
        observer.complete();
      });
    }

    // Si hay un proyecto seleccionado, sí hacemos la búsqueda de VersionDatos
    const queryObject: any = {
      'operadorId.equals': this.selectedOperadorId,
      eagerload: true,
      sort: this.sortService.buildSortParam(this.sortState()),
    };

    console.log('queryObject', queryObject);

    return this.proyectoService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected queryBackendVersionDatos(): Observable<EntityArrayResponseType> {
    this.isLoading = true;

    console.log('selectedProyectoId', this.selectedProyectoId);

    if (this.selectedProyectoId != null) {
      const todosLosProyectos = this.proyectos(); // Proyectos previamente cargados
      this.proyecto.set(todosLosProyectos.filter(p => p.id === this.selectedProyectoId)[0]);
      console.log('proyecto', this.proyecto());
    }
    // Si todavía no se ha seleccionado un proyecto, no cargamos VersionDatos aún
    if (this.selectedProyectoId == null) {
      this.isLoading = false;
      return new Observable<EntityArrayResponseType>(observer => {
        observer.complete();
      });
    }

    // Si hay un proyecto seleccionado, sí hacemos la búsqueda de VersionDatos
    const queryObject: any = {
      'proyectoId.equals': this.selectedProyectoId,
      eagerload: true,
      sort: this.sortService.buildSortParam(this.sortState()),
    };

    console.log('queryObject', queryObject);

    return this.versionDatosService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected queryBackendLlenarDatos(): Observable<EntityArrayResponseType> {
    this.isLoading = true;

    console.log('selectedVersionId', this.selectedVersionId);

    if (this.selectedVersionId != null) {
      const todosLosProyectos = this.versionDatos(); // Proyectos previamente cargados
      this.veersion.set(todosLosProyectos.filter(p => p.id === this.selectedVersionId)[0]);
      console.log('veersion', this.veersion());
    }

    if (this.selectedVersionId == null) {
      this.isLoading = false;
      return new Observable<EntityArrayResponseType>(observer => {
        observer.complete();
      });
    }

    console.log('DATOS: ', this.datos());

    // Si hay un proyecto seleccionado, sí hacemos la búsqueda de VersionDatos
    const queryObject: any = {
      'versionId.equals': this.selectedVersionId,
      eagerload: true,
      sort: this.sortService.buildSortParam(this.sortState()),
    };

    return this.datosService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected onResponseSuccessDatos(response: EntityArrayResponseType): void {
    console.log('onResponseSuccessDatos***** ', response.body);
    const dataFromBody = this.fillComponentAttributesFromResponseBodyDatos(response.body);
    this.datos.set(this.refineDataDatos(dataFromBody)[0]);
  }

  protected refineDataDatos(data: IDatos[]): IDatos[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBodyDatos(data: IDatos[] | null): IDatos[] {
    return data ?? [];
  }

  protected queryBackendLlenarDatosImagen(): Observable<EntityArrayResponseType> {
    this.isLoading = true;

    console.log('selectedVersionId', this.selectedVersionId);

    if (this.selectedVersionId == null) {
      this.isLoading = false;
      return new Observable<EntityArrayResponseType>(observer => {
        observer.complete();
      });
    }

    console.log('DATOS: ', this.datosImagen.toString);

    // Si hay un proyecto seleccionado, sí hacemos la búsqueda de VersionDatos
    const queryObject: any = {
      'versionId.equals': this.selectedVersionId,
      eagerload: true,
      sort: this.sortService.buildSortParam(this.sortState()),
    };

    return this.datosImagenService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected onResponseSuccessDatosImagen(response: EntityArrayResponseType): void {
    console.log('onResponseSuccessDatosImage***** ', response.body);
    const dataFromBody = this.fillComponentAttributesFromResponseBodyDatosImagen(response.body);
    this.datosImagen.set(this.refineDataDatosImagen(dataFromBody)[0]);
  }

  protected refineDataDatosImagen(data: IDatosImagen[]): IDatosImagen[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBodyDatosImagen(data: IDatosImagen[] | null): IDatosImagen[] {
    return data ?? [];
  }

  protected onResponseSuccessVersionDatos(response: EntityArrayResponseType): void {
    console.log('onResponseSuccessVersionDatos***** ');
    const dataFromBody = this.fillComponentAttributesFromResponseBodyVersionDatos(response.body);
    this.versionDatos.set(this.refineDataVersionDatos(dataFromBody));
  }

  protected fillComponentAttributesFromResponseBodyVersionDatos(data: IVersionDatos[] | null): IVersionDatos[] {
    return data ?? [];
  }

  protected refineDataVersionDatos(data: IVersionDatos[]): IVersionDatos[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected onResponseSuccessProyect(response: EntityArrayResponseType): void {
    console.log('onResponseSuccessProyect', response.body);
    const dataFromBody = this.VersionDatos(response.body);
    this.proyectos.set(this.refineDataProyect(dataFromBody));
    this.versionDatos.set([]);
  }

  protected refineDataProyect(data: IProyecto[]): IProyecto[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected VersionDatos(data: IProyecto[] | null): IProyecto[] {
    return data ?? [];
  }

  protected onResponseSuccessOperador(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBodyOperador(response.body);
    this.operadores.set(this.refineDataOperador(dataFromBody));
  }

  protected refineDataOperador(data: IOperador[]): IOperador[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBodyOperador(data: IOperador[] | null): IOperador[] {
    return data ?? [];
  }
}
