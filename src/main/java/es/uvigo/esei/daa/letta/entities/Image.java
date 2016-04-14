package es.uvigo.esei.daa.letta.entities;

public class Image implements Entity{
	public static enum ExtensionTypes{
		jpg,
		png,
		jpeg
	}
	
	private ExtensionTypes img_ext;
	private byte[] img;
	
	public Image(ExtensionTypes img_ext, byte[] img) {
		this.setImg_ext(img_ext);
		this.setImg(img);
	}

	public ExtensionTypes getImg_ext() {
		return img_ext;
	}

	public void setImg_ext(ExtensionTypes img_ext) {
		if(img_ext == null)
			throw new NullPointerException("Extension can't be null");
		this.img_ext = img_ext;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		if(img == null)
			throw new NullPointerException("Image array can't be null");
		this.img = img;
	}
	
	public boolean equals(Image img){
		return img.getImg().equals(this.img) && img.getImg_ext().equals(this.img_ext);
	}

}
