import dayjs from 'dayjs/esm';

import { IOperador, NewOperador } from './operador.model';

export const sampleWithRequiredData: IOperador = {
  id: 9665,
  nombre: 'hmph gadzooks inside',
  nacionalidad: 'famously oh',
  fechaCreacion: dayjs('2025-03-28'),
};

export const sampleWithPartialData: IOperador = {
  id: 22162,
  nombre: 'angrily mid neatly',
  nacionalidad: 'bleak',
  fechaCreacion: dayjs('2025-03-28'),
};

export const sampleWithFullData: IOperador = {
  id: 14670,
  nombre: 'interior longingly',
  nacionalidad: 'jungle towards quip',
  fechaCreacion: dayjs('2025-03-28'),
};

export const sampleWithNewData: NewOperador = {
  nombre: 'knavishly along vista',
  nacionalidad: 'jubilantly',
  fechaCreacion: dayjs('2025-03-28'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
