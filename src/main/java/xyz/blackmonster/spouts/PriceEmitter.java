package xyz.blackmonster.spouts;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import xyz.blackmonster.clients.BitCoinClient;
import xyz.blackmonster.clients.BitCoinClientImpl;

/**
 * Retrieves bitcoin price every 60 seconds
 */
public class PriceEmitter extends BaseRichSpout {

	public static String PRICE = "price";
	public static String INTERVAL_LENGTH_CONF = "interval";
	public static String URL_CONF = "url";

	private SpoutOutputCollector collector;
	private long intervalLength;
	private BitCoinClient bitCoinClient;

	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
		this.intervalLength = (Long) conf.get(INTERVAL_LENGTH_CONF);
		this.bitCoinClient = new BitCoinClientImpl((String) conf.get(URL_CONF));
	}

	@Override
	public void nextTuple() {
		double value = bitCoinClient.readPriceEUR();
		if (value != -1) {
			System.out.println("Emitting value: " + value);
			collector.emit(new Values(value));
		}
		try {
			Thread.sleep(intervalLength);
		} catch (InterruptedException e) {
			System.out.println("Not able to pause: " + e.getMessage());
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(PRICE));
	}
}
