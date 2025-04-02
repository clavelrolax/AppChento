import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import OperadorResolve from './route/operador-routing-resolve.service';

const operadorRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/operador.component').then(m => m.OperadorComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/operador-detail.component').then(m => m.OperadorDetailComponent),
    resolve: {
      operador: OperadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/operador-update.component').then(m => m.OperadorUpdateComponent),
    resolve: {
      operador: OperadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/operador-update.component').then(m => m.OperadorUpdateComponent),
    resolve: {
      operador: OperadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default operadorRoute;
