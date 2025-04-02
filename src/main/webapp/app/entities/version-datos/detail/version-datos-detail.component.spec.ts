import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { VersionDatosDetailComponent } from './version-datos-detail.component';

describe('VersionDatos Management Detail Component', () => {
  let comp: VersionDatosDetailComponent;
  let fixture: ComponentFixture<VersionDatosDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VersionDatosDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./version-datos-detail.component').then(m => m.VersionDatosDetailComponent),
              resolve: { versionDatos: () => of({ id: 31885 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(VersionDatosDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VersionDatosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load versionDatos on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', VersionDatosDetailComponent);

      // THEN
      expect(instance.versionDatos()).toEqual(expect.objectContaining({ id: 31885 }));
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
