import javax.swing.*;
import java.awt.*;

public class InfoLabel extends JLabel {
    private final SpriteHpBar barra;

    public InfoLabel(Pokemon p){
        this(p, loadPalette());
    }

    public InfoLabel(Pokemon p, BattleHubAssets.HpBarPalette palette){
        this.setText(p.getNome());
        this.setFont(this.getFont().deriveFont(20.0f));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.TOP);
        this.setOpaque(true);
        this.setBackground(Color.WHITE);

        barra = new SpriteHpBar(p.getMaxHp(), palette);
        barra.setHp(p.getHp());
        barra.setBounds(0, 25, 200, 14);
        this.add(barra);
    }

    public void updateHp(int hp) {
        barra.setHp(hp);
    }

    public SpriteHpBar getLifeBar() {
        return barra;
    }

    private static BattleHubAssets.HpBarPalette loadPalette() {
        try {
            return BattleHubAssets.loadDefault().hpBarPalette();
        } catch (java.io.IOException exception) {
            return new BattleHubAssets.HpBarPalette(Color.GREEN, Color.YELLOW, Color.RED, Color.BLACK);
        }
    }
    
}
