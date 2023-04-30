import java.util.ArrayList;
public class Lasku {
    public Asiakas asiakas;
    public ArrayList<LaskutusTapahtuma> laskutusTapahtumat;
    public float summa;

    public Lasku(Asiakas asiakas, LaskutusTapahtuma laskutusTapahtuma)
    {
        this.asiakas = asiakas;
        this.laskutusTapahtumat = new ArrayList<LaskutusTapahtuma>();
        if (laskutusTapahtuma != null)
        {
            this.laskutusTapahtumat.add(laskutusTapahtuma);
            this.summa = 0;
        }
    }

    public void add(LaskutusTapahtuma laskutusTapahtuma)
    {
        for (int i = 0; i < this.laskutusTapahtumat.size(); i++)
        {
            if (this.laskutusTapahtumat.get(i).tuote.equals(laskutusTapahtuma.tuote))
            {
                this.laskutusTapahtumat.get(i).add(laskutusTapahtuma.kappaleMaara);
                return;
            }
        }
        this.laskutusTapahtumat.add(laskutusTapahtuma);
    }

    public void yhteen()
    {
        this.summa = 0;
        for (int i = 0; i < laskutusTapahtumat.size(); i++)
        {
            this.summa += laskutusTapahtumat.get(i).kokoHinta;
        }
    }

    public String[] tuloste()
    {
        yhteen();
        String[] a = new String[laskutusTapahtumat.size() + 2];
        a[0] = this.asiakas.asiakasTunnus + " " + this.asiakas.asiakasRyhmä;
        for (int i = 0; i < laskutusTapahtumat.size(); i++)
        {
            LaskutusTapahtuma rivi = laskutusTapahtumat.get(i);
            a[i+1] = rivi.tuote + ", " + rivi.kappaleMaara + " kappaletta, " + rivi.hinta + " euroa/kappale, " + rivi.kokoHinta + " euroa";
        }
        a[a.length - 1] = "Yhteensä: " + this.summa + " euroa";
        return a;
    }
}
