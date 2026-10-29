import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import javax.imageio.ImageIO;

/**
 * Access point for the assets in src/sprites/battleHub.png.
 */
public final class BattleHubAssets {
    private static final String[] FONT_ROWS = {
            "/123456789",
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ",
            "abcdefghijklmnopqrstuvwxyz",
            "!?:;.,'-+*/()[]"
    };

    private final BufferedImage sheet;

    private BattleHubAssets(BufferedImage sheet) {
        this.sheet = sheet;
    }

    public static BattleHubAssets loadDefault() throws IOException {
        InputStream resource = BattleHubAssets.class.getResourceAsStream("/sprites/battleHub.png");
        if (resource != null) {
            try (resource) {
                return new BattleHubAssets(requireImage(ImageIO.read(resource)));
            }
        }
        return load(Path.of("src", "sprites", "battleHub.png"));
    }

    public static BattleHubAssets load(Path path) throws IOException {
        Objects.requireNonNull(path, "path");
        return new BattleHubAssets(requireImage(ImageIO.read(Files.newInputStream(path))));
    }

    public BufferedImage sheet() {
        return sheet;
    }

    /**
     * The font in the sheet starts at (516, 86), with 6x8 glyphs.
     * Coordinates can be supplied to support a different revision of the sheet.
     */
    public PixelFont font() {
        return font(516, 86, 6, 8, 8, FONT_ROWS);
    }

    public PixelFont font(int x, int y, int glyphWidth, int glyphHeight,
                          int lineHeight, String... rows) {
        return new PixelFont(sheet, x, y, glyphWidth, glyphHeight, lineHeight, rows);
    }

    /**
     * Colors sampled from the green/yellow/red HP-bar sprites in the sheet.
     */
    public HpBarPalette hpBarPalette() {
        return new HpBarPalette(
                new Color(sheet.getRGB(690, 34), true),
                new Color(sheet.getRGB(690, 51), true),
                new Color(sheet.getRGB(690, 63), true),
                new Color(sheet.getRGB(620, 31), true));
    }

    private static BufferedImage requireImage(BufferedImage image) throws IOException {
        if (image == null) {
            throw new IOException("The battle hub is not a readable PNG image");
        }
        return image;
    }

    public record HpBarPalette(Color green, Color yellow, Color red, Color border) {
        public HpBarPalette {
            Objects.requireNonNull(green, "green");
            Objects.requireNonNull(yellow, "yellow");
            Objects.requireNonNull(red, "red");
            Objects.requireNonNull(border, "border");
        }
    }
}
