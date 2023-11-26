import { IClient } from 'app/shared/model/client.model';
import { IAppService } from 'app/shared/model/app-service.model';
import { IAppUser } from 'app/shared/model/app-user.model';

export interface IAppointment {
  id?: number;
  dateTime?: string;
  status?: string;
  client?: IClient | null;
  appService?: IAppService | null;
  appUser?: IAppUser | null;
}

export const defaultValue: Readonly<IAppointment> = {};
