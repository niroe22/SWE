package artcreator.creator.impl;

import artcreator.domain.port.Profile;
import artcreator.domain.port.Domain;
import artcreator.domain.port.Template;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.Observer;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.State.S;
import artcreator.statemachine.port.Subject;

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
        Subject subject = StateMachineFactory.FACTORY.subject();
        subject.attach(this);
    }

    public void setImage(File imageFile) {
        try {
            BufferedImage image = ImageIO.read(imageFile);
            domain.setImage(image);
        } catch (IOException e) {
            System.err.println("Error while reading image " + imageFile.getName());
        }
    }

    public void updateProfile(Profile profile) {
        domain.setProfile(profile);
    }

    public void createTemplate() {
        Profile profile = domain.getProfile();
        BufferedImage image=domain.getImage();

        int gridSize = (int) ((double) Math.min(image.getWidth(), image.getHeight()) * (double) profile.getGranularity() / (double) Math.min(profile.getWidth(), profile.getLength()));
        int gridWidth = image.getWidth() / gridSize;
        int gridHeight = image.getHeight() / gridSize;

        Color[][] grid = new Color[gridWidth][gridHeight];

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                grid[x][y] = getAverageColor(image, x * gridSize, y * gridSize, gridSize);

            }
        }

        switch (profile.getRotation()) {
            case 270:
                grid = rotate90(grid);
            case 180:
                grid = rotate90(grid);
            case 90:
                grid = rotate90(grid);
                break;
        }

        domain.setTemplate(new Template(grid));
    }

    private static Color getAverageColor(BufferedImage image, int startX, int startY, int cellSize) {

        int endX = Math.min(startX + cellSize, image.getWidth());
        int endY = Math.min(startY + cellSize, image.getHeight());
        long sumRed = 0, sumGreen = 0, sumBlue = 0, sumAlpha = 0;
        int count = 0;

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                Color color = new Color(image.getRGB(x, y));
                sumRed += color.getRed();
                sumGreen += color.getGreen();
                sumBlue += color.getBlue();
                sumAlpha += color.getAlpha();
                count++;
            }
        }

        int avgRed = (int) (sumRed / count);
        int avgGreen = (int) (sumGreen / count);
        int avgBlue = (int) (sumBlue / count);
        int avgAlpha = (int) (sumAlpha / count);

        return ColorMatcher.findClosestColor(new Color(avgRed, avgGreen, avgBlue, avgAlpha));
    }

    private static Color[][] rotate90(Color[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        Color[][] rotatedGrid = new Color[cols][rows];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rotatedGrid[cols - 1 - c][r] = grid[r][c];
            }
        }

        return rotatedGrid;
    }

    public static BufferedImage scaleToFitAspectRatio(BufferedImage originalImage, int targetWidth, int targetHeight) {
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();

        // Berechne das neue Seitenverhältnis
        double aspectRatio = (double) targetWidth / targetHeight;

        // Berechne die neuen Dimensionen
        double scalingFactor = Math.min((double) targetWidth / imageWidth, (double) targetHeight / imageHeight);
        int newWidth = (int) (imageWidth * scalingFactor);
        int newHeight = (int) (imageHeight * scalingFactor);

        // Erzeuge ein neues Bild mit den neuen Dimensionen
        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, originalImage.getType());

        // Zeichne das skalierte Bild
        Graphics2D g2d = scaledImage.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance(scalingFactor, scalingFactor);
        g2d.drawRenderedImage(originalImage, at);
        g2d.dispose();

        return scaledImage;
    }

    @Override
    public void update(State currentState) {
        if (currentState.isSubStateOf(S.IMAGE_LOADED) ||
                currentState.isSubStateOf(S.PROFILE_UPDATED)) {
            this.createTemplate();
        }
    }
}
