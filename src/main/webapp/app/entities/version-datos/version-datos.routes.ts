import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import VersionDatosResolve from './route/version-datos-routing-resolve.service';

const versionDatosRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/version-datos.component').then(m => m.VersionDatosComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/version-datos-detail.component').then(m => m.VersionDatosDetailComponent),
    resolve: {
      versionDatos: VersionDatosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/version-datos-update.component').then(m => m.VersionDatosUpdateComponent),
    resolve: {
      versionDatos: VersionDatosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/version-datos-update.component').then(m => m.VersionDatosUpdateComponent),
    resolve: {
      versionDatos: VersionDatosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default versionDatosRoute;
