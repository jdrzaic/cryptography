package criptography.calculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrequenciesCalculator {

	private List<String> contents;
	
	private Map<String, Double> frequencies;
	
	public FrequenciesCalculator(List<String> contents) {
		this.contents = contents;
		frequencies = new HashMap<String, Double>();
	}
	
	public Map<String, Double> calculateFrequency(int n) {
		frequencies.clear();
		int lensSum = 0; 
		for(String s : contents) {
			calculateTextFrequency(n, s);
			lensSum += s.length();
		}
		for(String key : frequencies.keySet()) {
			frequencies.put(key, frequencies.get(key) / (lensSum - n + 1));
		}
		return new HashMap<String, Double>(frequencies);
	}

	private void calculateTextFrequency(int n, String s) {
		int len = s.length();
		StringBuilder sb = new StringBuilder(s.substring(0, n));
		for(int i = 1; i < len; ++i) {
			if(frequencies.containsKey(sb.toString())) {
				frequencies.put(sb.toString(), frequencies.get(sb.toString()) + 1);
			}else {
				frequencies.put(sb.toString(), 1.0);
			}
			sb.deleteCharAt(0);
			sb.append(s.charAt(i));
		}
	}
}
