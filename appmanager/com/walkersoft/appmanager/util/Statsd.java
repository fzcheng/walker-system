package com.walkersoft.appmanager.util;

import java.util.Locale;

public class Statsd {

	public boolean decrement(String key) {
		return increment(key, -1, 1.0);
	}

	public boolean decrement(String key, int magnitude) {
		return decrement(key, magnitude, 1.0);
	}

	public boolean decrement(String key, int magnitude, double sampleRate) {
		magnitude = magnitude < 0 ? magnitude : -magnitude;
		return increment(key, magnitude, sampleRate);
	}

	public boolean decrement(String... keys) {
		return increment(-1, 1.0, keys);
	}

	public boolean decrement(int magnitude, String... keys) {
		magnitude = magnitude < 0 ? magnitude : -magnitude;
		return increment(magnitude, 1.0, keys);
	}

	public boolean decrement(int magnitude, double sampleRate, String... keys) {
		magnitude = magnitude < 0 ? magnitude : -magnitude;
		return increment(magnitude, sampleRate, keys);
	}

	public boolean increment(String key) {
		return increment(key, 1, 1.0);
	}

	public boolean increment(String key, int magnitude) {
		return increment(key, magnitude, 1.0);
	}

	public boolean increment(String key, int magnitude, double sampleRate) {
		String stat = String.format(Locale.ENGLISH, "%s:%s|c", key, magnitude);
		
		StatsdClient sc = StatsdClient.getInstance(); 
		if(sc == null)
			return false;
		return sc.send(sampleRate, stat);
	}
	
	public boolean increment(int magnitude, double sampleRate, String... keys) {
		String[] stats = new String[keys.length];
		for (int i = 0; i < keys.length; i++) {
			stats[i] = String.format(Locale.ENGLISH, "%s:%s|c", keys[i],
					magnitude);
		}
		
		StatsdClient sc = StatsdClient.getInstance(); 
		if(sc == null)
			return false;
		return sc.send(sampleRate, stats);
	}
}
