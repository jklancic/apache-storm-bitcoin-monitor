package xyz.blackmonster.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * BitCoin price index for several currencies.
 */
public class BitCoinPriceIndex {

	@JsonProperty("USD")
	private BitCoinPrice usd;
	@JsonProperty("GBP")
	private BitCoinPrice gbp;
	@JsonProperty("EUR")
	private BitCoinPrice eur;

	public BitCoinPrice getUsd() {
		return usd;
	}

	public void setUsd(BitCoinPrice usd) {
		this.usd = usd;
	}

	public BitCoinPrice getGbp() {
		return gbp;
	}

	public void setGbp(BitCoinPrice gbp) {
		this.gbp = gbp;
	}

	public BitCoinPrice getEur() {
		return eur;
	}

	public void setEur(BitCoinPrice eur) {
		this.eur = eur;
	}
}
