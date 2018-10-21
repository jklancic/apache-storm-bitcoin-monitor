package xyz.blackmonster.bolts;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import xyz.blackmonster.spouts.PriceEmitter;

/**
 * Alerts user BitCoin went above a certain value
 */
public class CriticalTopValue extends BaseRichBolt {

	public static String TOP_LIMIT_CONF = "top";
	private float criticalTopLimit;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.criticalTopLimit = (Float) stormConf.get(TOP_LIMIT_CONF);
	}

	@Override
	public void execute(Tuple input) {
		float value = input.getFloatByField(PriceEmitter.PRICE);
		if (criticalTopLimit < value) {
			System.out.println("The value topped the critical limit. Start mining!");
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// nothing to do
	}
}
