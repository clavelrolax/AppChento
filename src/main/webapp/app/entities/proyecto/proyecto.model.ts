import dayjs from 'dayjs/esm';
import { IOperador } from 'app/entities/operador/operador.model';

export interface IProyecto {
  id: number;
  nombreProyecto?: string | null;
  objetivo?: string | null;
  tiempoProyecto?: number | null;
  fechaInicio?: dayjs.Dayjs | null;
  fechaFin?: dayjs.Dayjs | null;
  operador?: Pick<IOperador, 'id' | 'nombre'> | null;
}

export type NewProyecto = Omit<IProyecto, 'id'> & { id: null };
