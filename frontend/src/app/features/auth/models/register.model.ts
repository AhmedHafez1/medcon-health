import { Role } from '../../../shared/enums/role.enum';

export interface Register {
  email: string;
  password: string;
  role: Role;
}
