package de.ruegnerlukas.simpleapplication.simpleui.core.tags;

import lombok.AllArgsConstructor;

import java.util.Set;

public abstract class TagConditionExpression {


	/**
	 * @param tags the input tags
	 * @return whether this condition matches the input tags
	 */
	public abstract boolean matches(Tags tags);


	@Override
	public abstract boolean equals(Object obj);




	@AllArgsConstructor
	public static class ContainsConditionExpression extends TagConditionExpression {


		/**
		 * The tag that must be contained in the list of tags.
		 */
		private final String tag;




		@Override
		public boolean matches(final Tags tags) {
			return tags.getTags().contains(this.tag);
		}




		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof ContainsConditionExpression) {
				final ContainsConditionExpression other = (ContainsConditionExpression) obj;
				return this.tag.equals(other.tag);
			} else {
				return false;
			}
		}




		@Override
		public int hashCode() {
			return super.hashCode();
		}

	}






	@AllArgsConstructor
	public static class ContainsAllConditionExpression extends TagConditionExpression {


		/**
		 * The tags that must be contained in the list of tags.
		 */
		private final Set<String> tags;




		@Override
		public boolean matches(final Tags tags) {
			return tags.getTags().containsAll(this.tags);
		}




		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof ContainsAllConditionExpression) {
				final ContainsAllConditionExpression other = (ContainsAllConditionExpression) obj;
				return this.tags.equals(other.tags);
			} else {
				return false;
			}
		}




		@Override
		public int hashCode() {
			return super.hashCode();
		}

	}






	@AllArgsConstructor
	public static class ContainsAnyConditionExpression extends TagConditionExpression {


		/**
		 * The tags. At least one of them must be in the list of tags.
		 */
		private final Set<String> tags;




		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof ContainsAnyConditionExpression) {
				final ContainsAnyConditionExpression other = (ContainsAnyConditionExpression) obj;
				return this.tags.equals(other.tags);
			} else {
				return false;
			}
		}




		@Override
		public boolean matches(final Tags tags) {
			return this.tags.stream().anyMatch(tag -> tags.getTags().contains(tag));
		}




		@Override
		public int hashCode() {
			return super.hashCode();
		}

	}






	@AllArgsConstructor
	public static class OrConditionExpression extends TagConditionExpression {


		/**
		 * The expressions to combine with 'or'.
		 */
		private final Set<TagConditionExpression> expressions;




		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof OrConditionExpression) {
				final OrConditionExpression other = (OrConditionExpression) obj;
				return this.expressions.equals(other.expressions);
			} else {
				return false;
			}
		}




		@Override
		public boolean matches(final Tags tags) {
			return expressions.stream().anyMatch(expr -> expr.matches(tags));
		}




		@Override
		public int hashCode() {
			return super.hashCode();
		}

	}






	@AllArgsConstructor
	public static class AndConditionExpression extends TagConditionExpression {


		/**
		 * The expressions to combine with 'and'.
		 */
		private final Set<TagConditionExpression> expressions;




		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof AndConditionExpression) {
				final AndConditionExpression other = (AndConditionExpression) obj;
				return this.expressions.equals(other.expressions);
			} else {
				return false;
			}
		}




		@Override
		public boolean matches(final Tags tags) {
			return !expressions.isEmpty() && expressions.stream().allMatch(expr -> expr.matches(tags));
		}




		@Override
		public int hashCode() {
			return super.hashCode();
		}

	}






	@AllArgsConstructor
	public static class NotConditionExpression extends TagConditionExpression {


		/**
		 * The expressions to invert.
		 */
		private final TagConditionExpression expression;




		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof NotConditionExpression) {
				final NotConditionExpression other = (NotConditionExpression) obj;
				return this.expression.equals(other.expression);
			} else {
				return false;
			}
		}




		@Override
		public boolean matches(final Tags tags) {
			return !expression.matches(tags);
		}




		@Override
		public int hashCode() {
			return super.hashCode();
		}

	}






	@AllArgsConstructor
	public static class ConstantConditionExpression extends TagConditionExpression {


		/**
		 * An expression that always evaluates to true.
		 */
		public static final ConstantConditionExpression TRUE = new ConstantConditionExpression(true);

		/**
		 * An expression that always evaluates to false.
		 */
		public static final ConstantConditionExpression FALSE = new ConstantConditionExpression(false);

		/**
		 * The constant result of this expression independent of the input tags.
		 */
		private final boolean result;




		@Override
		public boolean matches(final Tags tags) {
			return result;
		}




		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof ConstantConditionExpression) {
				final ConstantConditionExpression other = (ConstantConditionExpression) obj;
				return this.result == other.result;
			} else {
				return false;
			}
		}




		@Override
		public int hashCode() {
			return super.hashCode();
		}

	}


}
