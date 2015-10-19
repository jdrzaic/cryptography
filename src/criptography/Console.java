package criptography;

import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import criptography.calculator.FrequenciesCalculator;
import criptography.reader.Reader;

public class Console {

	public static void main(String[] args) {
				
		String folder = ".";
		if(args.length > 0) {
			folder = args[0];
		}
		Reader reader;
		reader = new Reader(folder);
		try {
			reader.loadContents();
		}catch(InvalidParameterException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
		FrequenciesCalculator fc = new FrequenciesCalculator(reader.getContents());
		Map<String, Map<String, Double> > data = new HashMap<String, Map<String, Double>>();
		data.put("1graph", fc.calculateFrequency(1));
		data.put("2graph", fc.calculateFrequency(2));
		data.put("3graph", fc.calculateFrequency(3));
		
		Gson gson = new Gson();
		String json = gson.toJson(data);

		try {
			FileWriter writer = new FileWriter("/home/jelena/graphs.json");
			writer.write(json);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(json);

	}
}
