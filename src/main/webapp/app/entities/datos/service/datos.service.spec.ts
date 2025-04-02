import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDatos } from '../datos.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../datos.test-samples';

import { DatosService } from './datos.service';

const requireRestSample: IDatos = {
  ...sampleWithRequiredData,
};

describe('Datos Service', () => {
  let service: DatosService;
  let httpMock: HttpTestingController;
  let expectedResult: IDatos | IDatos[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DatosService);
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

    it('should create a Datos', () => {
      const datos = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(datos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Datos', () => {
      const datos = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(datos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Datos', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Datos', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Datos', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDatosToCollectionIfMissing', () => {
      it('should add a Datos to an empty array', () => {
        const datos: IDatos = sampleWithRequiredData;
        expectedResult = service.addDatosToCollectionIfMissing([], datos);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(datos);
      });

      it('should not add a Datos to an array that contains it', () => {
        const datos: IDatos = sampleWithRequiredData;
        const datosCollection: IDatos[] = [
          {
            ...datos,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDatosToCollectionIfMissing(datosCollection, datos);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Datos to an array that doesn't contain it", () => {
        const datos: IDatos = sampleWithRequiredData;
        const datosCollection: IDatos[] = [sampleWithPartialData];
        expectedResult = service.addDatosToCollectionIfMissing(datosCollection, datos);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(datos);
      });

      it('should add only unique Datos to an array', () => {
        const datosArray: IDatos[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const datosCollection: IDatos[] = [sampleWithRequiredData];
        expectedResult = service.addDatosToCollectionIfMissing(datosCollection, ...datosArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const datos: IDatos = sampleWithRequiredData;
        const datos2: IDatos = sampleWithPartialData;
        expectedResult = service.addDatosToCollectionIfMissing([], datos, datos2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(datos);
        expect(expectedResult).toContain(datos2);
      });

      it('should accept null and undefined values', () => {
        const datos: IDatos = sampleWithRequiredData;
        expectedResult = service.addDatosToCollectionIfMissing([], null, datos, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(datos);
      });

      it('should return initial array if no Datos is added', () => {
        const datosCollection: IDatos[] = [sampleWithRequiredData];
        expectedResult = service.addDatosToCollectionIfMissing(datosCollection, undefined, null);
        expect(expectedResult).toEqual(datosCollection);
      });
    });

    describe('compareDatos', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDatos(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 10279 };
        const entity2 = null;

        const compareResult1 = service.compareDatos(entity1, entity2);
        const compareResult2 = service.compareDatos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 10279 };
        const entity2 = { id: 25344 };

        const compareResult1 = service.compareDatos(entity1, entity2);
        const compareResult2 = service.compareDatos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 10279 };
        const entity2 = { id: 10279 };

        const compareResult1 = service.compareDatos(entity1, entity2);
        const compareResult2 = service.compareDatos(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
