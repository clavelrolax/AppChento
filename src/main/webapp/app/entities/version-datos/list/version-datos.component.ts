import { Component, NgZone, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { Observable, Subscription, combineLatest, filter, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';
import { FormatMediumDatePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';
import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { IVersionDatos } from '../version-datos.model';
import { EntityArrayResponseType, VersionDatosService } from '../service/version-datos.service';
import { VersionDatosDeleteDialogComponent } from '../delete/version-datos-delete-dialog.component';

import { IProyecto } from '../../proyecto/proyecto.model';
import { ProyectoService } from '../../proyecto/service/proyecto.service';
import { IOperador } from '../../operador/operador.model';
import { OperadorService } from '../../operador/service/operador.service';

@Component({
  selector: 'jhi-version-datos',
  templateUrl: './version-datos.component.html',
  imports: [RouterModule, FormsModule, SharedModule, SortDirective, SortByDirective, FormatMediumDatePipe],
})
export class VersionDatosComponent implements OnInit {
  subscription: Subscription | null = null;
  versionDatos = signal<IVersionDatos[]>([]);

  proyectos = signal<IProyecto[]>([]);
  operadores = signal<IOperador[]>([]);

  selectedProyectoId: number | undefined;
  selectedOperadorId: number | undefined;

  isLoading = false;

  sortState = sortStateSignal({});

  public readonly router = inject(Router);
  protected readonly versionDatosService = inject(VersionDatosService);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  protected proyectoService = inject(ProyectoService);
  protected operadorService = inject(OperadorService);

  trackId = (item: IVersionDatos): number => this.versionDatosService.getVersionDatosIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.load()),
      )
      .subscribe();

    this.proyectoService.query().subscribe(res => this.proyectos.set(res.body ?? []));

    this.operadorService.query().subscribe(res => this.operadores.set(res.body ?? []));
  }

  delete(versionDatos: IVersionDatos): void {
    const modalRef = this.modalService.open(VersionDatosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.versionDatos = versionDatos;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  load(): void {
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  loadProyectos(): void {
    this.queryBackendProyectos().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccessProyect(res);
      },
    });
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(event);
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.versionDatos.set(this.refineData(dataFromBody));
  }

  protected refineData(data: IVersionDatos[]): IVersionDatos[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IVersionDatos[] | null): IVersionDatos[] {
    return data ?? [];
  }

  protected onResponseSuccessProyect(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBodyProyect(response.body);
    this.proyectos.set(this.refineDataProyect(dataFromBody));
  }

  protected refineDataProyect(data: IProyecto[]): IVersionDatos[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBodyProyect(data: IProyecto[] | null): IProyecto[] {
    return data ?? [];
  }

  protected queryBackendProyectos(): Observable<EntityArrayResponseType> {
    this.isLoading = true;

    // Si todavía no se ha seleccionado un proyecto, no cargamos VersionDatos aún
    if (this.selectedOperadorId == null) {
      this.versionDatos.set([]);
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

    return this.proyectoService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      eagerload: true,
      sort: this.sortService.buildSortParam(this.sortState()),
    };

    if (this.selectedProyectoId != null) {
      queryObject['proyectoId.equals'] = this.selectedProyectoId;
    }

    if (this.selectedOperadorId != null) {
      const proyectosDelOperador = this.proyectos().filter(p => p.operador?.id === this.selectedOperadorId);
      const ids = proyectosDelOperador.map(p => p.id).join(',');
      queryObject['proyectoId.in'] = ids || '0';
    }

    //console.log(this.selectedProyectoId);

    //console.log(this.selectedOperadorId);

    //console.log('queryObject', queryObject);

    return this.versionDatosService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
