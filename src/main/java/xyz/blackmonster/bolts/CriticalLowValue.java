package xyz.blackmonster.bolts;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import xyz.blackmonster.spouts.PriceEmitter;

/**
 * Alerts user BitCoin went below a certain value
 */
public class CriticalLowValue extends BaseRichBolt {

	public static String BOTTOM_LIMIT_CONF = "bottom";
	private float criticalBottomLimit;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.criticalBottomLimit = (Float) stormConf.get(BOTTOM_LIMIT_CONF);
	}

	@Override
	public void execute(Tuple input) {
		float value = input.getFloatByField(PriceEmitter.PRICE);
		if (criticalBottomLimit > value) {
			System.out.println("The value dropped under the critical limit. Stop mining!");
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// nothing to do
	}
}
