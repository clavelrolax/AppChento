import dayjs from 'dayjs/esm';

import { IVersionDatos, NewVersionDatos } from './version-datos.model';

export const sampleWithRequiredData: IVersionDatos = {
  id: 12309,
  nombreVersion: 'harp what',
  fechaVersion: dayjs('2025-03-28'),
  citeVersion: 'ick pro insignificant',
};

export const sampleWithPartialData: IVersionDatos = {
  id: 16739,
  nombreVersion: 'embossing cruelty bewail',
  fechaVersion: dayjs('2025-03-28'),
  citeVersion: 'despite because reconstitute',
};

export const sampleWithFullData: IVersionDatos = {
  id: 31953,
  nombreVersion: 'spattering',
  fechaVersion: dayjs('2025-03-28'),
  citeVersion: 'indeed',
};

export const sampleWithNewData: NewVersionDatos = {
  nombreVersion: 'jut whether gee',
  fechaVersion: dayjs('2025-03-28'),
  citeVersion: 'times',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
