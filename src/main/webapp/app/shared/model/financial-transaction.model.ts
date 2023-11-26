export interface IFinancialTransaction {
  id?: number;
  transactionType?: string;
  amount?: number;
  dateTime?: string;
  description?: string | null;
}

export const defaultValue: Readonly<IFinancialTransaction> = {};
