import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IOperador } from '../operador.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../operador.test-samples';

import { OperadorService, RestOperador } from './operador.service';

const requireRestSample: RestOperador = {
  ...sampleWithRequiredData,
  fechaCreacion: sampleWithRequiredData.fechaCreacion?.format(DATE_FORMAT),
};

describe('Operador Service', () => {
  let service: OperadorService;
  let httpMock: HttpTestingController;
  let expectedResult: IOperador | IOperador[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(OperadorService);
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

    it('should create a Operador', () => {
      const operador = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(operador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Operador', () => {
      const operador = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(operador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Operador', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Operador', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Operador', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOperadorToCollectionIfMissing', () => {
      it('should add a Operador to an empty array', () => {
        const operador: IOperador = sampleWithRequiredData;
        expectedResult = service.addOperadorToCollectionIfMissing([], operador);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(operador);
      });

      it('should not add a Operador to an array that contains it', () => {
        const operador: IOperador = sampleWithRequiredData;
        const operadorCollection: IOperador[] = [
          {
            ...operador,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOperadorToCollectionIfMissing(operadorCollection, operador);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Operador to an array that doesn't contain it", () => {
        const operador: IOperador = sampleWithRequiredData;
        const operadorCollection: IOperador[] = [sampleWithPartialData];
        expectedResult = service.addOperadorToCollectionIfMissing(operadorCollection, operador);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(operador);
      });

      it('should add only unique Operador to an array', () => {
        const operadorArray: IOperador[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const operadorCollection: IOperador[] = [sampleWithRequiredData];
        expectedResult = service.addOperadorToCollectionIfMissing(operadorCollection, ...operadorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const operador: IOperador = sampleWithRequiredData;
        const operador2: IOperador = sampleWithPartialData;
        expectedResult = service.addOperadorToCollectionIfMissing([], operador, operador2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(operador);
        expect(expectedResult).toContain(operador2);
      });

      it('should accept null and undefined values', () => {
        const operador: IOperador = sampleWithRequiredData;
        expectedResult = service.addOperadorToCollectionIfMissing([], null, operador, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(operador);
      });

      it('should return initial array if no Operador is added', () => {
        const operadorCollection: IOperador[] = [sampleWithRequiredData];
        expectedResult = service.addOperadorToCollectionIfMissing(operadorCollection, undefined, null);
        expect(expectedResult).toEqual(operadorCollection);
      });
    });

    describe('compareOperador', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOperador(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 6338 };
        const entity2 = null;

        const compareResult1 = service.compareOperador(entity1, entity2);
        const compareResult2 = service.compareOperador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 6338 };
        const entity2 = { id: 16073 };

        const compareResult1 = service.compareOperador(entity1, entity2);
        const compareResult2 = service.compareOperador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 6338 };
        const entity2 = { id: 6338 };

        const compareResult1 = service.compareOperador(entity1, entity2);
        const compareResult2 = service.compareOperador(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
