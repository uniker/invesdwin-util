package de.invesdwin.util.lang;

import javax.annotation.concurrent.Immutable;

import org.junit.Test;

import de.invesdwin.util.assertions.Assertions;

@Immutable
public class ReflectionsTest {

    @Test
    public void testClassExists() {
        Assertions.assertThat(Reflections.classExists(this.getClass().getName())).isTrue();
    }

}
