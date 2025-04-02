import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../datos.test-samples';

import { DatosFormService } from './datos-form.service';

describe('Datos Form Service', () => {
  let service: DatosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DatosFormService);
  });

  describe('Service methods', () => {
    describe('createDatosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDatosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            inversionTotal: expect.any(Object),
            ingresosxVentas: expect.any(Object),
            gananciasYLB: expect.any(Object),
            goubernamentTak: expect.any(Object),
            regalias: expect.any(Object),
            iue: expect.any(Object),
            iva: expect.any(Object),
            it: expect.any(Object),
            t1precioVentaprom: expect.any(Object),
            t1costoVariable: expect.any(Object),
            t1costoVartarifa: expect.any(Object),
            t1margenUnitario: expect.any(Object),
            t1costoFijo: expect.any(Object),
            t1costoTotalunitprom: expect.any(Object),
            t1puntoEquilibrio: expect.any(Object),
            t2tasainteres: expect.any(Object),
            t2tasadescuento: expect.any(Object),
            t2vandelProyecto: expect.any(Object),
            t2vanYlb: expect.any(Object),
            t2vp: expect.any(Object),
            t2tirProyecto: expect.any(Object),
            t2tirYlb: expect.any(Object),
            versionDatos: expect.any(Object),
          }),
        );
      });

      it('passing IDatos should create a new form with FormGroup', () => {
        const formGroup = service.createDatosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            inversionTotal: expect.any(Object),
            ingresosxVentas: expect.any(Object),
            gananciasYLB: expect.any(Object),
            goubernamentTak: expect.any(Object),
            regalias: expect.any(Object),
            iue: expect.any(Object),
            iva: expect.any(Object),
            it: expect.any(Object),
            t1precioVentaprom: expect.any(Object),
            t1costoVariable: expect.any(Object),
            t1costoVartarifa: expect.any(Object),
            t1margenUnitario: expect.any(Object),
            t1costoFijo: expect.any(Object),
            t1costoTotalunitprom: expect.any(Object),
            t1puntoEquilibrio: expect.any(Object),
            t2tasainteres: expect.any(Object),
            t2tasadescuento: expect.any(Object),
            t2vandelProyecto: expect.any(Object),
            t2vanYlb: expect.any(Object),
            t2vp: expect.any(Object),
            t2tirProyecto: expect.any(Object),
            t2tirYlb: expect.any(Object),
            versionDatos: expect.any(Object),
          }),
        );
      });
    });

    describe('getDatos', () => {
      it('should return NewDatos for default Datos initial value', () => {
        const formGroup = service.createDatosFormGroup(sampleWithNewData);

        const datos = service.getDatos(formGroup) as any;

        expect(datos).toMatchObject(sampleWithNewData);
      });

      it('should return NewDatos for empty Datos initial value', () => {
        const formGroup = service.createDatosFormGroup();

        const datos = service.getDatos(formGroup) as any;

        expect(datos).toMatchObject({});
      });

      it('should return IDatos', () => {
        const formGroup = service.createDatosFormGroup(sampleWithRequiredData);

        const datos = service.getDatos(formGroup) as any;

        expect(datos).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDatos should not enable id FormControl', () => {
        const formGroup = service.createDatosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDatos should disable id FormControl', () => {
        const formGroup = service.createDatosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
