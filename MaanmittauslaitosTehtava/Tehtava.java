import java.lang.System;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tehtava
{
    public static void main(String args[])
    {
        Tuotteet hinnasto = tuotteidenMäärittely();
        toinenAlgoritmi(hinnasto);
        /* 
        Hitaampi mutta luotettavampi versio:
        hidasAlgoritmi(hinnasto);
        Tiedostosta lukeva versio:
        Tuotteet hinnasto = tuotteidenLuku();
        yksiAlgoritmi(hinnasto)
        */ 
        
    }

    public static Tuotteet tuotteidenMäärittely()
    {
        String[] tuotteet = {"Lainhuutodistus;5",
        "Rasitustodistus;10",
        "Vuokraoikeustodistus;5",
        "Lainhuutodistus oikeaksi todistettava;5",
        "Rasitustodistus oikeaksi todistettava;10",
        "Vuokraoikeustodistus oikeaksi todistettava;5",
        "Ylläpitomaksu;15"};
        Tuotteet hinnasto = new Tuotteet(csvParse(tuotteet[0]));
        for (int i = 1; i < tuotteet.length; i++)
        {
            hinnasto.add(csvParse(tuotteet[i]));
        }
        return hinnasto;
    }

    public static String asiakkaanPyyntö(int i)
    {
        String[] asiakkaat = {"asiakas1;kiinteistönvälittäjät",
            "asiakas2;maistaraatit",
            "asiakas3;kunnat",
            "asiakas4;kiinteistönvälittäjät"};
        if (i >= asiakkaat.length)
        {
            return null;
        }
        return asiakkaat[i];
    }

    public static String tapahtumanPyyntö(int i)
    {
        String[] tapahtumat = {
            "asiakas1;Lainhuutodistus;1;1.4.2023",
            "asiakas1;Vuokraoikeustodistus oikeaksi todistettava;8;1.4.2023",
            "asiakas1;Rasitustodistus;1;4.4.2023",
            "asiakas2;Rasitustodistus oikeaksi todistettava;2;2.4.2023",
            "asiakas2;Lainhuutodistus oikeaksi todistettava;1;7.4.2023",
            "asiakas2;Vuokraoikeustodistus;1;7.4.2023",
            "asiakas3;Vuokraoikeustodistus;5;2.4.2023",
            "asiakas3;Vuokraoikeustodistus oikeaksi todistettava;7;7.4.2023",
            "asiakas4;Rasitustodistus;7;7.4.2023",
            "asiakas4;Rasitustodistus oikeaksi todistettava;3;8.4.2023"};
        if (i >= tapahtumat.length)
        {
            return null;
        }
        return tapahtumat[i];
    }

    public static void toinenAlgoritmi(Tuotteet hinnasto)
    {
        /*
         * Oletuksena että asiakkaa ja tapahtumat ovat samassa järjestyksessä
         */
        int i = 0;
        int j = 0;
        String[] y = csvParse(asiakkaanPyyntö(i));
        String[] x = csvParse(tapahtumanPyyntö(j));
        while (y != null)
        {
            y = csvParse(asiakkaanPyyntö(i));
            if (y == null)
            {
                System.out.println("Tapahtumat epäjärjestyksessä tai tuntematon asiakas");
                System.out.println("Tapahtuma: " + x);
                break;
            }
            Asiakas a = new Asiakas(y[0], y[1]);
            if (a.Ilmainen())
            {
                while(x[0].equals(a.asiakasTunnus))
                {
                    j += 1;
                    x = csvParse(tapahtumanPyyntö(j));
                    if (x == null)
                    {
                        break;
                    }
                }
            }
            if (a.Maksullinen())
            {
                int k = simpleFind("Ylläpitomaksu", hinnasto);
                LaskutusTapahtuma yllapitoMaksu = new LaskutusTapahtuma(hinnasto.tuoteNimet.get(k), 1, hinnasto.tuoteHinnat.get(k));
                Lasku maksullinenLasku = new Lasku(a, yllapitoMaksu);
                while (x[0].equals(a.asiakasTunnus))
                {
                    LaskutusTapahtuma luettuTapahtuma = generateTapahtuma(x, hinnasto);
                    maksullinenLasku.add(luettuTapahtuma);
                    j += 1;
                    x = csvParse(tapahtumanPyyntö(j));
                    if (x == null)
                    {
                        break;
                    }
                }
                printtaus(maksullinenLasku.tuloste());
            }
            else
            {
                if (x[0].equals(a.asiakasTunnus))
                {
                    Lasku maistraattiLasku = new Lasku(a, null);
                    while (x[0].equals(a.asiakasTunnus))
                    {
                        if (x[1].contains(" oikeaksi "))
                        {
                            LaskutusTapahtuma luettuTapahtuma = generateTapahtuma(x, hinnasto);
                            maistraattiLasku.add(luettuTapahtuma);
                        }
                        j += 1;
                        x = csvParse(tapahtumanPyyntö(j));
                        if (x == null)
                        {
                            break;
                        }
                    }
                    if (maistraattiLasku.laskutusTapahtumat.size() > 0)
                    {
                        printtaus(maistraattiLasku.tuloste());
                    }
                }
            }
            i += 1;
            if (x == null)
            {
                break;
            }
        }
    }

	public static void hidasAlgoritmi(Tuotteet hinnasto)
    {
        /*
         * Tämä algoritmi toimii myös sekalaisille asiakas- ja tapahtuma taulukoille
         * Kompleksisuus on n(tapahtuma)*n(asiakas)
         */
        int i = 0;
        String[] y = csvParse(asiakkaanPyyntö(i));
        while (y != null)
        {
			int j = 0;
			String[] x = csvParse(tapahtumanPyyntö(j));
            Asiakas a = new Asiakas(y[0], y[1]);
            if (a.Maksullinen())
            {
                int k = simpleFind("Ylläpitomaksu", hinnasto);
                LaskutusTapahtuma yllapitoMaksu = new LaskutusTapahtuma(hinnasto.tuoteNimet.get(k), 1, hinnasto.tuoteHinnat.get(k));
                Lasku maksullinenLasku = new Lasku(a, yllapitoMaksu);
                while (x != null)
                {
					if (x[0].equals(a.asiakasTunnus))
                    {
						LaskutusTapahtuma luettuTapahtuma = generateTapahtuma(x, hinnasto);
						maksullinenLasku.add(luettuTapahtuma);
					}
                    j += 1;
                    x = csvParse(tapahtumanPyyntö(j));
                }
                printtaus(maksullinenLasku.tuloste());
            }
            else if (!a.Ilmainen())
            {
                Lasku maistraattiLasku = new Lasku(a, null);
                while (x != null)
                {
                    if (x[0].equals(a.asiakasTunnus) & x[1].contains(" oikeaksi "))
                    {
                        LaskutusTapahtuma luettuTapahtuma = generateTapahtuma(x, hinnasto);
                        maistraattiLasku.add(luettuTapahtuma);
                    }
                    j += 1;
                    x = csvParse(tapahtumanPyyntö(j));
                }
                if (maistraattiLasku.laskutusTapahtumat.size() > 0)
                {
                    printtaus(maistraattiLasku.tuloste());
                }
            }
            i += 1;
            y = csvParse(asiakkaanPyyntö(i));
        }
    }

    public static LaskutusTapahtuma generateTapahtuma(String[] info, Tuotteet hinnasto)
    {
        int i = simpleFind(info[1], hinnasto);
        if (i < 0)
        {
            System.out.println("Tuote ei löytynyt hinnastosta: " + info[1]);
        }
        return new LaskutusTapahtuma(hinnasto.tuoteNimet.get(i), Integer.parseInt(info[2]), hinnasto.tuoteHinnat.get(i));
    }

    public static void printtaus(String[] lasku)
    {
        for (int i = 0; i < lasku.length; i++)
        {
            System.out.println(lasku[i]);
        }
    }

    public static int simpleFind(String nimi, Tuotteet hinnasto)
    {
        for (int i = 0; i < hinnasto.tuoteNimet.size(); i++)
        {
            if (nimi.equals(hinnasto.tuoteNimet.get(i)))
            {
                return i;
            }
        }
        return -1;
    }

    public static String[] csvParse(String csvString)
    {
        if (csvString == null)
        {
            return null;
        }
        return csvString.split(";");
    }

    // Alla olevan koodin tulisi lukea tiedostosta
    // kaatuu .nextLine(); riveillä
    // Pelkistetty versio kuitenkin toimi testauksessa
    public static Tuotteet tuotteidenLuku()
    {
        Scanner tuoteTiedosto = openFile("Tuotteet.csv");
        tuoteTiedosto.nextLine();
        Tuotteet luetutTuotteet = new Tuotteet(csvParse(tuoteTiedosto.nextLine()));
        while (tuoteTiedosto.hasNextLine())
        {
            luetutTuotteet.add(csvParse(tuoteTiedosto.nextLine()));
        }
        tuoteTiedosto.close();
        return luetutTuotteet;
    }

    public static Scanner openFile(String fileName)
    {
        try
        {
            File openedFile = new File(fileName);
            Scanner requestedFile = new Scanner(openedFile);
            return requestedFile;
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found at " + fileName);
            return null;
        }
    }
    
    public static void yksiAlgoritmi(Tuotteet hinnasto)
    {
        Scanner asiakkaatTiedosto = openFile("Asiakkaat.csv");
        Scanner tapahtumatTiedosto = openFile("LaskutusTapahtumat.csv");
        asiakkaatTiedosto.nextLine();
        tapahtumatTiedosto.nextLine();
        String[] x = csvParse(tapahtumatTiedosto.nextLine());
        while (asiakkaatTiedosto.hasNextLine())
        {
            String[] y = csvParse(asiakkaatTiedosto.nextLine());
            Asiakas a = new Asiakas(y[0], y[1]);
            if (a.Ilmainen())
            {
                while(x[0].equals(a.asiakasTunnus) & tapahtumatTiedosto.hasNextLine())
                {
                    x = csvParse(tapahtumatTiedosto.nextLine());
                }
                continue;
            }
            if (a.Maksullinen())
            {
                int i = simpleFind("Ylläpitomaksu", hinnasto);
                LaskutusTapahtuma yllapitoMaksu = new LaskutusTapahtuma(hinnasto.tuoteNimet.get(i), 1, hinnasto.tuoteHinnat.get(i));
                Lasku maksullinenLasku = new Lasku(a, yllapitoMaksu);
                while (x[0].equals(a.asiakasTunnus) & tapahtumatTiedosto.hasNextLine())
                {
                    LaskutusTapahtuma luettuTapahtuma = generateTapahtuma(x, hinnasto);
                    maksullinenLasku.add(luettuTapahtuma);
                    x = csvParse(tapahtumatTiedosto.nextLine());
                }
                printtaus(maksullinenLasku.tuloste());
            }
            else
            {
                if (x[0].equals(a.asiakasTunnus))
                {
                    Lasku maistraattiLasku = new Lasku(a, null);
                    while (x[0].equals(a.asiakasTunnus) & tapahtumatTiedosto.hasNextLine())
                    {
                        if (x[1].contains("oikeaksi"))
                        {
                            LaskutusTapahtuma luettuTapahtuma = generateTapahtuma(x, hinnasto);
                            maistraattiLasku.add(luettuTapahtuma);
                        }
                        x = csvParse(tapahtumatTiedosto.nextLine());
                    }
                    if (maistraattiLasku.laskutusTapahtumat.size() > 0)
                    {
                        printtaus(maistraattiLasku.tuloste());
                    }
                }
            }
        }
    }
}