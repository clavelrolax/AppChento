import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import DatosResolve from './route/datos-routing-resolve.service';

const datosRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/datos.component').then(m => m.DatosComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/datos-detail.component').then(m => m.DatosDetailComponent),
    resolve: {
      datos: DatosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/datos-update.component').then(m => m.DatosUpdateComponent),
    resolve: {
      datos: DatosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/datos-update.component').then(m => m.DatosUpdateComponent),
    resolve: {
      datos: DatosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default datosRoute;
