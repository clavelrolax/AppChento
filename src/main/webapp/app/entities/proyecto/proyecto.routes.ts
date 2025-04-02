import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ProyectoResolve from './route/proyecto-routing-resolve.service';

const proyectoRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/proyecto.component').then(m => m.ProyectoComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/proyecto-detail.component').then(m => m.ProyectoDetailComponent),
    resolve: {
      proyecto: ProyectoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/proyecto-update.component').then(m => m.ProyectoUpdateComponent),
    resolve: {
      proyecto: ProyectoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/proyecto-update.component').then(m => m.ProyectoUpdateComponent),
    resolve: {
      proyecto: ProyectoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default proyectoRoute;
