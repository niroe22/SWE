package artcreator.creator.impl;

import artcreator.domain.port.Profile;
import artcreator.domain.port.Domain;
import artcreator.domain.port.Template;
import artcreator.statemachine.port.Observer;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.State.S;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CreatorImpl implements Observer {

	private final Domain domain;

	public CreatorImpl(Domain domain) {
		this.domain = domain;
	}

	public void setImage(String imagePath) {
		try{
			BufferedImage image = ImageIO.read(new File(imagePath));
			domain.setImage(image);
		} catch (IOException e) {
			System.err.println("Error while reading image " + imagePath);
		}
	}

	public void updateProfile(Profile profile){
		domain.setProfile(profile);
	}

	private void createTemplate(Profile profile, BufferedImage image){
		BufferedImage transformedImage = rotateImage(image, profile.getRotation());
		transformedImage = resizeImage(transformedImage, mmToPixels(profile.getWidth()), mmToPixels(profile.getHeight()));

		int gridSize = mmToPixels(profile.getGranularity());
		int gridWidth = transformedImage.getWidth()/gridSize;
		int gridHeight = transformedImage.getWidth()/gridSize;

		Color[][] grid = new Color[gridWidth][gridHeight];

		for(int x = 0; x < gridWidth; x++){
			for(int y = 0; y < gridHeight; y++){
				grid[x][y] = getAverageColor(image, x, y, gridSize, gridSize);
			}
		}

		domain.setTemplate(new Template(grid));
	}

	private static Color getAverageColor(BufferedImage image, int startX, int startY, int cellWidth, int cellHeight) {
		int endX = Math.min(startX + cellWidth, image.getWidth());
		int endY = Math.min(startY + cellHeight, image.getHeight());
		long sumRed = 0, sumGreen = 0, sumBlue = 0;
		int count = 0;

		for (int y = startY; y < endY; y++) {
			for (int x = startX; x < endX; x++) {
				Color color = new Color(image.getRGB(x, y));
				sumRed += color.getRed();
				sumGreen += color.getGreen();
				sumBlue += color.getBlue();
				count++;
			}
		}

		int avgRed = (int) (sumRed / count);
		int avgGreen = (int) (sumGreen / count);
		int avgBlue = (int) (sumBlue / count);

		return ColorMatcher.findClosestColor(new Color(avgRed, avgGreen, avgBlue));
	}

	private static BufferedImage rotateImage(BufferedImage originalImage, double angle) {
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		// Erstellen eines neuen Bildes mit vertauschten Breiten- und Höhenwerten
		BufferedImage rotatedImage = new BufferedImage(width ,height, originalImage.getType());

		// AffineTransform zum Rotieren des Bildes
		AffineTransform transform = new AffineTransform();
		transform.translate(width / 2.0, height / 2.0); // Zentrieren
		transform.rotate(Math.toRadians(angle)); // Rotieren
		transform.translate(-height / 2.0, -width / 2.0); // Verschiebung zurück

		// Rotiertes Bild zeichnen
		Graphics2D g2d = rotatedImage.createGraphics();
		g2d.setTransform(transform);
		g2d.drawImage(originalImage, 0, 0, null);
		g2d.dispose();

		return rotatedImage;
	}

	private static int mmToPixels(double mm) {
		double inches = mm / 25.4;
		return (int) Math.round(inches * 600);
	}

	private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
		int originalWidth = originalImage.getWidth();
		int originalHeight = originalImage.getHeight();

		// Berechnung des Skalierungsverhältnisses
		double widthRatio = (double) targetWidth / originalWidth;
		double heightRatio = (double) targetHeight / originalHeight;
		double scaleFactor = Math.min(widthRatio, heightRatio);

		// Berechnung der neuen Dimensionen
		int newWidth = (int) (originalWidth * scaleFactor);
		int newHeight = (int) (originalHeight * scaleFactor);

		// Erstellen des neuen skalierten Bildes
		BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
		Graphics2D g2d = resizedImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
		g2d.dispose();

		return resizedImage;
	}

	@Override
	public void update(State currentState) {
		if (currentState.isSubStateOf(S.PROFILE_UPDATE) ||
			currentState.isSubStateOf(S.IMAGE_LOADED)){
			this.createTemplate(domain.getProfile(), domain.getImage());
		}
	}
}
