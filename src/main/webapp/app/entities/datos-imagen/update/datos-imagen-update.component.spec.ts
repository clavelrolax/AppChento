import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IVersionDatos } from 'app/entities/version-datos/version-datos.model';
import { VersionDatosService } from 'app/entities/version-datos/service/version-datos.service';
import { DatosImagenService } from '../service/datos-imagen.service';
import { IDatosImagen } from '../datos-imagen.model';
import { DatosImagenFormService } from './datos-imagen-form.service';

import { DatosImagenUpdateComponent } from './datos-imagen-update.component';

describe('DatosImagen Management Update Component', () => {
  let comp: DatosImagenUpdateComponent;
  let fixture: ComponentFixture<DatosImagenUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let datosImagenFormService: DatosImagenFormService;
  let datosImagenService: DatosImagenService;
  let versionDatosService: VersionDatosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DatosImagenUpdateComponent],
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
      .overrideTemplate(DatosImagenUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DatosImagenUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    datosImagenFormService = TestBed.inject(DatosImagenFormService);
    datosImagenService = TestBed.inject(DatosImagenService);
    versionDatosService = TestBed.inject(VersionDatosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call versionDatos query and add missing value', () => {
      const datosImagen: IDatosImagen = { id: 15193 };
      const versionDatos: IVersionDatos = { id: 31885 };
      datosImagen.versionDatos = versionDatos;

      const versionDatosCollection: IVersionDatos[] = [{ id: 31885 }];
      jest.spyOn(versionDatosService, 'query').mockReturnValue(of(new HttpResponse({ body: versionDatosCollection })));
      const expectedCollection: IVersionDatos[] = [versionDatos, ...versionDatosCollection];
      jest.spyOn(versionDatosService, 'addVersionDatosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ datosImagen });
      comp.ngOnInit();

      expect(versionDatosService.query).toHaveBeenCalled();
      expect(versionDatosService.addVersionDatosToCollectionIfMissing).toHaveBeenCalledWith(versionDatosCollection, versionDatos);
      expect(comp.versionDatosCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const datosImagen: IDatosImagen = { id: 15193 };
      const versionDatos: IVersionDatos = { id: 31885 };
      datosImagen.versionDatos = versionDatos;

      activatedRoute.data = of({ datosImagen });
      comp.ngOnInit();

      expect(comp.versionDatosCollection).toContainEqual(versionDatos);
      expect(comp.datosImagen).toEqual(datosImagen);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDatosImagen>>();
      const datosImagen = { id: 21783 };
      jest.spyOn(datosImagenFormService, 'getDatosImagen').mockReturnValue(datosImagen);
      jest.spyOn(datosImagenService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ datosImagen });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: datosImagen }));
      saveSubject.complete();

      // THEN
      expect(datosImagenFormService.getDatosImagen).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(datosImagenService.update).toHaveBeenCalledWith(expect.objectContaining(datosImagen));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDatosImagen>>();
      const datosImagen = { id: 21783 };
      jest.spyOn(datosImagenFormService, 'getDatosImagen').mockReturnValue({ id: null });
      jest.spyOn(datosImagenService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ datosImagen: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: datosImagen }));
      saveSubject.complete();

      // THEN
      expect(datosImagenFormService.getDatosImagen).toHaveBeenCalled();
      expect(datosImagenService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDatosImagen>>();
      const datosImagen = { id: 21783 };
      jest.spyOn(datosImagenService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ datosImagen });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(datosImagenService.update).toHaveBeenCalled();
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
