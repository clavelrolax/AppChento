import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDatosImagen } from '../datos-imagen.model';
import { DatosImagenService } from '../service/datos-imagen.service';

const datosImagenResolve = (route: ActivatedRouteSnapshot): Observable<null | IDatosImagen> => {
  const id = route.params.id;
  if (id) {
    return inject(DatosImagenService)
      .find(id)
      .pipe(
        mergeMap((datosImagen: HttpResponse<IDatosImagen>) => {
          if (datosImagen.body) {
            return of(datosImagen.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default datosImagenResolve;
