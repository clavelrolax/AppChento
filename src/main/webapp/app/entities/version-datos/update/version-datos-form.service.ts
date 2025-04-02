import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IVersionDatos, NewVersionDatos } from '../version-datos.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVersionDatos for edit and NewVersionDatosFormGroupInput for create.
 */
type VersionDatosFormGroupInput = IVersionDatos | PartialWithRequiredKeyOf<NewVersionDatos>;

type VersionDatosFormDefaults = Pick<NewVersionDatos, 'id'>;

type VersionDatosFormGroupContent = {
  id: FormControl<IVersionDatos['id'] | NewVersionDatos['id']>;
  nombreVersion: FormControl<IVersionDatos['nombreVersion']>;
  fechaVersion: FormControl<IVersionDatos['fechaVersion']>;
  citeVersion: FormControl<IVersionDatos['citeVersion']>;
  proyecto: FormControl<IVersionDatos['proyecto']>;
};

export type VersionDatosFormGroup = FormGroup<VersionDatosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VersionDatosFormService {
  createVersionDatosFormGroup(versionDatos: VersionDatosFormGroupInput = { id: null }): VersionDatosFormGroup {
    const versionDatosRawValue = {
      ...this.getFormDefaults(),
      ...versionDatos,
    };
    return new FormGroup<VersionDatosFormGroupContent>({
      id: new FormControl(
        { value: versionDatosRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombreVersion: new FormControl(versionDatosRawValue.nombreVersion, {
        validators: [Validators.required],
      }),
      fechaVersion: new FormControl(versionDatosRawValue.fechaVersion, {
        validators: [Validators.required],
      }),
      citeVersion: new FormControl(versionDatosRawValue.citeVersion, {
        validators: [Validators.required],
      }),
      proyecto: new FormControl(versionDatosRawValue.proyecto),
    });
  }

  getVersionDatos(form: VersionDatosFormGroup): IVersionDatos | NewVersionDatos {
    return form.getRawValue() as IVersionDatos | NewVersionDatos;
  }

  resetForm(form: VersionDatosFormGroup, versionDatos: VersionDatosFormGroupInput): void {
    const versionDatosRawValue = { ...this.getFormDefaults(), ...versionDatos };
    form.reset(
      {
        ...versionDatosRawValue,
        id: { value: versionDatosRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): VersionDatosFormDefaults {
    return {
      id: null,
    };
  }
}
