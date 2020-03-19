package de.ruegnerlukas.simpleapplication.core.events;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChannelTest {


	@Test
	public void testEqualityName() {
		Channel channel1A = Channel.name("test 1");
		Channel channel1B = Channel.name("test 1");
		assertThat(channel1A.equals(channel1B)).isTrue();
		assertThat(channel1B.equals(channel1A)).isTrue();
		assertThat(channel1A.hashCode()).isEqualTo(channel1B.hashCode());

		Channel channel2A = Channel.name("test 2 a");
		Channel channel2B = Channel.name("test 2 b");
		assertThat(channel2A.equals(channel2B)).isFalse();
		assertThat(channel2B.equals(channel2A)).isFalse();
		assertThat(channel2A.hashCode()).isNotEqualTo(channel2B.hashCode());
	}




	@Test
	public void testEqualityType() {
		Channel channel1A = Channel.type(TypeA.class);
		Channel channel1B = Channel.type(TypeA.class);
		assertThat(channel1A.equals(channel1B)).isTrue();
		assertThat(channel1B.equals(channel1A)).isTrue();
		assertThat(channel1A.hashCode()).isEqualTo(channel1B.hashCode());

		Channel channel2A = Channel.type(TypeA.class);
		Channel channel2B = Channel.type(TypeB.class);
		assertThat(channel2A.equals(channel2B)).isFalse();
		assertThat(channel2B.equals(channel2A)).isFalse();
		assertThat(channel2A.hashCode()).isNotEqualTo(channel2B.hashCode());
	}




	@Test
	public void testInequality() {
		Channel channelName = Channel.name("name");
		Channel channelType = Channel.type(TypeA.class);
		assertThat(channelName.equals(channelType)).isFalse();
		assertThat(channelType.equals(channelName)).isFalse();
		assertThat(channelName.hashCode()).isNotEqualTo(channelType.hashCode());
	}






	private static class TypeA {


	}






	private static class TypeB {


	}

}
