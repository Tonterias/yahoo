import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'stock',
        loadChildren: () => import('./stock/stock.module').then(m => m.YahooStockModule),
      },
      {
        path: 'quote',
        loadChildren: () => import('./quote/quote.module').then(m => m.YahooQuoteModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class YahooEntityModule {}
