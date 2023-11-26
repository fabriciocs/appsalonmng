import { IProduct } from 'app/shared/model/product.model';

export interface IAppService {
  id?: number;
  name?: string;
  description?: string | null;
  duration?: number;
  price?: number;
  products?: IProduct[] | null;
}

export const defaultValue: Readonly<IAppService> = {};
