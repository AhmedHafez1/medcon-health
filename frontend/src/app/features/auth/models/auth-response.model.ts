import { Role } from '../../../shared/enums/role.enum';

export interface AuthResponse {
  token: string;
  user: User;
}

export interface User {
  id: number;
  email: string;
  role: Role;
}
