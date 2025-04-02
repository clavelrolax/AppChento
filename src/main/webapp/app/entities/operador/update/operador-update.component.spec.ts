import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { OperadorService } from '../service/operador.service';
import { IOperador } from '../operador.model';
import { OperadorFormService } from './operador-form.service';

import { OperadorUpdateComponent } from './operador-update.component';

describe('Operador Management Update Component', () => {
  let comp: OperadorUpdateComponent;
  let fixture: ComponentFixture<OperadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let operadorFormService: OperadorFormService;
  let operadorService: OperadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OperadorUpdateComponent],
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
      .overrideTemplate(OperadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OperadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    operadorFormService = TestBed.inject(OperadorFormService);
    operadorService = TestBed.inject(OperadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const operador: IOperador = { id: 16073 };

      activatedRoute.data = of({ operador });
      comp.ngOnInit();

      expect(comp.operador).toEqual(operador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperador>>();
      const operador = { id: 6338 };
      jest.spyOn(operadorFormService, 'getOperador').mockReturnValue(operador);
      jest.spyOn(operadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: operador }));
      saveSubject.complete();

      // THEN
      expect(operadorFormService.getOperador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(operadorService.update).toHaveBeenCalledWith(expect.objectContaining(operador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperador>>();
      const operador = { id: 6338 };
      jest.spyOn(operadorFormService, 'getOperador').mockReturnValue({ id: null });
      jest.spyOn(operadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: operador }));
      saveSubject.complete();

      // THEN
      expect(operadorFormService.getOperador).toHaveBeenCalled();
      expect(operadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperador>>();
      const operador = { id: 6338 };
      jest.spyOn(operadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(operadorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
