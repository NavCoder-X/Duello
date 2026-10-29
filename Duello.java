import javax.swing.*;
import java.awt.*;

public class Duello extends JFrame{

    private final Pokemon a,b;
    private final BattleHubAssets assets;
    int WIDTH = 1060, HEIGHT = 720;
    boolean isFinished = false;
    MovePanel moves = new MovePanel();
    FightPanel fight = new FightPanel();

    public Duello(Pokemon a,Pokemon b){
        this.a = a;
        this.b = b;
        try {
            assets = BattleHubAssets.loadDefault();
        } catch (java.io.IOException exception) {
            throw new IllegalStateException("Unable to load src/sprites/battleHub.png", exception);
        }

        // inzio della finestra del duello
        this.setTitle("Duello");
        this.setSize(WIDTH,HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        moves.setPreferredSize(new Dimension(WIDTH,200));
        this.add(moves,BorderLayout.SOUTH);
        this.add(fight,BorderLayout.CENTER);

        // metto i due pokemon
        int w = 250, h = 250;
        ImageIcon imgA = scaleImg(a.getBImmagine(), w, h);
        ImageIcon imgB = scaleImg(b.getImmagine(), w, h);
        JLabel labelA = new JLabel(imgA);
        JLabel labelB = new JLabel(imgB);
        fight.add(labelA);
        fight.add(labelB);
        labelA.setBounds(100, 265, w, h);
        labelB.setBounds(625, 115, w, h);

        // metto le barre della vita
        InfoLabel infoA = new InfoLabel(a, assets.hpBarPalette());
        InfoLabel infoB = new InfoLabel(b, assets.hpBarPalette());
        fight.add(infoA);
        fight.add(infoB);
        infoA.setBounds(10, 75, 200, 80);
        infoB.setBounds(WIDTH - 230, 380, 200, 80);

        this.setVisible(true);
    }

    ImageIcon scaleImg(ImageIcon img, int width, int height){
        Image imgScalata = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon iconaPronta = new ImageIcon(imgScalata);
        return iconaPronta;
    }

    void turno(){
        isFinished = a.attack(b);
        if(isFinished) return;
        isFinished = b.attack(a);
        if(isFinished) return;
    }
}