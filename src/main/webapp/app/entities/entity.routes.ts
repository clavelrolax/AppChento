import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'appylbApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'datos',
    data: { pageTitle: 'appylbApp.datos.home.title' },
    loadChildren: () => import('./datos/datos.routes'),
  },
  {
    path: 'datos-imagen',
    data: { pageTitle: 'appylbApp.datosImagen.home.title' },
    loadChildren: () => import('./datos-imagen/datos-imagen.routes'),
  },
  {
    path: 'operador',
    data: { pageTitle: 'appylbApp.operador.home.title' },
    loadChildren: () => import('./operador/operador.routes'),
  },
  {
    path: 'proyecto',
    data: { pageTitle: 'appylbApp.proyecto.home.title' },
    loadChildren: () => import('./proyecto/proyecto.routes'),
  },
  {
    path: 'version-datos',
    data: { pageTitle: 'appylbApp.versionDatos.home.title' },
    loadChildren: () => import('./version-datos/version-datos.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
