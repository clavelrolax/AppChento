import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IProyecto, NewProyecto } from '../proyecto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProyecto for edit and NewProyectoFormGroupInput for create.
 */
type ProyectoFormGroupInput = IProyecto | PartialWithRequiredKeyOf<NewProyecto>;

type ProyectoFormDefaults = Pick<NewProyecto, 'id'>;

type ProyectoFormGroupContent = {
  id: FormControl<IProyecto['id'] | NewProyecto['id']>;
  nombreProyecto: FormControl<IProyecto['nombreProyecto']>;
  objetivo: FormControl<IProyecto['objetivo']>;
  tiempoProyecto: FormControl<IProyecto['tiempoProyecto']>;
  fechaInicio: FormControl<IProyecto['fechaInicio']>;
  fechaFin: FormControl<IProyecto['fechaFin']>;
  operador: FormControl<IProyecto['operador']>;
};

export type ProyectoFormGroup = FormGroup<ProyectoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProyectoFormService {
  createProyectoFormGroup(proyecto: ProyectoFormGroupInput = { id: null }): ProyectoFormGroup {
    const proyectoRawValue = {
      ...this.getFormDefaults(),
      ...proyecto,
    };
    return new FormGroup<ProyectoFormGroupContent>({
      id: new FormControl(
        { value: proyectoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombreProyecto: new FormControl(proyectoRawValue.nombreProyecto, {
        validators: [Validators.required],
      }),
      objetivo: new FormControl(proyectoRawValue.objetivo, {
        validators: [Validators.required],
      }),
      tiempoProyecto: new FormControl(proyectoRawValue.tiempoProyecto, {
        validators: [Validators.required],
      }),
      fechaInicio: new FormControl(proyectoRawValue.fechaInicio),
      fechaFin: new FormControl(proyectoRawValue.fechaFin),
      operador: new FormControl(proyectoRawValue.operador),
    });
  }

  getProyecto(form: ProyectoFormGroup): IProyecto | NewProyecto {
    return form.getRawValue() as IProyecto | NewProyecto;
  }

  resetForm(form: ProyectoFormGroup, proyecto: ProyectoFormGroupInput): void {
    const proyectoRawValue = { ...this.getFormDefaults(), ...proyecto };
    form.reset(
      {
        ...proyectoRawValue,
        id: { value: proyectoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProyectoFormDefaults {
    return {
      id: null,
    };
  }
}
