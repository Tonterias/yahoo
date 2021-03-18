import { element, by, ElementFinder } from 'protractor';

export class QuoteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-quote div table .btn-danger'));
  title = element.all(by.css('jhi-quote div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class QuoteUpdatePage {
  pageTitle = element(by.id('jhi-quote-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  symbolInput = element(by.id('field_symbol'));
  dateInput = element(by.id('field_date'));
  openInput = element(by.id('field_open'));
  highInput = element(by.id('field_high'));
  lowInput = element(by.id('field_low'));
  closeInput = element(by.id('field_close'));
  adjcloseInput = element(by.id('field_adjclose'));
  volumeInput = element(by.id('field_volume'));

  stockSelect = element(by.id('field_stock'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSymbolInput(symbol: string): Promise<void> {
    await this.symbolInput.sendKeys(symbol);
  }

  async getSymbolInput(): Promise<string> {
    return await this.symbolInput.getAttribute('value');
  }

  async setDateInput(date: string): Promise<void> {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput(): Promise<string> {
    return await this.dateInput.getAttribute('value');
  }

  async setOpenInput(open: string): Promise<void> {
    await this.openInput.sendKeys(open);
  }

  async getOpenInput(): Promise<string> {
    return await this.openInput.getAttribute('value');
  }

  async setHighInput(high: string): Promise<void> {
    await this.highInput.sendKeys(high);
  }

  async getHighInput(): Promise<string> {
    return await this.highInput.getAttribute('value');
  }

  async setLowInput(low: string): Promise<void> {
    await this.lowInput.sendKeys(low);
  }

  async getLowInput(): Promise<string> {
    return await this.lowInput.getAttribute('value');
  }

  async setCloseInput(close: string): Promise<void> {
    await this.closeInput.sendKeys(close);
  }

  async getCloseInput(): Promise<string> {
    return await this.closeInput.getAttribute('value');
  }

  async setAdjcloseInput(adjclose: string): Promise<void> {
    await this.adjcloseInput.sendKeys(adjclose);
  }

  async getAdjcloseInput(): Promise<string> {
    return await this.adjcloseInput.getAttribute('value');
  }

  async setVolumeInput(volume: string): Promise<void> {
    await this.volumeInput.sendKeys(volume);
  }

  async getVolumeInput(): Promise<string> {
    return await this.volumeInput.getAttribute('value');
  }

  async stockSelectLastOption(): Promise<void> {
    await this.stockSelect.all(by.tagName('option')).last().click();
  }

  async stockSelectOption(option: string): Promise<void> {
    await this.stockSelect.sendKeys(option);
  }

  getStockSelect(): ElementFinder {
    return this.stockSelect;
  }

  async getStockSelectedOption(): Promise<string> {
    return await this.stockSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class QuoteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-quote-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-quote'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
