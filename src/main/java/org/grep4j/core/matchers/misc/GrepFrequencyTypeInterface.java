package org.grep4j.core.matchers.misc;

/**
 * Basic interface for comparing a frequency of times against a threshold
 * 
 * @author Giovanni Gargiulo
 *
 */
public interface GrepFrequencyTypeInterface {
	
	boolean valuate(int value, int threshold);

}
