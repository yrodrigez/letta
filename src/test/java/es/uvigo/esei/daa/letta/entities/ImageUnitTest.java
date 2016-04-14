package es.uvigo.esei.daa.letta.entities;

import org.junit.Test;

import es.uvigo.esei.daa.letta.entities.Image.ExtensionTypes;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class ImageUnitTest {
	
	@Test
	public void testExtensionTypesByteArray(){
		ExtensionTypes ext = ExtensionTypes.jpeg;
		byte[] img = (new String("Hola")).getBytes();
		
		Image i = new Image(ext, img);
		
		assertThat(i.getImg(), is(equalTo(img)));
		assertThat(i.getImg_ext(), is(equalTo(ext)));
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullByteArray(){
		ExtensionTypes ext = null;
		byte[] img = (new String("Hola")).getBytes();
		
		Image i = new Image(ext, img);
	}
	
	@Test(expected = NullPointerException.class)
	public void testExtensionTypesNull(){
		ExtensionTypes ext = ExtensionTypes.jpeg;
		byte[] img = null;
		
		Image i = new Image(ext, img);
	}
	
	@Test
	public void testSetImg_ext(){
		ExtensionTypes ext = ExtensionTypes.png;
		byte[] img = (new String("Hola")).getBytes();
		
		Image i = new Image(ExtensionTypes.jpeg, img);
		
		i.setImg_ext(ExtensionTypes.png);
		
		assertThat(i.getImg_ext(), is(equalTo(ext)));
		
	}
	
	@Test
	public void testSetImg(){
		ExtensionTypes ext = ExtensionTypes.png;
		byte[] img = (new String("Hola")).getBytes();
		
		Image i = new Image(ext, (new String("Adios")).getBytes());
		
		i.setImg(img);
		
		assertThat(i.getImg(), is(equalTo(img)));
		
	}

}
