package de.ruegnerlukas.simpleapplication.simpleui.core.tags;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TagsFilterTest {


	@Test
	public void test_filter_expression_contains() {
		assertThat(Tags.contains("tag1").matches(createTags())).isTrue();
		assertThat(Tags.contains("nope").matches(createTags())).isFalse();
	}




	@Test
	public void test_filter_expression_containsAll() {
		assertThat(Tags.containsAll("tag1", "tag2").matches(createTags())).isTrue();
		assertThat(Tags.containsAll("tag1", "nope").matches(createTags())).isFalse();
	}




	@Test
	public void test_filter_expression_containsAny() {
		assertThat(Tags.containsAny("tag1", "tag2").matches(createTags())).isTrue();
		assertThat(Tags.containsAny("tag1", "nope").matches(createTags())).isTrue();
		assertThat(Tags.containsAny("nope", "fail").matches(createTags())).isFalse();
	}




	@Test
	public void test_filter_expression_or() {
		assertThat(Tags.or(Tags.contains("tag1"), Tags.contains("tag2")).matches(createTags())).isTrue();
		assertThat(Tags.or(Tags.contains("tag1"), Tags.contains("nope")).matches(createTags())).isTrue();
		assertThat(Tags.or(Tags.contains("nope"), Tags.contains("fail")).matches(createTags())).isFalse();
		assertThat(Tags.or(Tags.contains("tag1")).matches(createTags())).isTrue();
		assertThat(Tags.or(Tags.contains("nope")).matches(createTags())).isFalse();
		assertThat(Tags.or().matches(createTags())).isFalse();
	}




	@Test
	public void test_filter_expression_and() {
		assertThat(Tags.and(Tags.contains("tag1"), Tags.contains("tag2")).matches(createTags())).isTrue();
		assertThat(Tags.and(Tags.contains("tag1"), Tags.contains("nope")).matches(createTags())).isFalse();
		assertThat(Tags.and(Tags.contains("nope"), Tags.contains("fail")).matches(createTags())).isFalse();
		assertThat(Tags.and(Tags.contains("tag1")).matches(createTags())).isTrue();
		assertThat(Tags.and(Tags.contains("nope")).matches(createTags())).isFalse();
		assertThat(Tags.and().matches(createTags())).isFalse();
	}




	@Test
	public void test_filter_expression_not() {
		assertThat(Tags.not(Tags.contains("tag1")).matches(createTags())).isFalse();
		assertThat(Tags.not(Tags.contains("nope")).matches(createTags())).isTrue();
	}




	@Test
	public void test_filter_expression_constant_true() {
		assertThat(Tags.constant(true).matches(createTags())).isTrue();
	}




	@Test
	public void test_filter_expression_constant_false() {
		assertThat(Tags.constant(false).matches(createTags())).isFalse();
	}




	private Tags createTags() {
		return Tags.from("tag0", "tag1", "tag2");
	}


}
