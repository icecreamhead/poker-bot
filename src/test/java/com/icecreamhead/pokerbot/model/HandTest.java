package com.icecreamhead.pokerbot.model;

import org.junit.Test;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: joshcooke
 * Date: 06/04/2017
 * Time: 21:16
 */
public class HandTest {

    @Test
    public void isPair() throws Exception {
        assertThat(of("AH", "AC").isPair()).isTrue();
        assertThat(of("AH", "KC").isPair()).isFalse();
    }

    @Test
    public void isHolePair() throws Exception {
        assertThat(of("AH", "AC").isHolePair()).isTrue();
        assertThat(of("AH", "KC", "AD").isHolePair()).isFalse();
        assertThat(of("AH", "KC", "2D", "2S").isHolePair()).isFalse();
    }

    @Test
    public void isHiddenPair() throws Exception {
        assertThat(of("AH", "AC").isHiddenPair()).isFalse();
        assertThat(of("AH", "KC", "AD").isHiddenPair()).isTrue();
        assertThat(of("AH", "KC", "2D", "2S").isHiddenPair()).isFalse();
    }

    @Test
    public void isTwoPair() throws Exception {
        assertThat(of("AH", "AC").isTwoPair()).isFalse();
        assertThat(of("AH", "KC", "AD", "KD").isTwoPair()).isTrue();
        assertThat(of("AH", "KC", "2D", "2S").isTwoPair()).isFalse();
        assertThat(of("AH", "KC", "2D", "2S", "KS").isTwoPair()).isTrue();
        assertThat(of("AH", "KC", "2D", "2S", "3H", "3D").isTwoPair()).isTrue();
    }

    @Test
    public void isThreeOfAKind() throws Exception {
        assertThat(of("AH", "AC").isThreeOfAKind()).isFalse();
        assertThat(of("AH", "AC", "AD", "KD").isThreeOfAKind()).isTrue();
        assertThat(of("AH", "2C", "2D", "2S").isThreeOfAKind()).isTrue();
        assertThat(of("AH", "KC", "2D", "2S", "2C").isThreeOfAKind()).isTrue();
        assertThat(of("AH", "2C", "3D", "4S", "5H", "6D").isThreeOfAKind()).isFalse();
    }

    @Test
    public void isStraight() throws Exception {
        assertThat(of("AH", "AC").isStraight()).isFalse();
        assertThat(of("AH", "2C", "3D", "4D").isStraight()).isFalse();
        assertThat(of("AH", "2C", "3D", "4D", "5D").isStraight()).isTrue();
        assertThat(of("AH", "KC", "QD", "JD", "TD").isStraight()).isTrue();
        assertThat(of("AH", "2C", "3D", "4S", "7H", "6D", "8D").isStraight()).isFalse();
    }

    @Test
    public void isFlush() throws Exception {
        assertThat(of("AH", "AC").isFlush()).isFalse();
        assertThat(of("AH", "2C", "3D", "4D").isFlush()).isFalse();
        assertThat(of("AH", "2H", "7H", "4H", "5H").isFlush()).isTrue();
        assertThat(of("AD", "KC", "QD", "JD", "TD", "2D").isFlush()).isTrue();
        assertThat(of("AH", "2C", "3D", "4D", "7D", "6D", "8D").isFlush()).isTrue();
    }

    @Test
    public void isFullHouse() throws Exception {
        assertThat(of("AH", "AC").isFullHouse()).isFalse();
        assertThat(of("AH", "2C", "3D", "4D").isFullHouse()).isFalse();
        assertThat(of("AH", "AC", "JC", "JD", "JS").isFullHouse()).isTrue();
        assertThat(of("AH", "AC", "AS", "JD", "JS").isFullHouse()).isTrue();
        assertThat(of("AD", "KC", "AH", "KD", "TD", "AC").isFullHouse()).isTrue();
        assertThat(of("AH", "2C", "3D", "3C", "3H", "4H", "4D").isFullHouse()).isTrue();
    }

    @Test
    public void isFourOfAKind() throws Exception {
        assertThat(of("AH", "AC").isFourOfAKind()).isFalse();
        assertThat(of("AH", "2C", "3D", "4D").isFourOfAKind()).isFalse();
        assertThat(of("AH", "AC", "AD", "AS", "JS").isFourOfAKind()).isTrue();
        assertThat(of("AH", "2C", "AD", "AC", "AS").isFourOfAKind()).isTrue();
        assertThat(of("AD", "KC", "3H", "3C", "3D", "3S").isFourOfAKind()).isTrue();
        assertThat(of("AH", "2C", "3D", "3C", "3H", "4H", "4D").isFourOfAKind()).isFalse();
    }

    @Test
    public void isStraightFlush() throws Exception {

        assertThat(of("AH", "AC").isStraightFlush()).isFalse();
        assertThat(of("AH", "2C", "3D", "4D").isStraightFlush()).isFalse();
        assertThat(of("AH", "2C", "3D", "4D", "5D").isStraightFlush()).isFalse();
        assertThat(of("AH", "KC", "QD", "JD", "TD").isStraightFlush()).isFalse();
        assertThat(of("AH", "2C", "3D", "4S", "7H", "6D", "8D").isStraightFlush()).isFalse();

        assertThat(of("AH", "AC").isStraightFlush()).isFalse();
        assertThat(of("AH", "2C", "3D", "4D").isStraightFlush()).isFalse();
        assertThat(of("AH", "2H", "7H", "4H", "5H").isStraightFlush()).isFalse();
        assertThat(of("AD", "KC", "QD", "JD", "9D", "2D").isStraightFlush()).isFalse();
        assertThat(of("AH", "2C", "3D", "4D", "7D", "6D", "8D").isStraightFlush()).isFalse();
        assertThat(of("AD", "KD", "QD", "JD", "TD", "2S").isStraightFlush()).isTrue();

        assertThat(of("AD", "KC", "QD", "JD", "TD", "2D").isStraightFlush()).isFalse();
    }

    private static Card c(String cardString) {
        return new Card(cardString);
    }

    private static Hand of(String hole1, String hole2, String... shared) {
        return new Hand(c(hole1), c(hole2), Stream.of(shared).map(HandTest::c).collect(toList()));
    }
}