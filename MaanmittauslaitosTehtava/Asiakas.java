
public class Asiakas
{
    public String asiakasTunnus;
    public String asiakasRyhmä;

    public Asiakas(String asiakasTunnus, String asiakasRyhmä)
    {
        this.asiakasTunnus = asiakasTunnus;
        this.asiakasRyhmä = asiakasRyhmä;
    }

    public boolean Ilmainen()
    {
        if (this.asiakasRyhmä.equals("kunnat"))
        {
            return true;
        }
        return false;
    }

    public boolean Maksullinen()
    {
        if (this.asiakasRyhmä.equals("kiinteistönvälittäjät"))
        {
            return true;
        }
        return false;
    }
}