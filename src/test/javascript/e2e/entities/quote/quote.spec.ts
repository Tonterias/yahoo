import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { QuoteComponentsPage, QuoteDeleteDialog, QuoteUpdatePage } from './quote.page-object';

const expect = chai.expect;

describe('Quote e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let quoteComponentsPage: QuoteComponentsPage;
  let quoteUpdatePage: QuoteUpdatePage;
  let quoteDeleteDialog: QuoteDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Quotes', async () => {
    await navBarPage.goToEntity('quote');
    quoteComponentsPage = new QuoteComponentsPage();
    await browser.wait(ec.visibilityOf(quoteComponentsPage.title), 5000);
    expect(await quoteComponentsPage.getTitle()).to.eq('yahooApp.quote.home.title');
    await browser.wait(ec.or(ec.visibilityOf(quoteComponentsPage.entities), ec.visibilityOf(quoteComponentsPage.noResult)), 1000);
  });

  it('should load create Quote page', async () => {
    await quoteComponentsPage.clickOnCreateButton();
    quoteUpdatePage = new QuoteUpdatePage();
    expect(await quoteUpdatePage.getPageTitle()).to.eq('yahooApp.quote.home.createOrEditLabel');
    await quoteUpdatePage.cancel();
  });

  it('should create and save Quotes', async () => {
    const nbButtonsBeforeCreate = await quoteComponentsPage.countDeleteButtons();

    await quoteComponentsPage.clickOnCreateButton();

    await promise.all([
      quoteUpdatePage.setSymbolInput('symbol'),
      quoteUpdatePage.setDateInput('2000-12-31'),
      quoteUpdatePage.setOpenInput('5'),
      quoteUpdatePage.setHighInput('5'),
      quoteUpdatePage.setLowInput('5'),
      quoteUpdatePage.setCloseInput('5'),
      quoteUpdatePage.setAdjcloseInput('5'),
      quoteUpdatePage.setVolumeInput('5'),
      quoteUpdatePage.stockSelectLastOption(),
    ]);

    expect(await quoteUpdatePage.getSymbolInput()).to.eq('symbol', 'Expected Symbol value to be equals to symbol');
    expect(await quoteUpdatePage.getDateInput()).to.eq('2000-12-31', 'Expected date value to be equals to 2000-12-31');
    expect(await quoteUpdatePage.getOpenInput()).to.eq('5', 'Expected open value to be equals to 5');
    expect(await quoteUpdatePage.getHighInput()).to.eq('5', 'Expected high value to be equals to 5');
    expect(await quoteUpdatePage.getLowInput()).to.eq('5', 'Expected low value to be equals to 5');
    expect(await quoteUpdatePage.getCloseInput()).to.eq('5', 'Expected close value to be equals to 5');
    expect(await quoteUpdatePage.getAdjcloseInput()).to.eq('5', 'Expected adjclose value to be equals to 5');
    expect(await quoteUpdatePage.getVolumeInput()).to.eq('5', 'Expected volume value to be equals to 5');

    await quoteUpdatePage.save();
    expect(await quoteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await quoteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Quote', async () => {
    const nbButtonsBeforeDelete = await quoteComponentsPage.countDeleteButtons();
    await quoteComponentsPage.clickOnLastDeleteButton();

    quoteDeleteDialog = new QuoteDeleteDialog();
    expect(await quoteDeleteDialog.getDialogTitle()).to.eq('yahooApp.quote.delete.question');
    await quoteDeleteDialog.clickOnConfirmButton();

    expect(await quoteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
