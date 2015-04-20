
```
package com.marco.brownbag.functional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Ordering;

public class MicroBenchMarkFunctional {

	private static final int totStrings = 2;

	public static void main(String[] args) {

		Set<String> someNames = new HashSet<String>();

		init(someNames);

		for (int i = 1; i < totStrings; i++) {
			someNames.add("marco_" + i);
			someNames.add("someone_else_" + i);
		}

		System.out.println("start");

		run(someNames);

	}

	private static void run(Set<String> someNames) {
		System.out.println("========================");
		long start = System.nanoTime();
		int totalLoops = 20;
		for (int i = 1; i < totalLoops; i++) {
			classic(someNames);
		}
		System.out.println("Classic         : " + ((System.nanoTime() - start)) / totalLoops);

		start = System.nanoTime();
		for (int i = 1; i < totalLoops; i++) {
			guava(someNames);
		}
		System.out.println("Guava           : " + ((System.nanoTime() - start)) / totalLoops);

		start = System.nanoTime();
		for (int i = 1; i < totalLoops; i++) {
			stream(someNames);
		}
		System.out.println("Stream          : " + ((System.nanoTime() - start)) / totalLoops);

		start = System.nanoTime();
		for (int i = 1; i < totalLoops; i++) {
			parallelStream(someNames);
		}
		System.out.println("Parallel Stream : " + ((System.nanoTime() - start)) / totalLoops);

		System.out.println("========================");
	}

	private static void init(Set<String> someNames) {
		someNames.add("marco_1");
		classic(someNames);
		guava(someNames);
		stream(someNames);
		parallelStream(someNames);
		someNames.clear();
	}

	private static String stream(Set<String> someNames) {
		return someNames.stream().filter(element -> element.startsWith("m")).map(element -> element.replaceAll("marco_", "")).sorted()
				.collect(Collectors.joining(","));
	}

	private static String parallelStream(Set<String> someNames) {
		return someNames.parallelStream().filter(element -> element.startsWith("m")).map(element -> element.replaceAll("marco_", "")).sorted()
				.collect(Collectors.joining(","));
	}

	private static String guava(Set<String> someNames) {
		return Joiner.on(',').join(
				Ordering.from(String.CASE_INSENSITIVE_ORDER).immutableSortedCopy(
						Collections2.transform(Collections2.filter(someNames, Predicates.containsPattern("marco")), REPLACE_MARCO)));

	}

	private static Function<String, String> REPLACE_MARCO = new Function<String, String>() {
		@Override
		public String apply(final String element) {
			return element.replaceAll("marco_", "");
		}
	};

	private static String classic(Set<String> someNames) {

		List<String> namesWithM = new ArrayList<String>();

		for (String element : someNames) {
			if (element.startsWith("m")) {
				namesWithM.add(element.replaceAll("marco_", ""));
			}
		}

		Collections.sort(namesWithM);

		StringBuilder commaSeparetedString = new StringBuilder();

		Iterator<String> namesWithMIterator = namesWithM.iterator();
		while (namesWithMIterator.hasNext()) {
			commaSeparetedString.append(namesWithMIterator.next());
			if (namesWithMIterator.hasNext()) {
				commaSeparetedString.append(",");
			}

		}

		return commaSeparetedString.toString();

	}

}



```