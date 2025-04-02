import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProyecto } from '../proyecto.model';
import { ProyectoService } from '../service/proyecto.service';

const proyectoResolve = (route: ActivatedRouteSnapshot): Observable<null | IProyecto> => {
  const id = route.params.id;
  if (id) {
    return inject(ProyectoService)
      .find(id)
      .pipe(
        mergeMap((proyecto: HttpResponse<IProyecto>) => {
          if (proyecto.body) {
            return of(proyecto.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default proyectoResolve;
