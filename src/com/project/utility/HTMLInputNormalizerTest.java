package com.project.utility;

import static org.junit.Assert.*;

import org.junit.Test;

public class HTMLInputNormalizerTest {

	@Test
	public void testReplaceLinks_onlyOneLabel() {
		assertEquals("<input name=\"toto\"/>", HTMLInputNormalizer.normalize("<input name=\"toto\">")); 
	}

}
