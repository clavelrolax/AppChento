import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IVersionDatos } from 'app/entities/version-datos/version-datos.model';
import { VersionDatosService } from 'app/entities/version-datos/service/version-datos.service';
import { DatosService } from '../service/datos.service';
import { IDatos } from '../datos.model';
import { DatosFormService } from './datos-form.service';

import { DatosUpdateComponent } from './datos-update.component';

describe('Datos Management Update Component', () => {
  let comp: DatosUpdateComponent;
  let fixture: ComponentFixture<DatosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let datosFormService: DatosFormService;
  let datosService: DatosService;
  let versionDatosService: VersionDatosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DatosUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DatosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DatosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    datosFormService = TestBed.inject(DatosFormService);
    datosService = TestBed.inject(DatosService);
    versionDatosService = TestBed.inject(VersionDatosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call versionDatos query and add missing value', () => {
      const datos: IDatos = { id: 25344 };
      const versionDatos: IVersionDatos = { id: 31885 };
      datos.versionDatos = versionDatos;

      const versionDatosCollection: IVersionDatos[] = [{ id: 31885 }];
      jest.spyOn(versionDatosService, 'query').mockReturnValue(of(new HttpResponse({ body: versionDatosCollection })));
      const expectedCollection: IVersionDatos[] = [versionDatos, ...versionDatosCollection];
      jest.spyOn(versionDatosService, 'addVersionDatosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ datos });
      comp.ngOnInit();

      expect(versionDatosService.query).toHaveBeenCalled();
      expect(versionDatosService.addVersionDatosToCollectionIfMissing).toHaveBeenCalledWith(versionDatosCollection, versionDatos);
      expect(comp.versionDatosCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const datos: IDatos = { id: 25344 };
      const versionDatos: IVersionDatos = { id: 31885 };
      datos.versionDatos = versionDatos;

      activatedRoute.data = of({ datos });
      comp.ngOnInit();

      expect(comp.versionDatosCollection).toContainEqual(versionDatos);
      expect(comp.datos).toEqual(datos);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDatos>>();
      const datos = { id: 10279 };
      jest.spyOn(datosFormService, 'getDatos').mockReturnValue(datos);
      jest.spyOn(datosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ datos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: datos }));
      saveSubject.complete();

      // THEN
      expect(datosFormService.getDatos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(datosService.update).toHaveBeenCalledWith(expect.objectContaining(datos));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDatos>>();
      const datos = { id: 10279 };
      jest.spyOn(datosFormService, 'getDatos').mockReturnValue({ id: null });
      jest.spyOn(datosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ datos: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: datos }));
      saveSubject.complete();

      // THEN
      expect(datosFormService.getDatos).toHaveBeenCalled();
      expect(datosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDatos>>();
      const datos = { id: 10279 };
      jest.spyOn(datosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ datos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(datosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareVersionDatos', () => {
      it('Should forward to versionDatosService', () => {
        const entity = { id: 31885 };
        const entity2 = { id: 18543 };
        jest.spyOn(versionDatosService, 'compareVersionDatos');
        comp.compareVersionDatos(entity, entity2);
        expect(versionDatosService.compareVersionDatos).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
