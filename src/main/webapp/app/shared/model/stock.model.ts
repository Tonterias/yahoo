export interface IStock {
  id?: number;
  name?: string;
  userLogin?: string;
  userId?: number;
}

export class Stock implements IStock {
  constructor(public id?: number, public name?: string, public userLogin?: string, public userId?: number) {}
}
