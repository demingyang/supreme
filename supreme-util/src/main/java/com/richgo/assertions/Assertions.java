package com.richgo.assertions;

/**
 * <p>
 * Design by contract assertions.
 * </p>
 * <p>
 * This class is not part of the public API and may be removed or changed at any
 * time.
 * </p>
 */
public final class Assertions {

	//zs测试提交
	private Assertions() {
	}

	/**
	 * Throw IllegalArgumentException if the value is null.
	 *
	 * @param name
	 *            the parameter name
	 * @param value
	 *            the value that should not be null
	 * @param <T>
	 *            the value type
	 * @return the value
	 * @throws java.lang.IllegalArgumentException
	 *             if value is null
	 */
	public static <T> T notNull(final String name, final T value) {
		if (value == null) {
			throw new IllegalArgumentException(name + " can not be null");
		}
		return value;
	}

	/**
	 * 
	 *  Throw IllegalArgumentException if the value is null or "" .
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public static String notEmpty(final String name, String value) {
		if (value == null || value.length() == 0) {
			throw new IllegalArgumentException(name + " can not be Empty");
		}
		return value;
	}

	/**
	 * Throw IllegalStateException if the condition if false.
	 *
	 * @param name
	 *            the name of the state that is being checked
	 * @param condition
	 *            the condition about the parameter to check
	 * @throws java.lang.IllegalStateException
	 *             if the condition is false
	 */
	public static void isTrue(final String name, final boolean condition) {
		if (!condition) {
			throw new IllegalStateException("state should be: " + name);
		}
	}

	/**
	 * Throw IllegalArgumentException if the condition if false.
	 *
	 * @param name
	 *            the name of the state that is being checked
	 * @param condition
	 *            the condition about the parameter to check
	 * @throws java.lang.IllegalArgumentException
	 *             if the condition is false
	 */
	public static void isTrueArgument(final String name, final boolean condition) {
		if (!condition) {
			throw new IllegalArgumentException("state should be: " + name);
		}
	}

	// public static void main(String[] arg) {
	// isTrueArgument("geometries contains only non-null elements", false);
	// }
}
