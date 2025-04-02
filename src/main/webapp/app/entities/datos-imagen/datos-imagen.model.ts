import { IVersionDatos } from 'app/entities/version-datos/version-datos.model';

export interface IDatosImagen {
  id: number;
  imagen1?: string | null;
  imagen1ContentType?: string | null;
  imagen2?: string | null;
  imagen2ContentType?: string | null;
  imagen3?: string | null;
  imagen3ContentType?: string | null;
  imagen4?: string | null;
  imagen4ContentType?: string | null;
  imagen5?: string | null;
  imagen5ContentType?: string | null;
  imagen6?: string | null;
  imagen6ContentType?: string | null;
  imagen7?: string | null;
  imagen7ContentType?: string | null;
  imagen8?: string | null;
  imagen8ContentType?: string | null;
  imagen9?: string | null;
  imagen9ContentType?: string | null;
  imagen10?: string | null;
  imagen10ContentType?: string | null;
  imagen11?: string | null;
  imagen11ContentType?: string | null;
  imagen12?: string | null;
  imagen12ContentType?: string | null;
  versionDatos?: Pick<IVersionDatos, 'id' | 'nombreVersion'> | null;
}

export type NewDatosImagen = Omit<IDatosImagen, 'id'> & { id: null };
