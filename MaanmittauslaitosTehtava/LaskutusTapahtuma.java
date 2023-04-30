public class LaskutusTapahtuma {
    public String tuote;
    public int kappaleMaara;
    public float hinta;
    public float kokoHinta;

    public LaskutusTapahtuma(String tuote, int kappaleMaara, float hinta)
    {
        this.tuote = tuote;
        this.kappaleMaara = kappaleMaara;
        this.hinta = hinta;
        this.kokoHinta = kappaleMaara*hinta;
    }

    public void add(int kappaleMaara)
    {
        this.kappaleMaara += kappaleMaara;
        this.kokoHinta = kappaleMaara*hinta;
    }
}
