import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOperador } from '../operador.model';
import { OperadorService } from '../service/operador.service';

const operadorResolve = (route: ActivatedRouteSnapshot): Observable<null | IOperador> => {
  const id = route.params.id;
  if (id) {
    return inject(OperadorService)
      .find(id)
      .pipe(
        mergeMap((operador: HttpResponse<IOperador>) => {
          if (operador.body) {
            return of(operador.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default operadorResolve;
