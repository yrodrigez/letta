package es.uvigo.esei.daa.letta.matchers;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;


import es.uvigo.esei.daa.letta.entities.Assist;


public class IsEqualToAssist extends IsEqualToEntity<Assist>{

    public IsEqualToAssist(Assist entity) {
        super(entity);
    }

    @Override
    protected boolean matchesSafely(Assist actual) {
        this.clearDescribeTo();

        if (actual == null) {
            this.addTemplatedDescription("actual", expected.toString());
            return false;
        } else {
            return checkAttribute("user_id", Assist::getUser_id, actual);
        }
    }

    /**
     * Factory method that creates a new {@link IsEqualToEntity} matcher with
     * the provided {@link Assist} as the expected value.
     *
     * @param assist the expected assist.
     * @return a new {@link IsEqualToEntity} matcher with the provided
     * {@link Assist} as the expected value.
     */
    @Factory
    public static IsEqualToAssist equalsToAssist(Assist assist) {
        return new IsEqualToAssist(assist);
    }

    /**
     * Factory method that returns a new {@link Matcher} that includes several
     * {@link IsEqualToAssist} matchers, each one using an {@link Assist} of the
     * provided ones as the expected value.
     *
     * @param assists the persons to be used as the expected values.
     * @return a new {@link Matcher} that includes several
     * {@link IsEqualToAssist} matchers, each one using an {@link Assist} of the
     * provided ones as the expected value.
     * @see IsEqualToEntity#containsEntityInAnyOrder(java.util.function.Function, Object...)
     */
    @Factory
    public static Matcher<Iterable<? extends Assist>> containsAssistsInAnyOrder(Assist ... assists) {
        return containsEntityInAnyOrder(IsEqualToAssist::equalsToAssist, assists);
    }

}
