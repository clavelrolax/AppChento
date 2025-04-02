import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IProyecto } from '../proyecto.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../proyecto.test-samples';

import { ProyectoService, RestProyecto } from './proyecto.service';

const requireRestSample: RestProyecto = {
  ...sampleWithRequiredData,
  fechaInicio: sampleWithRequiredData.fechaInicio?.format(DATE_FORMAT),
  fechaFin: sampleWithRequiredData.fechaFin?.format(DATE_FORMAT),
};

describe('Proyecto Service', () => {
  let service: ProyectoService;
  let httpMock: HttpTestingController;
  let expectedResult: IProyecto | IProyecto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ProyectoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Proyecto', () => {
      const proyecto = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(proyecto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Proyecto', () => {
      const proyecto = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(proyecto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Proyecto', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Proyecto', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Proyecto', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProyectoToCollectionIfMissing', () => {
      it('should add a Proyecto to an empty array', () => {
        const proyecto: IProyecto = sampleWithRequiredData;
        expectedResult = service.addProyectoToCollectionIfMissing([], proyecto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(proyecto);
      });

      it('should not add a Proyecto to an array that contains it', () => {
        const proyecto: IProyecto = sampleWithRequiredData;
        const proyectoCollection: IProyecto[] = [
          {
            ...proyecto,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProyectoToCollectionIfMissing(proyectoCollection, proyecto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Proyecto to an array that doesn't contain it", () => {
        const proyecto: IProyecto = sampleWithRequiredData;
        const proyectoCollection: IProyecto[] = [sampleWithPartialData];
        expectedResult = service.addProyectoToCollectionIfMissing(proyectoCollection, proyecto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(proyecto);
      });

      it('should add only unique Proyecto to an array', () => {
        const proyectoArray: IProyecto[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const proyectoCollection: IProyecto[] = [sampleWithRequiredData];
        expectedResult = service.addProyectoToCollectionIfMissing(proyectoCollection, ...proyectoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const proyecto: IProyecto = sampleWithRequiredData;
        const proyecto2: IProyecto = sampleWithPartialData;
        expectedResult = service.addProyectoToCollectionIfMissing([], proyecto, proyecto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(proyecto);
        expect(expectedResult).toContain(proyecto2);
      });

      it('should accept null and undefined values', () => {
        const proyecto: IProyecto = sampleWithRequiredData;
        expectedResult = service.addProyectoToCollectionIfMissing([], null, proyecto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(proyecto);
      });

      it('should return initial array if no Proyecto is added', () => {
        const proyectoCollection: IProyecto[] = [sampleWithRequiredData];
        expectedResult = service.addProyectoToCollectionIfMissing(proyectoCollection, undefined, null);
        expect(expectedResult).toEqual(proyectoCollection);
      });
    });

    describe('compareProyecto', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProyecto(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 10716 };
        const entity2 = null;

        const compareResult1 = service.compareProyecto(entity1, entity2);
        const compareResult2 = service.compareProyecto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 10716 };
        const entity2 = { id: 11127 };

        const compareResult1 = service.compareProyecto(entity1, entity2);
        const compareResult2 = service.compareProyecto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 10716 };
        const entity2 = { id: 10716 };

        const compareResult1 = service.compareProyecto(entity1, entity2);
        const compareResult2 = service.compareProyecto(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
