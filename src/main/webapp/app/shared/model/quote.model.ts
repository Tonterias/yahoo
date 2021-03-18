import { Moment } from 'moment';

export interface IQuote {
  id?: number;
  symbol?: string;
  date?: Moment;
  open?: number;
  high?: number;
  low?: number;
  close?: number;
  adjclose?: number;
  volume?: number;
  stockName?: string;
  stockId?: number;
}

export class Quote implements IQuote {
  constructor(
    public id?: number,
    public symbol?: string,
    public date?: Moment,
    public open?: number,
    public high?: number,
    public low?: number,
    public close?: number,
    public adjclose?: number,
    public volume?: number,
    public stockName?: string,
    public stockId?: number
  ) {}
}
