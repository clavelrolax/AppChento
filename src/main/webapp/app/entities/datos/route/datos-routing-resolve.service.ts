import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDatos } from '../datos.model';
import { DatosService } from '../service/datos.service';

const datosResolve = (route: ActivatedRouteSnapshot): Observable<null | IDatos> => {
  const id = route.params.id;
  if (id) {
    return inject(DatosService)
      .find(id)
      .pipe(
        mergeMap((datos: HttpResponse<IDatos>) => {
          if (datos.body) {
            return of(datos.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default datosResolve;
