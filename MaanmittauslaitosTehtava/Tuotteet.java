import java.util.ArrayList;
public class Tuotteet {
    public ArrayList<String> tuoteNimet;
    public ArrayList<Float> tuoteHinnat;

    public Tuotteet(String[] tuoteTiedot)
    {
        this.tuoteNimet = new ArrayList<>();
        this.tuoteNimet.add(tuoteTiedot[0]);
        this.tuoteHinnat = new ArrayList<>();
        this.tuoteHinnat.add(Float.parseFloat(tuoteTiedot[1]));
    }

    public void add(String[] tuoteTiedot)
    {
        this.tuoteNimet.add(tuoteTiedot[0]);
        this.tuoteHinnat.add(Float.parseFloat(tuoteTiedot[1]));
    }
}
