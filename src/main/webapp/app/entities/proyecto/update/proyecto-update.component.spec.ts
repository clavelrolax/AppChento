import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IOperador } from 'app/entities/operador/operador.model';
import { OperadorService } from 'app/entities/operador/service/operador.service';
import { ProyectoService } from '../service/proyecto.service';
import { IProyecto } from '../proyecto.model';
import { ProyectoFormService } from './proyecto-form.service';

import { ProyectoUpdateComponent } from './proyecto-update.component';

describe('Proyecto Management Update Component', () => {
  let comp: ProyectoUpdateComponent;
  let fixture: ComponentFixture<ProyectoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let proyectoFormService: ProyectoFormService;
  let proyectoService: ProyectoService;
  let operadorService: OperadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ProyectoUpdateComponent],
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
      .overrideTemplate(ProyectoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProyectoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    proyectoFormService = TestBed.inject(ProyectoFormService);
    proyectoService = TestBed.inject(ProyectoService);
    operadorService = TestBed.inject(OperadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Operador query and add missing value', () => {
      const proyecto: IProyecto = { id: 11127 };
      const operador: IOperador = { id: 6338 };
      proyecto.operador = operador;

      const operadorCollection: IOperador[] = [{ id: 6338 }];
      jest.spyOn(operadorService, 'query').mockReturnValue(of(new HttpResponse({ body: operadorCollection })));
      const additionalOperadors = [operador];
      const expectedCollection: IOperador[] = [...additionalOperadors, ...operadorCollection];
      jest.spyOn(operadorService, 'addOperadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ proyecto });
      comp.ngOnInit();

      expect(operadorService.query).toHaveBeenCalled();
      expect(operadorService.addOperadorToCollectionIfMissing).toHaveBeenCalledWith(
        operadorCollection,
        ...additionalOperadors.map(expect.objectContaining),
      );
      expect(comp.operadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const proyecto: IProyecto = { id: 11127 };
      const operador: IOperador = { id: 6338 };
      proyecto.operador = operador;

      activatedRoute.data = of({ proyecto });
      comp.ngOnInit();

      expect(comp.operadorsSharedCollection).toContainEqual(operador);
      expect(comp.proyecto).toEqual(proyecto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProyecto>>();
      const proyecto = { id: 10716 };
      jest.spyOn(proyectoFormService, 'getProyecto').mockReturnValue(proyecto);
      jest.spyOn(proyectoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ proyecto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: proyecto }));
      saveSubject.complete();

      // THEN
      expect(proyectoFormService.getProyecto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(proyectoService.update).toHaveBeenCalledWith(expect.objectContaining(proyecto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProyecto>>();
      const proyecto = { id: 10716 };
      jest.spyOn(proyectoFormService, 'getProyecto').mockReturnValue({ id: null });
      jest.spyOn(proyectoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ proyecto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: proyecto }));
      saveSubject.complete();

      // THEN
      expect(proyectoFormService.getProyecto).toHaveBeenCalled();
      expect(proyectoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProyecto>>();
      const proyecto = { id: 10716 };
      jest.spyOn(proyectoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ proyecto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(proyectoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareOperador', () => {
      it('Should forward to operadorService', () => {
        const entity = { id: 6338 };
        const entity2 = { id: 16073 };
        jest.spyOn(operadorService, 'compareOperador');
        comp.compareOperador(entity, entity2);
        expect(operadorService.compareOperador).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
