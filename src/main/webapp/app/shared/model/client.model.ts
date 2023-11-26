import { IAppUser } from 'app/shared/model/app-user.model';

export interface IClient {
  id?: number;
  name?: string;
  email?: string;
  phone?: string | null;
  address?: string | null;
  appUser?: IAppUser | null;
}

export const defaultValue: Readonly<IClient> = {};
