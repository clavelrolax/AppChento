import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../operador.test-samples';

import { OperadorFormService } from './operador-form.service';

describe('Operador Form Service', () => {
  let service: OperadorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OperadorFormService);
  });

  describe('Service methods', () => {
    describe('createOperadorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOperadorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            nacionalidad: expect.any(Object),
            fechaCreacion: expect.any(Object),
          }),
        );
      });

      it('passing IOperador should create a new form with FormGroup', () => {
        const formGroup = service.createOperadorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            nacionalidad: expect.any(Object),
            fechaCreacion: expect.any(Object),
          }),
        );
      });
    });

    describe('getOperador', () => {
      it('should return NewOperador for default Operador initial value', () => {
        const formGroup = service.createOperadorFormGroup(sampleWithNewData);

        const operador = service.getOperador(formGroup) as any;

        expect(operador).toMatchObject(sampleWithNewData);
      });

      it('should return NewOperador for empty Operador initial value', () => {
        const formGroup = service.createOperadorFormGroup();

        const operador = service.getOperador(formGroup) as any;

        expect(operador).toMatchObject({});
      });

      it('should return IOperador', () => {
        const formGroup = service.createOperadorFormGroup(sampleWithRequiredData);

        const operador = service.getOperador(formGroup) as any;

        expect(operador).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOperador should not enable id FormControl', () => {
        const formGroup = service.createOperadorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOperador should disable id FormControl', () => {
        const formGroup = service.createOperadorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
