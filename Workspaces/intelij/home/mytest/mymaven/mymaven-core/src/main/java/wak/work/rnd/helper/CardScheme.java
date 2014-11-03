package wak.work.rnd.helper;

public enum CardScheme
{
    VSDC('0'),
    MasterCard('1'),
    AmericanExpress('2');

    private final char ps9000SchemeId;

    CardScheme(char ps9000SchemeId)
    {
        this.ps9000SchemeId = ps9000SchemeId;
    }

    public char getPs9000SchemeId()
    {
        return ps9000SchemeId;
    }
}
