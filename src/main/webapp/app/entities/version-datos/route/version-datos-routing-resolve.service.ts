import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVersionDatos } from '../version-datos.model';
import { VersionDatosService } from '../service/version-datos.service';

const versionDatosResolve = (route: ActivatedRouteSnapshot): Observable<null | IVersionDatos> => {
  const id = route.params.id;
  if (id) {
    return inject(VersionDatosService)
      .find(id)
      .pipe(
        mergeMap((versionDatos: HttpResponse<IVersionDatos>) => {
          if (versionDatos.body) {
            return of(versionDatos.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default versionDatosResolve;
