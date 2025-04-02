import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDatosImagen } from '../datos-imagen.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../datos-imagen.test-samples';

import { DatosImagenService } from './datos-imagen.service';

const requireRestSample: IDatosImagen = {
  ...sampleWithRequiredData,
};

describe('DatosImagen Service', () => {
  let service: DatosImagenService;
  let httpMock: HttpTestingController;
  let expectedResult: IDatosImagen | IDatosImagen[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DatosImagenService);
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

    it('should create a DatosImagen', () => {
      const datosImagen = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(datosImagen).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DatosImagen', () => {
      const datosImagen = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(datosImagen).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DatosImagen', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DatosImagen', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DatosImagen', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDatosImagenToCollectionIfMissing', () => {
      it('should add a DatosImagen to an empty array', () => {
        const datosImagen: IDatosImagen = sampleWithRequiredData;
        expectedResult = service.addDatosImagenToCollectionIfMissing([], datosImagen);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(datosImagen);
      });

      it('should not add a DatosImagen to an array that contains it', () => {
        const datosImagen: IDatosImagen = sampleWithRequiredData;
        const datosImagenCollection: IDatosImagen[] = [
          {
            ...datosImagen,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDatosImagenToCollectionIfMissing(datosImagenCollection, datosImagen);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DatosImagen to an array that doesn't contain it", () => {
        const datosImagen: IDatosImagen = sampleWithRequiredData;
        const datosImagenCollection: IDatosImagen[] = [sampleWithPartialData];
        expectedResult = service.addDatosImagenToCollectionIfMissing(datosImagenCollection, datosImagen);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(datosImagen);
      });

      it('should add only unique DatosImagen to an array', () => {
        const datosImagenArray: IDatosImagen[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const datosImagenCollection: IDatosImagen[] = [sampleWithRequiredData];
        expectedResult = service.addDatosImagenToCollectionIfMissing(datosImagenCollection, ...datosImagenArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const datosImagen: IDatosImagen = sampleWithRequiredData;
        const datosImagen2: IDatosImagen = sampleWithPartialData;
        expectedResult = service.addDatosImagenToCollectionIfMissing([], datosImagen, datosImagen2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(datosImagen);
        expect(expectedResult).toContain(datosImagen2);
      });

      it('should accept null and undefined values', () => {
        const datosImagen: IDatosImagen = sampleWithRequiredData;
        expectedResult = service.addDatosImagenToCollectionIfMissing([], null, datosImagen, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(datosImagen);
      });

      it('should return initial array if no DatosImagen is added', () => {
        const datosImagenCollection: IDatosImagen[] = [sampleWithRequiredData];
        expectedResult = service.addDatosImagenToCollectionIfMissing(datosImagenCollection, undefined, null);
        expect(expectedResult).toEqual(datosImagenCollection);
      });
    });

    describe('compareDatosImagen', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDatosImagen(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 21783 };
        const entity2 = null;

        const compareResult1 = service.compareDatosImagen(entity1, entity2);
        const compareResult2 = service.compareDatosImagen(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 21783 };
        const entity2 = { id: 15193 };

        const compareResult1 = service.compareDatosImagen(entity1, entity2);
        const compareResult2 = service.compareDatosImagen(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 21783 };
        const entity2 = { id: 21783 };

        const compareResult1 = service.compareDatosImagen(entity1, entity2);
        const compareResult2 = service.compareDatosImagen(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
