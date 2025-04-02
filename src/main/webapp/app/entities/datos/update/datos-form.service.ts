import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IDatos, NewDatos } from '../datos.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDatos for edit and NewDatosFormGroupInput for create.
 */
type DatosFormGroupInput = IDatos | PartialWithRequiredKeyOf<NewDatos>;

type DatosFormDefaults = Pick<NewDatos, 'id'>;

type DatosFormGroupContent = {
  id: FormControl<IDatos['id'] | NewDatos['id']>;
  inversionTotal: FormControl<IDatos['inversionTotal']>;
  ingresosxVentas: FormControl<IDatos['ingresosxVentas']>;
  gananciasYLB: FormControl<IDatos['gananciasYLB']>;
  goubernamentTak: FormControl<IDatos['goubernamentTak']>;
  regalias: FormControl<IDatos['regalias']>;
  iue: FormControl<IDatos['iue']>;
  iva: FormControl<IDatos['iva']>;
  it: FormControl<IDatos['it']>;
  t1precioVentaprom: FormControl<IDatos['t1precioVentaprom']>;
  t1costoVariable: FormControl<IDatos['t1costoVariable']>;
  t1costoVartarifa: FormControl<IDatos['t1costoVartarifa']>;
  t1margenUnitario: FormControl<IDatos['t1margenUnitario']>;
  t1costoFijo: FormControl<IDatos['t1costoFijo']>;
  t1costoTotalunitprom: FormControl<IDatos['t1costoTotalunitprom']>;
  t1puntoEquilibrio: FormControl<IDatos['t1puntoEquilibrio']>;
  t2tasainteres: FormControl<IDatos['t2tasainteres']>;
  t2tasadescuento: FormControl<IDatos['t2tasadescuento']>;
  t2vandelProyecto: FormControl<IDatos['t2vandelProyecto']>;
  t2vanYlb: FormControl<IDatos['t2vanYlb']>;
  t2vp: FormControl<IDatos['t2vp']>;
  t2tirProyecto: FormControl<IDatos['t2tirProyecto']>;
  t2tirYlb: FormControl<IDatos['t2tirYlb']>;
  versionDatos: FormControl<IDatos['versionDatos']>;
};

export type DatosFormGroup = FormGroup<DatosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DatosFormService {
  createDatosFormGroup(datos: DatosFormGroupInput = { id: null }): DatosFormGroup {
    const datosRawValue = {
      ...this.getFormDefaults(),
      ...datos,
    };
    return new FormGroup<DatosFormGroupContent>({
      id: new FormControl(
        { value: datosRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      inversionTotal: new FormControl(datosRawValue.inversionTotal),
      ingresosxVentas: new FormControl(datosRawValue.ingresosxVentas),
      gananciasYLB: new FormControl(datosRawValue.gananciasYLB),
      goubernamentTak: new FormControl(datosRawValue.goubernamentTak),
      regalias: new FormControl(datosRawValue.regalias),
      iue: new FormControl(datosRawValue.iue),
      iva: new FormControl(datosRawValue.iva),
      it: new FormControl(datosRawValue.it),
      t1precioVentaprom: new FormControl(datosRawValue.t1precioVentaprom),
      t1costoVariable: new FormControl(datosRawValue.t1costoVariable),
      t1costoVartarifa: new FormControl(datosRawValue.t1costoVartarifa),
      t1margenUnitario: new FormControl(datosRawValue.t1margenUnitario),
      t1costoFijo: new FormControl(datosRawValue.t1costoFijo),
      t1costoTotalunitprom: new FormControl(datosRawValue.t1costoTotalunitprom),
      t1puntoEquilibrio: new FormControl(datosRawValue.t1puntoEquilibrio),
      t2tasainteres: new FormControl(datosRawValue.t2tasainteres),
      t2tasadescuento: new FormControl(datosRawValue.t2tasadescuento),
      t2vandelProyecto: new FormControl(datosRawValue.t2vandelProyecto),
      t2vanYlb: new FormControl(datosRawValue.t2vanYlb),
      t2vp: new FormControl(datosRawValue.t2vp),
      t2tirProyecto: new FormControl(datosRawValue.t2tirProyecto),
      t2tirYlb: new FormControl(datosRawValue.t2tirYlb),
      versionDatos: new FormControl(datosRawValue.versionDatos),
    });
  }

  getDatos(form: DatosFormGroup): IDatos | NewDatos {
    return form.getRawValue() as IDatos | NewDatos;
  }

  resetForm(form: DatosFormGroup, datos: DatosFormGroupInput): void {
    const datosRawValue = { ...this.getFormDefaults(), ...datos };
    form.reset(
      {
        ...datosRawValue,
        id: { value: datosRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DatosFormDefaults {
    return {
      id: null,
    };
  }
}
