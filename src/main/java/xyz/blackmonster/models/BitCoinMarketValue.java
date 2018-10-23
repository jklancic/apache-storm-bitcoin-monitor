package xyz.blackmonster.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * BitCoin market value
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitCoinMarketValue {

	private CurrentTime currentTime;
	private String chartName;
	private BitCoinPriceIndex bpi;

	public CurrentTime getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(CurrentTime currentTime) {
		this.currentTime = currentTime;
	}

	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public BitCoinPriceIndex getBpi() {
		return bpi;
	}

	public void setBpi(BitCoinPriceIndex bpi) {
		this.bpi = bpi;
	}
}
