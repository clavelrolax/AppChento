import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../datos-imagen.test-samples';

import { DatosImagenFormService } from './datos-imagen-form.service';

describe('DatosImagen Form Service', () => {
  let service: DatosImagenFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DatosImagenFormService);
  });

  describe('Service methods', () => {
    describe('createDatosImagenFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDatosImagenFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            imagen1: expect.any(Object),
            imagen2: expect.any(Object),
            imagen3: expect.any(Object),
            imagen4: expect.any(Object),
            imagen5: expect.any(Object),
            imagen6: expect.any(Object),
            imagen7: expect.any(Object),
            imagen8: expect.any(Object),
            imagen9: expect.any(Object),
            imagen10: expect.any(Object),
            imagen11: expect.any(Object),
            imagen12: expect.any(Object),
            versionDatos: expect.any(Object),
          }),
        );
      });

      it('passing IDatosImagen should create a new form with FormGroup', () => {
        const formGroup = service.createDatosImagenFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            imagen1: expect.any(Object),
            imagen2: expect.any(Object),
            imagen3: expect.any(Object),
            imagen4: expect.any(Object),
            imagen5: expect.any(Object),
            imagen6: expect.any(Object),
            imagen7: expect.any(Object),
            imagen8: expect.any(Object),
            imagen9: expect.any(Object),
            imagen10: expect.any(Object),
            imagen11: expect.any(Object),
            imagen12: expect.any(Object),
            versionDatos: expect.any(Object),
          }),
        );
      });
    });

    describe('getDatosImagen', () => {
      it('should return NewDatosImagen for default DatosImagen initial value', () => {
        const formGroup = service.createDatosImagenFormGroup(sampleWithNewData);

        const datosImagen = service.getDatosImagen(formGroup) as any;

        expect(datosImagen).toMatchObject(sampleWithNewData);
      });

      it('should return NewDatosImagen for empty DatosImagen initial value', () => {
        const formGroup = service.createDatosImagenFormGroup();

        const datosImagen = service.getDatosImagen(formGroup) as any;

        expect(datosImagen).toMatchObject({});
      });

      it('should return IDatosImagen', () => {
        const formGroup = service.createDatosImagenFormGroup(sampleWithRequiredData);

        const datosImagen = service.getDatosImagen(formGroup) as any;

        expect(datosImagen).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDatosImagen should not enable id FormControl', () => {
        const formGroup = service.createDatosImagenFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDatosImagen should disable id FormControl', () => {
        const formGroup = service.createDatosImagenFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
