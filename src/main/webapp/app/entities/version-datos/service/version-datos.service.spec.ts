import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IVersionDatos } from '../version-datos.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../version-datos.test-samples';

import { RestVersionDatos, VersionDatosService } from './version-datos.service';

const requireRestSample: RestVersionDatos = {
  ...sampleWithRequiredData,
  fechaVersion: sampleWithRequiredData.fechaVersion?.format(DATE_FORMAT),
};

describe('VersionDatos Service', () => {
  let service: VersionDatosService;
  let httpMock: HttpTestingController;
  let expectedResult: IVersionDatos | IVersionDatos[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(VersionDatosService);
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

    it('should create a VersionDatos', () => {
      const versionDatos = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(versionDatos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a VersionDatos', () => {
      const versionDatos = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(versionDatos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a VersionDatos', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of VersionDatos', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a VersionDatos', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addVersionDatosToCollectionIfMissing', () => {
      it('should add a VersionDatos to an empty array', () => {
        const versionDatos: IVersionDatos = sampleWithRequiredData;
        expectedResult = service.addVersionDatosToCollectionIfMissing([], versionDatos);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(versionDatos);
      });

      it('should not add a VersionDatos to an array that contains it', () => {
        const versionDatos: IVersionDatos = sampleWithRequiredData;
        const versionDatosCollection: IVersionDatos[] = [
          {
            ...versionDatos,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addVersionDatosToCollectionIfMissing(versionDatosCollection, versionDatos);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a VersionDatos to an array that doesn't contain it", () => {
        const versionDatos: IVersionDatos = sampleWithRequiredData;
        const versionDatosCollection: IVersionDatos[] = [sampleWithPartialData];
        expectedResult = service.addVersionDatosToCollectionIfMissing(versionDatosCollection, versionDatos);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(versionDatos);
      });

      it('should add only unique VersionDatos to an array', () => {
        const versionDatosArray: IVersionDatos[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const versionDatosCollection: IVersionDatos[] = [sampleWithRequiredData];
        expectedResult = service.addVersionDatosToCollectionIfMissing(versionDatosCollection, ...versionDatosArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const versionDatos: IVersionDatos = sampleWithRequiredData;
        const versionDatos2: IVersionDatos = sampleWithPartialData;
        expectedResult = service.addVersionDatosToCollectionIfMissing([], versionDatos, versionDatos2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(versionDatos);
        expect(expectedResult).toContain(versionDatos2);
      });

      it('should accept null and undefined values', () => {
        const versionDatos: IVersionDatos = sampleWithRequiredData;
        expectedResult = service.addVersionDatosToCollectionIfMissing([], null, versionDatos, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(versionDatos);
      });

      it('should return initial array if no VersionDatos is added', () => {
        const versionDatosCollection: IVersionDatos[] = [sampleWithRequiredData];
        expectedResult = service.addVersionDatosToCollectionIfMissing(versionDatosCollection, undefined, null);
        expect(expectedResult).toEqual(versionDatosCollection);
      });
    });

    describe('compareVersionDatos', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareVersionDatos(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 31885 };
        const entity2 = null;

        const compareResult1 = service.compareVersionDatos(entity1, entity2);
        const compareResult2 = service.compareVersionDatos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 31885 };
        const entity2 = { id: 18543 };

        const compareResult1 = service.compareVersionDatos(entity1, entity2);
        const compareResult2 = service.compareVersionDatos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 31885 };
        const entity2 = { id: 31885 };

        const compareResult1 = service.compareVersionDatos(entity1, entity2);
        const compareResult2 = service.compareVersionDatos(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
