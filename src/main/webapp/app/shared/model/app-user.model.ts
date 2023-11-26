import { IClient } from 'app/shared/model/client.model';

export interface IAppUser {
  id?: number;
  username?: string;
  password?: string;
  email?: string;
  role?: string;
  client?: IClient | null;
}

export const defaultValue: Readonly<IAppUser> = {};
