import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Renders text from a fixed-width bitmap font stored in a sprite sheet.
 */
public final class PixelFont {
    private final BufferedImage atlas;
    private final String[] rowCharacters;
    private final int x;
    private final int y;
    private final int glyphWidth;
    private final int glyphHeight;
    private final int lineHeight;
    private final Color transparentColor;

    public PixelFont(BufferedImage atlas, int x, int y, int glyphWidth,
                     int glyphHeight, int lineHeight, String... rowCharacters) {
        this.atlas = Objects.requireNonNull(atlas, "atlas");
        this.x = x;
        this.y = y;
        this.glyphWidth = requirePositive(glyphWidth, "glyphWidth");
        this.glyphHeight = requirePositive(glyphHeight, "glyphHeight");
        this.lineHeight = requirePositive(lineHeight, "lineHeight");
        this.rowCharacters = Objects.requireNonNull(rowCharacters, "rowCharacters").clone();
        if (rowCharacters.length == 0) {
            throw new IllegalArgumentException("At least one font row is required");
        }
        for (String row : rowCharacters) {
            if (row == null || row.isEmpty()) {
                throw new IllegalArgumentException("Font rows cannot be empty");
            }
        }
        transparentColor = new Color(atlas.getRGB(x, y), true);
    }

    public BufferedImage render(String text) {
        return render(text, 1, Color.WHITE);
    }

    public BufferedImage render(String text, int scale, Color ink) {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(ink, "ink");
        if (scale < 1) {
            throw new IllegalArgumentException("scale must be at least 1");
        }

        String[] lines = text.split("\\R", -1);
        int width = 0;
        for (String line : lines) {
            width = Math.max(width, line.length() * glyphWidth);
        }
        BufferedImage result = new BufferedImage(
                Math.max(1, width * scale),
                Math.max(1, lines.length * lineHeight * scale),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = result.createGraphics();
        for (int line = 0; line < lines.length; line++) {
            for (int column = 0; column < lines[line].length(); column++) {
                drawGlyph(graphics, lines[line].charAt(column), column * glyphWidth * scale,
                        line * lineHeight * scale, scale, ink);
            }
        }
        graphics.dispose();
        return result;
    }

    public Image renderImage(String text, int scale, Color ink) {
        return render(text, scale, ink);
    }

    private void drawGlyph(Graphics2D graphics, char character, int destinationX,
                           int destinationY, int scale, Color ink) {
        int sourceRow = -1;
        int sourceColumn = -1;
        for (int row = 0; row < rowCharacters.length && sourceRow < 0; row++) {
            sourceColumn = rowCharacters[row].indexOf(character);
            if (sourceColumn >= 0) {
                sourceRow = row;
            }
        }
        if (sourceRow < 0) {
            return;
        }

        for (int pixelY = 0; pixelY < glyphHeight; pixelY++) {
            for (int pixelX = 0; pixelX < glyphWidth; pixelX++) {
                int rgb = atlas.getRGB(x + sourceColumn * glyphWidth + pixelX,
                        y + sourceRow * lineHeight + pixelY);
                if (rgb != transparentColor.getRGB()) {
                    graphics.setColor(ink);
                    graphics.fillRect(destinationX + pixelX * scale,
                            destinationY + pixelY * scale, scale, scale);
                }
            }
        }
    }

    private static int requirePositive(int value, String name) {
        if (value < 1) {
            throw new IllegalArgumentException(name + " must be positive");
        }
        return value;
    }
}
