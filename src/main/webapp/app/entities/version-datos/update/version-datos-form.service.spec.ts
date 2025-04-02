import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../version-datos.test-samples';

import { VersionDatosFormService } from './version-datos-form.service';

describe('VersionDatos Form Service', () => {
  let service: VersionDatosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VersionDatosFormService);
  });

  describe('Service methods', () => {
    describe('createVersionDatosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createVersionDatosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreVersion: expect.any(Object),
            fechaVersion: expect.any(Object),
            citeVersion: expect.any(Object),
            proyecto: expect.any(Object),
          }),
        );
      });

      it('passing IVersionDatos should create a new form with FormGroup', () => {
        const formGroup = service.createVersionDatosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreVersion: expect.any(Object),
            fechaVersion: expect.any(Object),
            citeVersion: expect.any(Object),
            proyecto: expect.any(Object),
          }),
        );
      });
    });

    describe('getVersionDatos', () => {
      it('should return NewVersionDatos for default VersionDatos initial value', () => {
        const formGroup = service.createVersionDatosFormGroup(sampleWithNewData);

        const versionDatos = service.getVersionDatos(formGroup) as any;

        expect(versionDatos).toMatchObject(sampleWithNewData);
      });

      it('should return NewVersionDatos for empty VersionDatos initial value', () => {
        const formGroup = service.createVersionDatosFormGroup();

        const versionDatos = service.getVersionDatos(formGroup) as any;

        expect(versionDatos).toMatchObject({});
      });

      it('should return IVersionDatos', () => {
        const formGroup = service.createVersionDatosFormGroup(sampleWithRequiredData);

        const versionDatos = service.getVersionDatos(formGroup) as any;

        expect(versionDatos).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IVersionDatos should not enable id FormControl', () => {
        const formGroup = service.createVersionDatosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewVersionDatos should disable id FormControl', () => {
        const formGroup = service.createVersionDatosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
