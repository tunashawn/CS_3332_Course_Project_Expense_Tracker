package sample.utils;

public enum CurrencyCode {

  /** Rate with 1 USD */
  USD("USD", 1, "$"),
  EUR("EUR", 0.85, "€"),
  GBP("GBP", 0.73, "£"),
  INR("INR", 74.43, "₹"),
  AUD("AUD", 1.35, "AU$"),
  CAD("CAD", 1.26, "C$"),
  SGD("SGD", 1.36, "S$"),
  MYR("MYR", 4.23, "RM"),
  JPY("JPY", 110.44, "JP¥"),
  CNY("CNY", 6.48, "¥"),
  THB("THB", 32.92, "฿"),
  HKD("HKD", 7.77, "HK$"),
  VND("VND", 23012, "₫");

  private String currencyCode;

  private double rate;

  private String symbol;

  CurrencyCode(String currencyCode, double rate, String symbol) {
    this.currencyCode = currencyCode;
    this.rate = rate;
    this.symbol = symbol;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public double getRate() {
    return rate;
  }

  public String getSymbol() {
    return symbol;
  }
}
