import { IProduct } from 'app/shared/model/product.model';

export interface IStockHistory {
  id?: number;
  quantityChanged?: number;
  dateTime?: string;
  reason?: string | null;
  product?: IProduct | null;
}

export const defaultValue: Readonly<IStockHistory> = {};
