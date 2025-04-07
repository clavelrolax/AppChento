import { Component, NgZone, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { Observable, Subscription, combineLatest, filter, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';
import { FormsModule } from '@angular/forms';
import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { IDatos } from '../datos.model';
import { DatosService, EntityArrayResponseType } from '../service/datos.service';
import { DatosDeleteDialogComponent } from '../delete/datos-delete-dialog.component';
import { IProyecto } from 'app/entities/proyecto/proyecto.model';
import { IOperador } from 'app/entities/operador/operador.model';
import { IVersionDatos } from 'app/entities/version-datos/version-datos.model';
import { ProyectoService } from 'app/entities/proyecto/service/proyecto.service';
import { OperadorService } from 'app/entities/operador/service/operador.service';
import { VersionDatosService } from 'app/entities/version-datos/service/version-datos.service';

@Component({
  selector: 'jhi-datos',
  templateUrl: './datos.component.html',
  imports: [RouterModule, FormsModule, SharedModule, SortDirective, SortByDirective],
})
export class DatosComponent implements OnInit {
  subscription: Subscription | null = null;
  datos = signal<IDatos[]>([]);
  isLoading = false;

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

  sortState = sortStateSignal({});

  public readonly router = inject(Router);
  protected readonly datosService = inject(DatosService);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (item: IDatos): number => this.datosService.getDatosIdentifier(item);

  ngOnInit(): void {
    this.operadorService.query().subscribe(res => this.operadores.set(res.body ?? []));

    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.load()),
      )
      .subscribe();
  }

  delete(datos: IDatos): void {
    const modalRef = this.modalService.open(DatosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.datos = datos;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
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

  load(): void {
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  protected queryBackendProyectos(): Observable<EntityArrayResponseType> {
    this.isLoading = true;

    // console.log('selectedOperadorId', this.selectedOperadorId);

    if (this.selectedOperadorId != null) {
      const todosLosOperadores = this.operadores(); // Proyectos previamente cargados
      //this.operador.set(todosLosOperadores.filter(p => p.id === this.selectedOperadorId)[0]);

      //console.log('operador', this.operador());
    }

    // Si todavía no se ha seleccionado un proyecto, no cargamos VersionDatos aún
    if (this.selectedOperadorId == undefined || this.selectedOperadorId == null) {
      this.proyectos.set([]);
      this.versionDatos.set([]);
      this.isLoading = false;

      this.selectedProyectoId = undefined;
      this.selectedVersionId = undefined;

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

    //console.log('selectedProyectoId', this.selectedProyectoId);

    if (this.selectedProyectoId != null) {
      const todosLosProyectos = this.proyectos(); // Proyectos previamente cargados
      //this.proyecto.set(todosLosProyectos.filter(p => p.id === this.selectedProyectoId)[0]);
      //console.log('proyecto', this.proyecto());
    }
    // Si todavía no se ha seleccionado un proyecto, no cargamos VersionDatos aún
    if (this.selectedProyectoId == undefined || this.selectedProyectoId == null) {
      this.isLoading = false;

      this.versionDatos.set([]);

      this.selectedVersionId = undefined;

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

  protected onResponseSuccessProyect(response: EntityArrayResponseType): void {
    console.log('onResponseSuccessProyect', response.body);
    const dataFromBody = this.ProyectDatos(response.body);
    this.proyectos.set(this.refineDataProyect(dataFromBody));
    this.versionDatos.set([]);
  }

  protected refineDataProyect(data: IProyecto[]): IProyecto[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected ProyectDatos(data: IProyecto[] | null): IProyecto[] {
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

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(event);
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.datos.set(this.refineData(dataFromBody));
  }

  protected refineData(data: IDatos[]): IDatos[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IDatos[] | null): IDatos[] {
    return data ?? [];
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      eagerload: true,
      sort: this.sortService.buildSortParam(this.sortState()),
    };

    if (this.selectedVersionId != null) {
      const queryObject: any = {
        'versionDatosId.equals': this.selectedVersionId,
        eagerload: true,
        sort: this.sortService.buildSortParam(this.sortState()),
      };
      return this.datosService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
    }

    return this.datosService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(sortState: SortState): void {
    const queryParamsObj = {
      sort: this.sortService.buildSortParam(sortState),
    };

    this.ngZone.run(() => {
      this.router.navigate(['./'], {
        relativeTo: this.activatedRoute,
        queryParams: queryParamsObj,
      });
    });
  }
}
