import dayjs from 'dayjs/esm';

export interface IOperador {
  id: number;
  nombre?: string | null;
  nacionalidad?: string | null;
  fechaCreacion?: dayjs.Dayjs | null;
}

export type NewOperador = Omit<IOperador, 'id'> & { id: null };
