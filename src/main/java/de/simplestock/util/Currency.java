package de.simplestock.util;

import java.util.Arrays;

public enum Currency {
    AUD("AUD"),
    BGN("BGN"),
    BRL("BRL"),
    CAD("CAD"),
    CHF("CHF"),
    CNY("CNY"),
    CYP("CYP"),
    CZK("CZK"),
    DKK("DKK"),
    EEK("EEK"),
    GBP("GBP"),
    GRD("GRD"),
    HKD("HKD"),
    HRK("HRK"),
    HUF("HUF"),
    IDR("IDR"),
    ILS("ILS"),
    INR("INR"),
    ISK("ISK"),
    JPY("JPY"),
    KRW("KRW"),
    LTL("LTL"),
    LVL("LVL"),
    MTL("MTL"),
    MXN("MXN"),
    MYR("MYR"),
    NOK("NOK"),
    NZD("NZD"),
    PHP("PHP"),
    PLN("PLN"),
    ROL("ROL"),
    RON("RON"),
    RUB("RUB"),
    SEK("SEK"),
    SGD("SGD"),
    SIT("SIT"),
    SKK("SKK"),
    THB("THB"),
    TRL("TRL"),
    TRY("TRY"),
    USD("USD"),
    ZAR("ZAR");
	
	private final String shortcut;
	
	Currency(String shortcut) {
		this.shortcut = shortcut;
	}
	
	public String getShortcut() {
		return shortcut;
	}
	
	/**
	 * Find a proper enum-value by String-shortcut.
	 *
	 * @param shortcut the string (e.g. "USD")
	 * @return the appropriate currency or optional, which throws further
	 */
	public static Currency findByShortcut(String shortcut) {
		return Arrays.stream(values())
					 .filter(value -> value.getShortcut().equals(shortcut))
					 .findFirst()
					 .orElseThrow();
	}
}