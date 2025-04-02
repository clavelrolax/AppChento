import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IDatosImagen, NewDatosImagen } from '../datos-imagen.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDatosImagen for edit and NewDatosImagenFormGroupInput for create.
 */
type DatosImagenFormGroupInput = IDatosImagen | PartialWithRequiredKeyOf<NewDatosImagen>;

type DatosImagenFormDefaults = Pick<NewDatosImagen, 'id'>;

type DatosImagenFormGroupContent = {
  id: FormControl<IDatosImagen['id'] | NewDatosImagen['id']>;
  imagen1: FormControl<IDatosImagen['imagen1']>;
  imagen1ContentType: FormControl<IDatosImagen['imagen1ContentType']>;
  imagen2: FormControl<IDatosImagen['imagen2']>;
  imagen2ContentType: FormControl<IDatosImagen['imagen2ContentType']>;
  imagen3: FormControl<IDatosImagen['imagen3']>;
  imagen3ContentType: FormControl<IDatosImagen['imagen3ContentType']>;
  imagen4: FormControl<IDatosImagen['imagen4']>;
  imagen4ContentType: FormControl<IDatosImagen['imagen4ContentType']>;
  imagen5: FormControl<IDatosImagen['imagen5']>;
  imagen5ContentType: FormControl<IDatosImagen['imagen5ContentType']>;
  imagen6: FormControl<IDatosImagen['imagen6']>;
  imagen6ContentType: FormControl<IDatosImagen['imagen6ContentType']>;
  imagen7: FormControl<IDatosImagen['imagen7']>;
  imagen7ContentType: FormControl<IDatosImagen['imagen7ContentType']>;
  imagen8: FormControl<IDatosImagen['imagen8']>;
  imagen8ContentType: FormControl<IDatosImagen['imagen8ContentType']>;
  imagen9: FormControl<IDatosImagen['imagen9']>;
  imagen9ContentType: FormControl<IDatosImagen['imagen9ContentType']>;
  imagen10: FormControl<IDatosImagen['imagen10']>;
  imagen10ContentType: FormControl<IDatosImagen['imagen10ContentType']>;
  imagen11: FormControl<IDatosImagen['imagen11']>;
  imagen11ContentType: FormControl<IDatosImagen['imagen11ContentType']>;
  imagen12: FormControl<IDatosImagen['imagen12']>;
  imagen12ContentType: FormControl<IDatosImagen['imagen12ContentType']>;
  versionDatos: FormControl<IDatosImagen['versionDatos']>;
};

export type DatosImagenFormGroup = FormGroup<DatosImagenFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DatosImagenFormService {
  createDatosImagenFormGroup(datosImagen: DatosImagenFormGroupInput = { id: null }): DatosImagenFormGroup {
    const datosImagenRawValue = {
      ...this.getFormDefaults(),
      ...datosImagen,
    };
    return new FormGroup<DatosImagenFormGroupContent>({
      id: new FormControl(
        { value: datosImagenRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      imagen1: new FormControl(datosImagenRawValue.imagen1),
      imagen1ContentType: new FormControl(datosImagenRawValue.imagen1ContentType),
      imagen2: new FormControl(datosImagenRawValue.imagen2),
      imagen2ContentType: new FormControl(datosImagenRawValue.imagen2ContentType),
      imagen3: new FormControl(datosImagenRawValue.imagen3),
      imagen3ContentType: new FormControl(datosImagenRawValue.imagen3ContentType),
      imagen4: new FormControl(datosImagenRawValue.imagen4),
      imagen4ContentType: new FormControl(datosImagenRawValue.imagen4ContentType),
      imagen5: new FormControl(datosImagenRawValue.imagen5),
      imagen5ContentType: new FormControl(datosImagenRawValue.imagen5ContentType),
      imagen6: new FormControl(datosImagenRawValue.imagen6),
      imagen6ContentType: new FormControl(datosImagenRawValue.imagen6ContentType),
      imagen7: new FormControl(datosImagenRawValue.imagen7),
      imagen7ContentType: new FormControl(datosImagenRawValue.imagen7ContentType),
      imagen8: new FormControl(datosImagenRawValue.imagen8),
      imagen8ContentType: new FormControl(datosImagenRawValue.imagen8ContentType),
      imagen9: new FormControl(datosImagenRawValue.imagen9),
      imagen9ContentType: new FormControl(datosImagenRawValue.imagen9ContentType),
      imagen10: new FormControl(datosImagenRawValue.imagen10),
      imagen10ContentType: new FormControl(datosImagenRawValue.imagen10ContentType),
      imagen11: new FormControl(datosImagenRawValue.imagen11),
      imagen11ContentType: new FormControl(datosImagenRawValue.imagen11ContentType),
      imagen12: new FormControl(datosImagenRawValue.imagen12),
      imagen12ContentType: new FormControl(datosImagenRawValue.imagen12ContentType),
      versionDatos: new FormControl(datosImagenRawValue.versionDatos),
    });
  }

  getDatosImagen(form: DatosImagenFormGroup): IDatosImagen | NewDatosImagen {
    return form.getRawValue() as IDatosImagen | NewDatosImagen;
  }

  resetForm(form: DatosImagenFormGroup, datosImagen: DatosImagenFormGroupInput): void {
    const datosImagenRawValue = { ...this.getFormDefaults(), ...datosImagen };
    form.reset(
      {
        ...datosImagenRawValue,
        id: { value: datosImagenRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DatosImagenFormDefaults {
    return {
      id: null,
    };
  }
}
