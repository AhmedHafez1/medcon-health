import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateDoctorComponent } from './create-doctor.component';
import { DoctorService } from '../services/doctor.service';

describe('CreateDoctorComponent', () => {
  let component: CreateDoctorComponent;
  let doctorServiceMock: jest.Mocked<DoctorService>;
  let fixture: ComponentFixture<CreateDoctorComponent>;

  beforeEach(async () => {
    doctorServiceMock = {
      createDoctor: jest.fn(),
    } as unknown as jest.Mocked<DoctorService>;
    await TestBed.configureTestingModule({
      imports: [CreateDoctorComponent],
      providers: [{ provide: DoctorService, useValue: doctorServiceMock }],
    }).compileComponents();

    fixture = TestBed.createComponent(CreateDoctorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
