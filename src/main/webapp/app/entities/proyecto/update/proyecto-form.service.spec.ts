import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../proyecto.test-samples';

import { ProyectoFormService } from './proyecto-form.service';

describe('Proyecto Form Service', () => {
  let service: ProyectoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProyectoFormService);
  });

  describe('Service methods', () => {
    describe('createProyectoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProyectoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreProyecto: expect.any(Object),
            objetivo: expect.any(Object),
            tiempoProyecto: expect.any(Object),
            fechaInicio: expect.any(Object),
            fechaFin: expect.any(Object),
            operador: expect.any(Object),
          }),
        );
      });

      it('passing IProyecto should create a new form with FormGroup', () => {
        const formGroup = service.createProyectoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreProyecto: expect.any(Object),
            objetivo: expect.any(Object),
            tiempoProyecto: expect.any(Object),
            fechaInicio: expect.any(Object),
            fechaFin: expect.any(Object),
            operador: expect.any(Object),
          }),
        );
      });
    });

    describe('getProyecto', () => {
      it('should return NewProyecto for default Proyecto initial value', () => {
        const formGroup = service.createProyectoFormGroup(sampleWithNewData);

        const proyecto = service.getProyecto(formGroup) as any;

        expect(proyecto).toMatchObject(sampleWithNewData);
      });

      it('should return NewProyecto for empty Proyecto initial value', () => {
        const formGroup = service.createProyectoFormGroup();

        const proyecto = service.getProyecto(formGroup) as any;

        expect(proyecto).toMatchObject({});
      });

      it('should return IProyecto', () => {
        const formGroup = service.createProyectoFormGroup(sampleWithRequiredData);

        const proyecto = service.getProyecto(formGroup) as any;

        expect(proyecto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProyecto should not enable id FormControl', () => {
        const formGroup = service.createProyectoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProyecto should disable id FormControl', () => {
        const formGroup = service.createProyectoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
