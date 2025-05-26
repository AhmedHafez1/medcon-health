import { Role } from '../../../shared/enums/role.enum';

export interface UserProfile {
  id: string;
  userId: string;
  email: string;
  firstName: string;
  lastName: string;
  gender: string;
  phone: string;
  address: string;
  profilePicture: string | null;
  dob: Date | null;
  role: Role;
}

export interface UserProfileUpdate {
  email: string;
  firstName: string;
  lastName: string;
  gender: string;
  phone: string;
  address: string;
  dob: Date | null;
}
