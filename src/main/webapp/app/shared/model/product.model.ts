import { IStockHistory } from 'app/shared/model/stock-history.model';
import { IAppService } from 'app/shared/model/app-service.model';

export interface IProduct {
  id?: number;
  name?: string;
  description?: string | null;
  quantityInStock?: number | null;
  price?: number;
  stockHistories?: IStockHistory[] | null;
  appServices?: IAppService[] | null;
}

export const defaultValue: Readonly<IProduct> = {};
