import dayjs from 'dayjs/esm';
import { IProyecto } from 'app/entities/proyecto/proyecto.model';

export interface IVersionDatos {
  id: number;
  nombreVersion?: string | null;
  fechaVersion?: dayjs.Dayjs | null;
  citeVersion?: string | null;
  proyecto?: Pick<IProyecto, 'id' | 'nombreProyecto'> | null;
}

export type NewVersionDatos = Omit<IVersionDatos, 'id'> & { id: null };
