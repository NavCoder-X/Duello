
public class Main {

    public static void main(String[] args){
        Pokemon p1 = new Pokemon(100,25,3,"Pikachu");
        Pokemon p2 = new Pokemon(200,30,3,"Charizard");
        Duello d = new Duello(p1,p2);
        d.turno();
    }
}