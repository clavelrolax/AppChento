import { IDatosImagen, NewDatosImagen } from './datos-imagen.model';

export const sampleWithRequiredData: IDatosImagen = {
  id: 20917,
};

export const sampleWithPartialData: IDatosImagen = {
  id: 26431,
  imagen1: '../fake-data/blob/hipster.png',
  imagen1ContentType: 'unknown',
  imagen4: '../fake-data/blob/hipster.png',
  imagen4ContentType: 'unknown',
  imagen6: '../fake-data/blob/hipster.png',
  imagen6ContentType: 'unknown',
  imagen7: '../fake-data/blob/hipster.png',
  imagen7ContentType: 'unknown',
  imagen9: '../fake-data/blob/hipster.png',
  imagen9ContentType: 'unknown',
  imagen11: '../fake-data/blob/hipster.png',
  imagen11ContentType: 'unknown',
  imagen12: '../fake-data/blob/hipster.png',
  imagen12ContentType: 'unknown',
};

export const sampleWithFullData: IDatosImagen = {
  id: 847,
  imagen1: '../fake-data/blob/hipster.png',
  imagen1ContentType: 'unknown',
  imagen2: '../fake-data/blob/hipster.png',
  imagen2ContentType: 'unknown',
  imagen3: '../fake-data/blob/hipster.png',
  imagen3ContentType: 'unknown',
  imagen4: '../fake-data/blob/hipster.png',
  imagen4ContentType: 'unknown',
  imagen5: '../fake-data/blob/hipster.png',
  imagen5ContentType: 'unknown',
  imagen6: '../fake-data/blob/hipster.png',
  imagen6ContentType: 'unknown',
  imagen7: '../fake-data/blob/hipster.png',
  imagen7ContentType: 'unknown',
  imagen8: '../fake-data/blob/hipster.png',
  imagen8ContentType: 'unknown',
  imagen9: '../fake-data/blob/hipster.png',
  imagen9ContentType: 'unknown',
  imagen10: '../fake-data/blob/hipster.png',
  imagen10ContentType: 'unknown',
  imagen11: '../fake-data/blob/hipster.png',
  imagen11ContentType: 'unknown',
  imagen12: '../fake-data/blob/hipster.png',
  imagen12ContentType: 'unknown',
};

export const sampleWithNewData: NewDatosImagen = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
