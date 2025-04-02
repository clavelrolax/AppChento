import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IOperador, NewOperador } from '../operador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOperador for edit and NewOperadorFormGroupInput for create.
 */
type OperadorFormGroupInput = IOperador | PartialWithRequiredKeyOf<NewOperador>;

type OperadorFormDefaults = Pick<NewOperador, 'id'>;

type OperadorFormGroupContent = {
  id: FormControl<IOperador['id'] | NewOperador['id']>;
  nombre: FormControl<IOperador['nombre']>;
  nacionalidad: FormControl<IOperador['nacionalidad']>;
  fechaCreacion: FormControl<IOperador['fechaCreacion']>;
};

export type OperadorFormGroup = FormGroup<OperadorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OperadorFormService {
  createOperadorFormGroup(operador: OperadorFormGroupInput = { id: null }): OperadorFormGroup {
    const operadorRawValue = {
      ...this.getFormDefaults(),
      ...operador,
    };
    return new FormGroup<OperadorFormGroupContent>({
      id: new FormControl(
        { value: operadorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(operadorRawValue.nombre, {
        validators: [Validators.required],
      }),
      nacionalidad: new FormControl(operadorRawValue.nacionalidad, {
        validators: [Validators.required],
      }),
      fechaCreacion: new FormControl(operadorRawValue.fechaCreacion, {
        validators: [Validators.required],
      }),
    });
  }

  getOperador(form: OperadorFormGroup): IOperador | NewOperador {
    return form.getRawValue() as IOperador | NewOperador;
  }

  resetForm(form: OperadorFormGroup, operador: OperadorFormGroupInput): void {
    const operadorRawValue = { ...this.getFormDefaults(), ...operador };
    form.reset(
      {
        ...operadorRawValue,
        id: { value: operadorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OperadorFormDefaults {
    return {
      id: null,
    };
  }
}
