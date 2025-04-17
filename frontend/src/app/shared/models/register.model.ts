import { Role } from '../enums/Role.enum';

export interface Register {
  fullName: string;
  email: string;
  password: string;
  role: Role;
  address: string;
}
