import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOperador, NewOperador } from '../operador.model';

export type PartialUpdateOperador = Partial<IOperador> & Pick<IOperador, 'id'>;

type RestOf<T extends IOperador | NewOperador> = Omit<T, 'fechaCreacion'> & {
  fechaCreacion?: string | null;
};

export type RestOperador = RestOf<IOperador>;

export type NewRestOperador = RestOf<NewOperador>;

export type PartialUpdateRestOperador = RestOf<PartialUpdateOperador>;

export type EntityResponseType = HttpResponse<IOperador>;
export type EntityArrayResponseType = HttpResponse<IOperador[]>;

@Injectable({ providedIn: 'root' })
export class OperadorService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/operadors');

  create(operador: NewOperador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(operador);
    return this.http
      .post<RestOperador>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(operador: IOperador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(operador);
    return this.http
      .put<RestOperador>(`${this.resourceUrl}/${this.getOperadorIdentifier(operador)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(operador: PartialUpdateOperador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(operador);
    return this.http
      .patch<RestOperador>(`${this.resourceUrl}/${this.getOperadorIdentifier(operador)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestOperador>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestOperador[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOperadorIdentifier(operador: Pick<IOperador, 'id'>): number {
    return operador.id;
  }

  compareOperador(o1: Pick<IOperador, 'id'> | null, o2: Pick<IOperador, 'id'> | null): boolean {
    return o1 && o2 ? this.getOperadorIdentifier(o1) === this.getOperadorIdentifier(o2) : o1 === o2;
  }

  addOperadorToCollectionIfMissing<Type extends Pick<IOperador, 'id'>>(
    operadorCollection: Type[],
    ...operadorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const operadors: Type[] = operadorsToCheck.filter(isPresent);
    if (operadors.length > 0) {
      const operadorCollectionIdentifiers = operadorCollection.map(operadorItem => this.getOperadorIdentifier(operadorItem));
      const operadorsToAdd = operadors.filter(operadorItem => {
        const operadorIdentifier = this.getOperadorIdentifier(operadorItem);
        if (operadorCollectionIdentifiers.includes(operadorIdentifier)) {
          return false;
        }
        operadorCollectionIdentifiers.push(operadorIdentifier);
        return true;
      });
      return [...operadorsToAdd, ...operadorCollection];
    }
    return operadorCollection;
  }

  protected convertDateFromClient<T extends IOperador | NewOperador | PartialUpdateOperador>(operador: T): RestOf<T> {
    return {
      ...operador,
      fechaCreacion: operador.fechaCreacion?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restOperador: RestOperador): IOperador {
    return {
      ...restOperador,
      fechaCreacion: restOperador.fechaCreacion ? dayjs(restOperador.fechaCreacion) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestOperador>): HttpResponse<IOperador> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestOperador[]>): HttpResponse<IOperador[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
