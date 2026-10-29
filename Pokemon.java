import javax.swing.ImageIcon;

public class Pokemon {

    private int hp;
    private int MAX_HP;
    private int attacco;
    private int difesa;
    private final String nome;
    private ImageIcon immagine;
    private ImageIcon bImmagine;

    Pokemon(int hp,int attacco,int difesa,String nome){
        this.MAX_HP = hp;
        this.hp = hp;
        this.attacco = attacco;
        this.difesa = difesa;
        this.nome = nome;
        this.immagine = new ImageIcon("src/sprites/charizardY.png"); 
        this.bImmagine = new ImageIcon("src/sprites/charizardY_back.png"); 
    }

    Pokemon(){
        this(100,10,10,"Rattata");
    }

    void setHp(int hp){
        this.hp = Math.max(0, Math.min(MAX_HP, hp));
    }

    void setAttacco(int attacco){
        this.attacco = attacco;
    }

    void setDifesa(int difesa){
        if(difesa < 0) this.difesa = 0;
        this.difesa = difesa;
    }

    int getHp(){
        return hp;
    }

    int getMaxHp(){
        return MAX_HP;
    }

    int getAttacco(){
        return attacco;
    }

    int getDifesa(){
        return difesa;
    }

    String getNome(){
        return nome;
    }

    ImageIcon getImmagine(){
        return immagine;
    }

    ImageIcon getBImmagine(){
        return bImmagine;
    }

    // true = ucciso , false = l avversario è in vita
    boolean attack(Pokemon avversario){
        int hpAvversario = avversario.getHp();
        int danno = this.getAttacco();
        System.out.println(nome + " ha fatto " + danno + " danni a " + avversario.getNome());
        hpAvversario -= danno;
        avversario.setHp(hpAvversario);
        System.out.println("adesso ha " + avversario.getHp());
        return  hpAvversario <= 0;
    }
     
}