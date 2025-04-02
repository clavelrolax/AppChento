import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { OperadorDetailComponent } from './operador-detail.component';

describe('Operador Management Detail Component', () => {
  let comp: OperadorDetailComponent;
  let fixture: ComponentFixture<OperadorDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OperadorDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./operador-detail.component').then(m => m.OperadorDetailComponent),
              resolve: { operador: () => of({ id: 6338 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(OperadorDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperadorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load operador on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', OperadorDetailComponent);

      // THEN
      expect(instance.operador()).toEqual(expect.objectContaining({ id: 6338 }));
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
