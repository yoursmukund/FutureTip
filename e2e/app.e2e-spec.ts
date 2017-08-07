import { FutureTipPage } from './app.po';

describe('future-tip App', () => {
  let page: FutureTipPage;

  beforeEach(() => {
    page = new FutureTipPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
