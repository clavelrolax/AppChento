import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDatosImagen, NewDatosImagen } from '../datos-imagen.model';

export type PartialUpdateDatosImagen = Partial<IDatosImagen> & Pick<IDatosImagen, 'id'>;

export type EntityResponseType = HttpResponse<IDatosImagen>;
export type EntityArrayResponseType = HttpResponse<IDatosImagen[]>;

@Injectable({ providedIn: 'root' })
export class DatosImagenService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/datos-imagens');

  create(datosImagen: NewDatosImagen): Observable<EntityResponseType> {
    return this.http.post<IDatosImagen>(this.resourceUrl, datosImagen, { observe: 'response' });
  }

  update(datosImagen: IDatosImagen): Observable<EntityResponseType> {
    return this.http.put<IDatosImagen>(`${this.resourceUrl}/${this.getDatosImagenIdentifier(datosImagen)}`, datosImagen, {
      observe: 'response',
    });
  }

  partialUpdate(datosImagen: PartialUpdateDatosImagen): Observable<EntityResponseType> {
    return this.http.patch<IDatosImagen>(`${this.resourceUrl}/${this.getDatosImagenIdentifier(datosImagen)}`, datosImagen, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDatosImagen>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDatosImagen[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDatosImagenIdentifier(datosImagen: Pick<IDatosImagen, 'id'>): number {
    return datosImagen.id;
  }

  compareDatosImagen(o1: Pick<IDatosImagen, 'id'> | null, o2: Pick<IDatosImagen, 'id'> | null): boolean {
    return o1 && o2 ? this.getDatosImagenIdentifier(o1) === this.getDatosImagenIdentifier(o2) : o1 === o2;
  }

  addDatosImagenToCollectionIfMissing<Type extends Pick<IDatosImagen, 'id'>>(
    datosImagenCollection: Type[],
    ...datosImagensToCheck: (Type | null | undefined)[]
  ): Type[] {
    const datosImagens: Type[] = datosImagensToCheck.filter(isPresent);
    if (datosImagens.length > 0) {
      const datosImagenCollectionIdentifiers = datosImagenCollection.map(datosImagenItem => this.getDatosImagenIdentifier(datosImagenItem));
      const datosImagensToAdd = datosImagens.filter(datosImagenItem => {
        const datosImagenIdentifier = this.getDatosImagenIdentifier(datosImagenItem);
        if (datosImagenCollectionIdentifiers.includes(datosImagenIdentifier)) {
          return false;
        }
        datosImagenCollectionIdentifiers.push(datosImagenIdentifier);
        return true;
      });
      return [...datosImagensToAdd, ...datosImagenCollection];
    }
    return datosImagenCollection;
  }
}
