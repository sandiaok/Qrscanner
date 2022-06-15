package com.example.springboot.qr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import io.nayuki.qrcodegen.QrCode;

public class QrGen {
	
	
	public static void main(String[] args) throws IOException {
		doBasicDemo();
	}
	

	private static BufferedImage toImage(QrCode qr, int scale, int border, int lightColor, int darkColor) {
		Objects.requireNonNull(qr);
		if (scale <= 0 || border < 0)
			throw new IllegalArgumentException("Value out of range");
		if (border > Integer.MAX_VALUE / 2 || qr.size + border * 2L > Integer.MAX_VALUE / scale)
			throw new IllegalArgumentException("Scale or border too large");

		BufferedImage result = new BufferedImage((qr.size + border * 2) * scale, (qr.size + border * 2) * scale,
				BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < result.getHeight(); y++) {
			for (int x = 0; x < result.getWidth(); x++) {
				boolean color = qr.getModule(x / scale - border, y / scale - border);
				result.setRGB(x, y, color ? darkColor : lightColor);
			}
		}
		return result;
	}


	private static void doBasicDemo() throws IOException {
		String text = "wg gbggbgb66663tfgnuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuua";
		QrCode.Ecc errCorLvl = QrCode.Ecc.LOW;

		QrCode qr = QrCode.encodeText(text, errCorLvl);

		BufferedImage img = toImage(qr, 10, 4);
		File imgFile = new File("hello-world-QR.png");
		ImageIO.write(img, "png", imgFile);

	}

	private static BufferedImage toImage(QrCode qr, int scale, int border) {
		return toImage(qr, scale, border, 0xFFFFFF, 0x000000);
	}

}
