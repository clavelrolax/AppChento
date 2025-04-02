import { IDatos, NewDatos } from './datos.model';

export const sampleWithRequiredData: IDatos = {
  id: 15353,
};

export const sampleWithPartialData: IDatos = {
  id: 30999,
  gananciasYLB: 'ouch quadruple',
  t1precioVentaprom: 'until babyish vice',
  t1costoVartarifa: 'pish or gah',
  t1margenUnitario: 'showboat worth',
  t1puntoEquilibrio: 'medium clearly',
  t2tasainteres: 'defiantly spiteful oh',
  t2tasadescuento: 'meaty lest',
  t2vp: 'productive blah greedily',
  t2tirProyecto: 'on upon since',
};

export const sampleWithFullData: IDatos = {
  id: 30962,
  inversionTotal: 'circumnavigate elementary greatly',
  ingresosxVentas: 'beneath courageously',
  gananciasYLB: 'kindheartedly gee',
  goubernamentTak: 'marathon',
  regalias: 'zany',
  iue: 'while minus ha',
  iva: 'pink assured so',
  it: 'unexpectedly catalog',
  t1precioVentaprom: 'mid',
  t1costoVariable: 'inquisitively optimistically',
  t1costoVartarifa: 'smoggy though carouse',
  t1margenUnitario: 'duffel pace uh-huh',
  t1costoFijo: 'gee when accomplished',
  t1costoTotalunitprom: 'happy traffic yahoo',
  t1puntoEquilibrio: 'funny lid',
  t2tasainteres: 'like',
  t2tasadescuento: 'woot',
  t2vandelProyecto: 'fluffy acceptable',
  t2vanYlb: 'owlishly',
  t2vp: 'lively',
  t2tirProyecto: 'restaurant mill co-producer',
  t2tirYlb: 'unrealistic mantua once',
};

export const sampleWithNewData: NewDatos = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
