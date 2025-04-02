import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDatos, NewDatos } from '../datos.model';

export type PartialUpdateDatos = Partial<IDatos> & Pick<IDatos, 'id'>;

export type EntityResponseType = HttpResponse<IDatos>;
export type EntityArrayResponseType = HttpResponse<IDatos[]>;

@Injectable({ providedIn: 'root' })
export class DatosService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/datos');

  create(datos: NewDatos): Observable<EntityResponseType> {
    return this.http.post<IDatos>(this.resourceUrl, datos, { observe: 'response' });
  }

  update(datos: IDatos): Observable<EntityResponseType> {
    return this.http.put<IDatos>(`${this.resourceUrl}/${this.getDatosIdentifier(datos)}`, datos, { observe: 'response' });
  }

  partialUpdate(datos: PartialUpdateDatos): Observable<EntityResponseType> {
    return this.http.patch<IDatos>(`${this.resourceUrl}/${this.getDatosIdentifier(datos)}`, datos, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDatos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDatos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDatosIdentifier(datos: Pick<IDatos, 'id'>): number {
    return datos.id;
  }

  compareDatos(o1: Pick<IDatos, 'id'> | null, o2: Pick<IDatos, 'id'> | null): boolean {
    return o1 && o2 ? this.getDatosIdentifier(o1) === this.getDatosIdentifier(o2) : o1 === o2;
  }

  addDatosToCollectionIfMissing<Type extends Pick<IDatos, 'id'>>(
    datosCollection: Type[],
    ...datosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const datos: Type[] = datosToCheck.filter(isPresent);
    if (datos.length > 0) {
      const datosCollectionIdentifiers = datosCollection.map(datosItem => this.getDatosIdentifier(datosItem));
      const datosToAdd = datos.filter(datosItem => {
        const datosIdentifier = this.getDatosIdentifier(datosItem);
        if (datosCollectionIdentifiers.includes(datosIdentifier)) {
          return false;
        }
        datosCollectionIdentifiers.push(datosIdentifier);
        return true;
      });
      return [...datosToAdd, ...datosCollection];
    }
    return datosCollection;
  }
}
