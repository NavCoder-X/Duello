import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Objects;
import javax.swing.JComponent;

/**
 * Pixel-style HP bar driven by absolute HP values.
 */
public final class SpriteHpBar extends JComponent {
    private final int maxHp;
    private final BattleHubAssets.HpBarPalette palette;
    private int hp;

    public SpriteHpBar(int maxHp, BattleHubAssets.HpBarPalette palette) {
        if (maxHp < 1) {
            throw new IllegalArgumentException("maxHp must be positive");
        }
        this.maxHp = maxHp;
        this.palette = Objects.requireNonNull(palette, "palette");
        hp = maxHp;
        setOpaque(false);
        setPreferredSize(new Dimension(124, 9));
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setHp(int hp) {
        int clamped = Math.max(0, Math.min(maxHp, hp));
        if (this.hp != clamped) {
            this.hp = clamped;
            repaint();
        }
    }

    public void damage(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount cannot be negative");
        }
        setHp(hp - amount);
    }

    public void heal(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount cannot be negative");
        }
        setHp(hp + amount);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        int width = getWidth();
        int height = getHeight();
        g.setColor(palette.border());
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, Math.max(0, width - 2), Math.max(0, height - 2));
        int fillWidth = Math.round((width - 4) * (hp / (float) maxHp));
        if (fillWidth > 0) {
            g.setColor(fillColor());
            g.fillRect(2, 2, fillWidth, Math.max(1, height - 4));
        }
        g.dispose();
    }

    private Color fillColor() {
        float ratio = hp / (float) maxHp;
        return ratio > 0.5f ? palette.green() : ratio > 0.2f ? palette.yellow() : palette.red();
    }
}
