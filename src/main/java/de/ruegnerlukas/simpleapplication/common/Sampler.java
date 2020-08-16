package de.ruegnerlukas.simpleapplication.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Sampler {


	private static Map<String, List<Sample>> samples = new HashMap<>();




	public static Sample start(final String name) {
		Sample sample = new Sample(name, System.currentTimeMillis(), -1);
//		samples.computeIfAbsent(name, k -> new ArrayList<>()).add(sample);
		return sample;
	}




	public static long getAvgTime(final String name) {
		return (long) Sampler.samples.getOrDefault(name, List.of()).stream()
				.map(Sample::getTime)
				.mapToLong(t -> t)
				.average()
				.orElse(-1);
	}




	public static long getMinTime(final String name) {
		return Sampler.samples.getOrDefault(name, List.of()).stream()
				.map(Sample::getTime)
				.mapToLong(t -> t)
				.min()
				.orElse(-1);
	}




	public static long getMaxTime(final String name) {
		return Sampler.samples.getOrDefault(name, List.of()).stream()
				.map(Sample::getTime)
				.mapToLong(t -> t)
				.max()
				.orElse(-1);
	}


	public static Set<String> getAllNames() {
		return samples.keySet();
	}




	public static void clear() {
		samples.clear();
	}




	@Getter
	@Setter
	@AllArgsConstructor
	public static class Sample {


		private String name;
		private long start;
		private long end;




		public long getTime() {
			return end - start;
		}




		public long stop() {
			end = System.currentTimeMillis();
			return end - start;
		}

	}

}
