import { CommonModule } from '@angular/common';
import { Component, inject, NgZone, OnInit, signal, WritableSignal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DEFAULT_SORT_DATA, SORT } from 'app/config/navigation.constants';
import { EntityArrayResponseType } from 'app/entities/datos/service/datos.service';
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

  protected datos: WritableSignal<IVersionDatos | null> = signal(null);

  selectedProyectoId: number | undefined;
  selectedOperadorId: number | undefined;
  selectedVersionId: number | undefined;

  protected proyectoService = inject(ProyectoService);
  protected operadorService = inject(OperadorService);
  protected versionDatosService = inject(VersionDatosService);

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
    this.queryBackendLlenarDatos();
  }

  protected queryBackendProyectos(): Observable<EntityArrayResponseType> {
    this.isLoading = true;

    console.log('selectedOperadorId', this.selectedOperadorId);
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

  protected queryBackendLlenarDatos() {
    this.isLoading = true;

    console.log('selectedVersionId', this.selectedVersionId);

    // Si hay un operador seleccionado, actualiza la lista de proyectos según ese operador
    if (this.selectedVersionId != null) {
      const todosLasVersiones = this.versionDatos(); // Proyectos previamente cargados
      const versionesFiltrados = todosLasVersiones.filter(p => p.id === this.selectedVersionId);

      console.log('versionesFiltrados', versionesFiltrados);

      this.datos.set(versionesFiltrados[0]); // Actualiza el ComboBox de Proyecto
    } else {
      // Si no hay operador, carga todos los proyectos de nuevo
      this.versionDatosService.query().subscribe(res => this.versionDatos.set(res.body ?? []));
    }

    console.log('DATOS: ', this.datos.toString);

    // Si hay un proyecto seleccionado, sí hacemos la búsqueda de VersionDatos
    //const queryObject: any = {
    //  'proyectoId.equals': this.selectedVersionId,
    //  eagerload: true,
    //  sort: this.sortService.buildSortParam(this.sortState()),
    //};

    //return this.versionDatosService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
