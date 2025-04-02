import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IProyecto } from 'app/entities/proyecto/proyecto.model';
import { ProyectoService } from 'app/entities/proyecto/service/proyecto.service';
import { VersionDatosService } from '../service/version-datos.service';
import { IVersionDatos } from '../version-datos.model';
import { VersionDatosFormService } from './version-datos-form.service';

import { VersionDatosUpdateComponent } from './version-datos-update.component';

describe('VersionDatos Management Update Component', () => {
  let comp: VersionDatosUpdateComponent;
  let fixture: ComponentFixture<VersionDatosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let versionDatosFormService: VersionDatosFormService;
  let versionDatosService: VersionDatosService;
  let proyectoService: ProyectoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [VersionDatosUpdateComponent],
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
      .overrideTemplate(VersionDatosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VersionDatosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    versionDatosFormService = TestBed.inject(VersionDatosFormService);
    versionDatosService = TestBed.inject(VersionDatosService);
    proyectoService = TestBed.inject(ProyectoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Proyecto query and add missing value', () => {
      const versionDatos: IVersionDatos = { id: 18543 };
      const proyecto: IProyecto = { id: 10716 };
      versionDatos.proyecto = proyecto;

      const proyectoCollection: IProyecto[] = [{ id: 10716 }];
      jest.spyOn(proyectoService, 'query').mockReturnValue(of(new HttpResponse({ body: proyectoCollection })));
      const additionalProyectos = [proyecto];
      const expectedCollection: IProyecto[] = [...additionalProyectos, ...proyectoCollection];
      jest.spyOn(proyectoService, 'addProyectoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ versionDatos });
      comp.ngOnInit();

      expect(proyectoService.query).toHaveBeenCalled();
      expect(proyectoService.addProyectoToCollectionIfMissing).toHaveBeenCalledWith(
        proyectoCollection,
        ...additionalProyectos.map(expect.objectContaining),
      );
      expect(comp.proyectosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const versionDatos: IVersionDatos = { id: 18543 };
      const proyecto: IProyecto = { id: 10716 };
      versionDatos.proyecto = proyecto;

      activatedRoute.data = of({ versionDatos });
      comp.ngOnInit();

      expect(comp.proyectosSharedCollection).toContainEqual(proyecto);
      expect(comp.versionDatos).toEqual(versionDatos);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVersionDatos>>();
      const versionDatos = { id: 31885 };
      jest.spyOn(versionDatosFormService, 'getVersionDatos').mockReturnValue(versionDatos);
      jest.spyOn(versionDatosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ versionDatos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: versionDatos }));
      saveSubject.complete();

      // THEN
      expect(versionDatosFormService.getVersionDatos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(versionDatosService.update).toHaveBeenCalledWith(expect.objectContaining(versionDatos));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVersionDatos>>();
      const versionDatos = { id: 31885 };
      jest.spyOn(versionDatosFormService, 'getVersionDatos').mockReturnValue({ id: null });
      jest.spyOn(versionDatosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ versionDatos: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: versionDatos }));
      saveSubject.complete();

      // THEN
      expect(versionDatosFormService.getVersionDatos).toHaveBeenCalled();
      expect(versionDatosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVersionDatos>>();
      const versionDatos = { id: 31885 };
      jest.spyOn(versionDatosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ versionDatos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(versionDatosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProyecto', () => {
      it('Should forward to proyectoService', () => {
        const entity = { id: 10716 };
        const entity2 = { id: 11127 };
        jest.spyOn(proyectoService, 'compareProyecto');
        comp.compareProyecto(entity, entity2);
        expect(proyectoService.compareProyecto).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
