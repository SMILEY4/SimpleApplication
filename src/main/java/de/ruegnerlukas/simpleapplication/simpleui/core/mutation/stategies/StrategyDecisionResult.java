package de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class StrategyDecisionResult {


	/**
	 * Cached default result for the decision to not apply a mutation strategy.
	 */
	public static final StrategyDecisionResult NOT_APPLICABLE = new StrategyDecisionResult(false);

	/**
	 * Cached default result for the decision to apply a mutation strategy.
	 */
	public static final StrategyDecisionResult APPLICABLE_NO_EXTRA_DATA = new StrategyDecisionResult(true);

	/**
	 * Whether the mutation strategy can be used for the mutation.
	 */
	@Getter
	private final boolean applicable;

}
