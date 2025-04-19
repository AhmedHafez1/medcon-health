import { ROLE } from '../enums/role.enum';

export interface Register {
  fullName: string;
  email: string;
  password: string;
  role: ROLE;
  address: string;
}
