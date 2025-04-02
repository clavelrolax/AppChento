import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DatosDetailComponent } from './datos-detail.component';

describe('Datos Management Detail Component', () => {
  let comp: DatosDetailComponent;
  let fixture: ComponentFixture<DatosDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DatosDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./datos-detail.component').then(m => m.DatosDetailComponent),
              resolve: { datos: () => of({ id: 10279 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DatosDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DatosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load datos on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DatosDetailComponent);

      // THEN
      expect(instance.datos()).toEqual(expect.objectContaining({ id: 10279 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
