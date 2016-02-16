/*
 * Copyright (c) 2015 SUN XIMENG (Nathaniel). All rights reserved.
 */

package io.bretty.console.table;

/**
 * The {@code Alignment} enum represents the alignment options of 
 * a {@code String} with a given width
 * @author Nathaniel
 *
 */

public enum Alignment {
	/**
	 * The left alignment
	 */
	LEFT {
		@Override
		public String format(String input, int width, char pad) {
			if (width < input.length()) {
				return input.substring(0, width);
			} else if (width == input.length()) {
				return input;
			} else {
				return Position.AFTER.pad(input, pad, width - input.length());
			}
		}
	},
	/**
	 * The right alignment
	 */
	RIGHT {
		@Override
		public String format(String input, int width, char pad) {
			if (width < input.length()) {
				return input.substring(input.length() - width, input.length());
			} else if (width == input.length()) {
				return input;
			} else {
				return Position.BEFORE.pad(input, pad, width - input.length());
			}
		}
	},
	/**
	 * The center alignment
	 */
	CENTER {
		@Override
		public String format(String input, int width, char pad) {
			if (width < input.length()) {
				int redun = (input.length() - width) / 2;
				return input.substring(redun, input.length() - redun);
			} else if (input.length() == width) {
				return input;
			} else {
				int firstHalf = (width - input.length()) / 2;
				int secondHalf = width - input.length() - firstHalf;
				input = Position.BEFORE.pad(input, pad, firstHalf);
				return Position.AFTER.pad(input, pad, secondHalf);
			}
		}
	};

	enum Position {
		BEFORE {
			@Override
			protected String pad(String input, char p, int count) {
				StringBuilder sb = new StringBuilder(input.length() + count);
				sb = append(sb, p, count);
				sb.append(input);
				return sb.toString();
			}
		},

		AFTER {
			@Override
			protected String pad(String input, char p, int count) {
				StringBuilder sb = new StringBuilder(input.length() + count);
				sb.append(input);
				sb = append(sb, p, count);
				return sb.toString();
			}
		};

		protected static StringBuilder append(StringBuilder sb, char p,
				int count) {
			for (int i = 0; i < count; ++i) {
				sb.append(p);
			}
			return sb;
		}

		protected abstract String pad(String input, char p, int count);
	}

	/**
	 * Format a String as the given alignment.
	 * If the input String is longer than the result width, it will be truncated.
	 * If the input String is shorter than the result width, char p will be used for padding.
	 * @param input The string to be formatted
	 * @param width The width of the result String
	 * @param pad Used for padding
	 * @return The result String
	 */
	public abstract String format(String input, int width, char pad);
}