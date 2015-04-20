
```
package com.marco.test;

import java.util.Comparator;

public class ABeanComparator implements Comparator<ABean> {

	public int compare(ABean o1, ABean o2) {
		if (o1.getStringValue().length() > o1.getStringValue().length()) {
			return 1;
		} else if (o1.getStringValue().length() < o1.getStringValue().length()) {
			return 1;
		} else {
			return 0;
		}
	}
}
```