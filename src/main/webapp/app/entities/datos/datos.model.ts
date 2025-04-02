import { IVersionDatos } from 'app/entities/version-datos/version-datos.model';

export interface IDatos {
  id: number;
  inversionTotal?: string | null;
  ingresosxVentas?: string | null;
  gananciasYLB?: string | null;
  goubernamentTak?: string | null;
  regalias?: string | null;
  iue?: string | null;
  iva?: string | null;
  it?: string | null;
  t1precioVentaprom?: string | null;
  t1costoVariable?: string | null;
  t1costoVartarifa?: string | null;
  t1margenUnitario?: string | null;
  t1costoFijo?: string | null;
  t1costoTotalunitprom?: string | null;
  t1puntoEquilibrio?: string | null;
  t2tasainteres?: string | null;
  t2tasadescuento?: string | null;
  t2vandelProyecto?: string | null;
  t2vanYlb?: string | null;
  t2vp?: string | null;
  t2tirProyecto?: string | null;
  t2tirYlb?: string | null;
  versionDatos?: Pick<IVersionDatos, 'id' | 'nombreVersion'> | null;
}

export type NewDatos = Omit<IDatos, 'id'> & { id: null };
