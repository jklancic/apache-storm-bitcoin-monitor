package xyz.blackmonster;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

import xyz.blackmonster.bolts.CriticalLowValue;
import xyz.blackmonster.bolts.CriticalTopValue;
import xyz.blackmonster.bolts.LogValue;
import xyz.blackmonster.spouts.PriceEmitter;

/**
 * Topology for monitoring BitCoin value.
 */
public class BitCoinMonitor {

	public static final String PRICE_EMITTER = "price-emitter";
	public static final String LOG_VALUE = "log-value";

	public static void main(String[] args) throws InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(PRICE_EMITTER, new PriceEmitter());

		builder.setBolt(LOG_VALUE, new LogValue()).shuffleGrouping(PRICE_EMITTER);
		builder.setBolt("critical-low", new CriticalLowValue()).shuffleGrouping(LOG_VALUE);
		builder.setBolt("critical-top", new CriticalTopValue()).shuffleGrouping(LOG_VALUE);

		// Configuration
		Config config = new Config();
		config.put(PriceEmitter.INTERVAL_LENGTH_CONF, new Integer(60000));
		config.put(PriceEmitter.URL_CONF, "https://api.coindesk.com/v1/bpi/currentprice.json");
		config.put(CriticalLowValue.BOTTOM_LIMIT_CONF, new Float(6000));
		config.put(CriticalTopValue.TOP_LIMIT_CONF, new Float(6200));
		config.setDebug(false);

		// Run topology
		LocalCluster localCluster = new LocalCluster();
		localCluster.submitTopology("bitcoin-monitor-topology", config, builder.createTopology());
		Thread.sleep(240000);
		localCluster.shutdown();
	}
}
