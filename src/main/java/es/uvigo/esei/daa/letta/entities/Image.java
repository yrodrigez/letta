package es.uvigo.esei.daa.letta.entities;

public class Image implements Entity{
	private String img_ext;
	private byte[] img;
	
	public Image(String img_ext, byte[] img) {
		this.img_ext = img_ext;
		this.img = img;
	}

	public String getImg_ext() {
		return img_ext;
	}

	public void setImg_ext(String img_ext) {
		this.img_ext = img_ext;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

}
