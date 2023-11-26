export interface IReward {
  id?: number;
  points?: number | null;
  description?: string | null;
}

export const defaultValue: Readonly<IReward> = {};
