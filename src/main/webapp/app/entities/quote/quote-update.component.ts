import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuote, Quote } from 'app/shared/model/quote.model';
import { QuoteService } from './quote.service';
import { IStock } from 'app/shared/model/stock.model';
import { StockService } from 'app/entities/stock/stock.service';

@Component({
  selector: 'jhi-quote-update',
  templateUrl: './quote-update.component.html',
})
export class QuoteUpdateComponent implements OnInit {
  isSaving = false;
  stocks: IStock[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    symbol: [null, [Validators.required]],
    date: [null, [Validators.required]],
    open: [],
    high: [],
    low: [],
    close: [null, [Validators.required]],
    adjclose: [null, [Validators.required]],
    volume: [],
    stockId: [],
  });

  constructor(
    protected quoteService: QuoteService,
    protected stockService: StockService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quote }) => {
      this.updateForm(quote);

      this.stockService.query().subscribe((res: HttpResponse<IStock[]>) => (this.stocks = res.body || []));
    });
  }

  updateForm(quote: IQuote): void {
    this.editForm.patchValue({
      id: quote.id,
      symbol: quote.symbol,
      date: quote.date,
      open: quote.open,
      high: quote.high,
      low: quote.low,
      close: quote.close,
      adjclose: quote.adjclose,
      volume: quote.volume,
      stockId: quote.stockId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const quote = this.createFromForm();
    if (quote.id !== undefined) {
      this.subscribeToSaveResponse(this.quoteService.update(quote));
    } else {
      this.subscribeToSaveResponse(this.quoteService.create(quote));
    }
  }

  private createFromForm(): IQuote {
    return {
      ...new Quote(),
      id: this.editForm.get(['id'])!.value,
      symbol: this.editForm.get(['symbol'])!.value,
      date: this.editForm.get(['date'])!.value,
      open: this.editForm.get(['open'])!.value,
      high: this.editForm.get(['high'])!.value,
      low: this.editForm.get(['low'])!.value,
      close: this.editForm.get(['close'])!.value,
      adjclose: this.editForm.get(['adjclose'])!.value,
      volume: this.editForm.get(['volume'])!.value,
      stockId: this.editForm.get(['stockId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuote>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IStock): any {
    return item.id;
  }
}
