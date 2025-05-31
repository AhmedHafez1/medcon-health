import { TestBed } from '@angular/core/testing';

import { DoctorService } from './doctor.service';
import { HttpClient } from '@angular/common/http';

describe('DoctorService', () => {
  let service: DoctorService;
  let mockHttpClient: jest.Mocked<HttpClient>;

  beforeEach(() => {
    mockHttpClient = {
      get: jest.fn(),
      post: jest.fn(),
      put: jest.fn(),
      delete: jest.fn(),
      patch: jest.fn(),
      request: jest.fn(),
    } as unknown as jest.Mocked<HttpClient>;

    TestBed.configureTestingModule({
      providers: [
        DoctorService,
        { provide: HttpClient, useValue: mockHttpClient },
      ],
    });
    service = TestBed.inject(DoctorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
