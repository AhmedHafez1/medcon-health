export interface DoctorAvailability {
  dayOfWeek: number;
  startTime: string;
  endTime: string;
  isActive: boolean;
}

export interface DoctorRequest {
  userId: number | null;
  specialization: string;
  licenseNumber: string;
  experience: number | null;
  bio: string;
  consultationFee: number | null;
  availabilities: DoctorAvailability[];
}
