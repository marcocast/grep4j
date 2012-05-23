package org.grep4j.core.aggregator;

import java.util.Iterator;

import ch.lambdaj.function.aggregate.Aggregator;

public class AggregateMultitokensExpression implements Aggregator<String> {

	public static final String MULITIPLE_TOKEN_DELIMITER = "'";

	@Override
	public String aggregate(Iterator<? extends String> iterator) {
		String expression = "";
		if (iterator == null)
			return "";
		StringBuilder expressionBuilder = new StringBuilder();
		while (iterator.hasNext()) {
			String item = iterator.next();
			if (item == null)
				continue;
			expressionBuilder.append(item);
			expressionBuilder.append(" ");
			if (item.endsWith(MULITIPLE_TOKEN_DELIMITER)) {
				expression = expressionBuilder.toString();
				break;
			}
		}
		return expression;
	}

}