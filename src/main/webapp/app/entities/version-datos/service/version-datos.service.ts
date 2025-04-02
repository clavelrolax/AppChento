import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVersionDatos, NewVersionDatos } from '../version-datos.model';

export type PartialUpdateVersionDatos = Partial<IVersionDatos> & Pick<IVersionDatos, 'id'>;

type RestOf<T extends IVersionDatos | NewVersionDatos> = Omit<T, 'fechaVersion'> & {
  fechaVersion?: string | null;
};

export type RestVersionDatos = RestOf<IVersionDatos>;

export type NewRestVersionDatos = RestOf<NewVersionDatos>;

export type PartialUpdateRestVersionDatos = RestOf<PartialUpdateVersionDatos>;

export type EntityResponseType = HttpResponse<IVersionDatos>;
export type EntityArrayResponseType = HttpResponse<IVersionDatos[]>;

@Injectable({ providedIn: 'root' })
export class VersionDatosService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/version-datos');

  create(versionDatos: NewVersionDatos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(versionDatos);
    return this.http
      .post<RestVersionDatos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(versionDatos: IVersionDatos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(versionDatos);
    return this.http
      .put<RestVersionDatos>(`${this.resourceUrl}/${this.getVersionDatosIdentifier(versionDatos)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(versionDatos: PartialUpdateVersionDatos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(versionDatos);
    return this.http
      .patch<RestVersionDatos>(`${this.resourceUrl}/${this.getVersionDatosIdentifier(versionDatos)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestVersionDatos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestVersionDatos[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVersionDatosIdentifier(versionDatos: Pick<IVersionDatos, 'id'>): number {
    return versionDatos.id;
  }

  compareVersionDatos(o1: Pick<IVersionDatos, 'id'> | null, o2: Pick<IVersionDatos, 'id'> | null): boolean {
    return o1 && o2 ? this.getVersionDatosIdentifier(o1) === this.getVersionDatosIdentifier(o2) : o1 === o2;
  }

  addVersionDatosToCollectionIfMissing<Type extends Pick<IVersionDatos, 'id'>>(
    versionDatosCollection: Type[],
    ...versionDatosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const versionDatos: Type[] = versionDatosToCheck.filter(isPresent);
    if (versionDatos.length > 0) {
      const versionDatosCollectionIdentifiers = versionDatosCollection.map(versionDatosItem =>
        this.getVersionDatosIdentifier(versionDatosItem),
      );
      const versionDatosToAdd = versionDatos.filter(versionDatosItem => {
        const versionDatosIdentifier = this.getVersionDatosIdentifier(versionDatosItem);
        if (versionDatosCollectionIdentifiers.includes(versionDatosIdentifier)) {
          return false;
        }
        versionDatosCollectionIdentifiers.push(versionDatosIdentifier);
        return true;
      });
      return [...versionDatosToAdd, ...versionDatosCollection];
    }
    return versionDatosCollection;
  }

  protected convertDateFromClient<T extends IVersionDatos | NewVersionDatos | PartialUpdateVersionDatos>(versionDatos: T): RestOf<T> {
    return {
      ...versionDatos,
      fechaVersion: versionDatos.fechaVersion?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restVersionDatos: RestVersionDatos): IVersionDatos {
    return {
      ...restVersionDatos,
      fechaVersion: restVersionDatos.fechaVersion ? dayjs(restVersionDatos.fechaVersion) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestVersionDatos>): HttpResponse<IVersionDatos> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestVersionDatos[]>): HttpResponse<IVersionDatos[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
