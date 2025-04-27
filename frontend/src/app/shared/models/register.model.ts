import { ROLE } from '../enums/role.enum';

export interface Register {
  email: string;
  password: string;
  role: ROLE;
}
