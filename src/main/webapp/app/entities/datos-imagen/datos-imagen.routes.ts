import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import DatosImagenResolve from './route/datos-imagen-routing-resolve.service';

const datosImagenRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/datos-imagen.component').then(m => m.DatosImagenComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/datos-imagen-detail.component').then(m => m.DatosImagenDetailComponent),
    resolve: {
      datosImagen: DatosImagenResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/datos-imagen-update.component').then(m => m.DatosImagenUpdateComponent),
    resolve: {
      datosImagen: DatosImagenResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/datos-imagen-update.component').then(m => m.DatosImagenUpdateComponent),
    resolve: {
      datosImagen: DatosImagenResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default datosImagenRoute;
