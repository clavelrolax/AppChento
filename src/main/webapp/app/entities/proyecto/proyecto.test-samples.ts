import dayjs from 'dayjs/esm';

import { IProyecto, NewProyecto } from './proyecto.model';

export const sampleWithRequiredData: IProyecto = {
  id: 19316,
  nombreProyecto: 'down',
  objetivo: 'worth since mobilise',
  tiempoProyecto: 11128,
};

export const sampleWithPartialData: IProyecto = {
  id: 18131,
  nombreProyecto: 'fondly inside',
  objetivo: 'serene failing sightseeing',
  tiempoProyecto: 810,
  fechaFin: dayjs('2025-03-28'),
};

export const sampleWithFullData: IProyecto = {
  id: 17840,
  nombreProyecto: 'pointed daintily',
  objetivo: 'untidy profitable',
  tiempoProyecto: 13259,
  fechaInicio: dayjs('2025-03-28'),
  fechaFin: dayjs('2025-03-28'),
};

export const sampleWithNewData: NewProyecto = {
  nombreProyecto: 'which unconscious thorny',
  objetivo: 'next pertinent',
  tiempoProyecto: 5637,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
