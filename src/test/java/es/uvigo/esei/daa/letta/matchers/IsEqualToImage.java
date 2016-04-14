package es.uvigo.esei.daa.letta.matchers;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import es.uvigo.esei.daa.letta.entities.Image;

public class IsEqualToImage extends IsEqualToEntity<Image> {
	public IsEqualToImage(Image entity) {
		super(entity);
	}

	@Override
	protected boolean matchesSafely(Image actual) {
		this.clearDescribeTo();
		
		if (actual == null) {
			this.addTemplatedDescription("actual", expected.toString());
			return false;
		} else {
			return checkAttribute("img", Image::getImg, actual) &&
					checkAttribute("img_ext", Image::getImg_ext, actual);
		}
	}

	/**
	 * Factory method that creates a new {@link IsEqualToEntity} matcher with
	 * the provided {@link Person} as the expected value.
	 * 
	 * @param person the expected person.
	 * @return a new {@link IsEqualToEntity} matcher with the provided
	 * {@link Person} as the expected value.
	 */
	@Factory
	public static IsEqualToImage equalsToImage(Image Image) {
		return new IsEqualToImage(Image);
	}
	
	/**
	 * Factory method that returns a new {@link Matcher} that includes several
	 * {@link IsEqualToUser} matchers, each one using an {@link Person} of the
	 * provided ones as the expected value.
	 * 
	 * @param persons the persons to be used as the expected values.
	 * @return a new {@link Matcher} that includes several
	 * {@link IsEqualToUser} matchers, each one using an {@link Person} of the
	 * provided ones as the expected value.
	 * @see IsEqualToEntity#containsEntityInAnyOrder(java.util.function.Function, Object...)
	 */
	@Factory
	public static Matcher<Iterable<? extends Image>> containsImagesInAnyOrder(Image ... Images) {
		return containsEntityInAnyOrder(IsEqualToImage::equalsToImage, Images);
	}

}
