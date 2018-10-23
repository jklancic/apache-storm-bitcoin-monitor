package xyz.blackmonster.bolts;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import xyz.blackmonster.spouts.PriceEmitter;

/**
 * Logs every value
 */
public class LogValue extends BaseBasicBolt {

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		double value = input.getDoubleByField(PriceEmitter.PRICE);
		System.out.println("Current value is: " + value);
		collector.emit(new Values(value));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(PriceEmitter.PRICE));
	}
}
