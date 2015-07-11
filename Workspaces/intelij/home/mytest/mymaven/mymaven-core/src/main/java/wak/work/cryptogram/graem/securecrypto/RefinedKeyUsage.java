package wak.work.cryptogram.graem.securecrypto;

import org.apache.log4j.Logger;

public enum RefinedKeyUsage
{
    //Name                (LMK,Vt) KeyType Alg category Usage type
    ZMK        (new byte[] {4, 0},  "000", AlgType.DES, UsageType.ECK),
    ZPK        (new byte[] {6, 0},  "001", AlgType.DES, UsageType.ECK),
    PVK        (new byte[] {14, 0}, "002", AlgType.DES, UsageType.ECK),
    TAK        (new byte[] {38, 9}, "003", AlgType.DES, UsageType.ECK),
    ZAK        (new byte[] {38, 9}, "008", AlgType.DES, UsageType.ECK),
    BDK_TYPE1  (new byte[] {38, 7}, "009", AlgType.DES, UsageType.BDK),
    KEK        (new byte[] {38, 9}, "107", AlgType.DES, UsageType.ECK),
    KMC        (new byte[] {38, 6}, "207", AlgType.DES, UsageType.KDK_KDK_ECK),
    SK_ENC     (new byte[] {38, 9}, "307", AlgType.DES, UsageType.ECK),
    SK_MAC     (new byte[] {38, 9}, "407", AlgType.DES, UsageType.ECK),
    SK_DEK     (new byte[] {38, 9}, "507", AlgType.DES, UsageType.ECK),
    MK_KE      (new byte[] {38, 6}, "807", AlgType.DES, UsageType.KDK_ECK),
    MK_AS      (new byte[] {38, 6}, "907", AlgType.DES, UsageType.KDK_ECK),
    SK_RMAC    (new byte[] {38, 9}, "008", AlgType.DES, UsageType.ECK),
    MK_AC      (new byte[] {38, 6}, "109", AlgType.DES, UsageType.KDK_KDK_ECK),
    MK_SMI     (new byte[] {38, 6}, "209", AlgType.DES, UsageType.KDK_KDK_ECK),
    MK_SMC     (new byte[] {38, 6}, "309", AlgType.DES, UsageType.KDK_KDK_ECK),
    MK_DAC     (new byte[] {38, 6}, "409", AlgType.DES, UsageType.KDK_KDK_ECK),
    MK_DN      (new byte[] {38, 6}, "509", AlgType.DES, UsageType.KDK_KDK_ECK),
    MK_CVC3    (new byte[] {38, 6}, "709", AlgType.DES, UsageType.ECK),
    CVK        (new byte[] {38, 9}, "402", AlgType.DES, UsageType.ECK),
    ZEK        (new byte[] {38, 9}, "00A", AlgType.DES, UsageType.BDK),
    DEK        (new byte[] {32, 0}, "00B", AlgType.DES, UsageType.ECK),
    RSA_PRIV   (new byte[] {34, 0}, "00C", AlgType.RSA_CRT, UsageType.BDK),
    RSA_PUB    (new byte[] {36, 0}, "00D", AlgType.RSA_ALL, UsageType.BDK),
    LMK_3839_V0(new byte[] {38, 0}, "00E", AlgType.DES, UsageType.ECK),
    LMK_3839_V9(new byte[] {38, 9}, "90E", AlgType.DES, UsageType.ECK),
    CK_ENC     (new byte[] {38, 6}, "30D", AlgType.DES, UsageType.KDK_ECK),  //38,6 is guessed for RG7000
    CK_MAC     (new byte[] {38, 6}, "40D", AlgType.DES, UsageType.KDK_ECK),  //38,6 is guessed for RG7000
    CK_DEK     (new byte[] {38, 6}, "50D", AlgType.DES, UsageType.KDK_ECK),  //38,6 is guessed for RG7000
    DUMMYTYPE  (new byte[] {0, 0},  "XXX", AlgType.DES, UsageType.NONE),
    KEYBLOCK   (null,               "FFF", AlgType.DES, UsageType.NONE);



    private static final Logger log = Logger.getLogger(RefinedKeyUsage.class);

    enum AlgType {DES, RSA_CRT, RSA_ALL}
    enum UsageType { ECK, BDK, KDK_ECK, KDK_KDK_ECK, NONE }


    private final byte[] smExt7000;
    private final String smExt9000;
    private final AlgType algType;
    private final UsageType usageType;

    private RefinedKeyUsage(byte[] smExt7000, String smExt9000, AlgType algType, UsageType usageType)
    {
        this.smExt7000 = smExt7000;
        this.smExt9000 = smExt9000;
        this.algType = algType;
        this.usageType = usageType;
    }

    public byte[] getSmExt7000()
    {
        return smExt7000;
    }

    public String getSmExt9000()
    {
        return smExt9000;
    }

    public KeyUsage[] getKeyUsageArray(int keySize)
    {
        //Get algorithm
        Algorithm algorithm = null;
        if (algType == AlgType.DES)
        {
            switch (keySize)
            {
                case 64: algorithm = Algorithm.DES1E; break;
                case 128: algorithm = Algorithm.DES2EDE; break;
                case 192: algorithm = Algorithm.DES3EDE; break;
                default:
                {
                    log.warn("Invalid key size (bits): " + keySize);
                    algorithm = Algorithm.DES2EDE;
                }
            }
        }
        else if (algType == AlgType.RSA_CRT)
        {
            algorithm = Algorithm.RSA_CRT;
        }
        else if (algType == AlgType.RSA_ALL)
        {
            algorithm = Algorithm.RSA_ALL;
        }
        else
        {
            log.warn("Invalid algType: " + algType);
        }


        KeyUsage[] keyUsage;
        switch (usageType)
        {
            case ECK:
                keyUsage = new KeyUsage[] {
                    new KeyUsage(algorithm, Usage.ECK)
                };
                break;

            case BDK:
                keyUsage = new KeyUsage[] {
                    new KeyUsage(algorithm, Usage.BDK)
                };
                break;

            case KDK_ECK:
                keyUsage = new KeyUsage[] {
                    new KeyUsage(algorithm, Usage.KDK),
                    new KeyUsage(algorithm, Usage.ECK)
                };
                break;

            case KDK_KDK_ECK:
                keyUsage = new KeyUsage[] {
                    new KeyUsage(algorithm, Usage.KDK),
                    new KeyUsage(algorithm, Usage.KDK),
                    new KeyUsage(algorithm, Usage.ECK)
                };
                break;

            default:
                keyUsage = new KeyUsage[0];
        }

        return keyUsage;
    }

    public static RefinedKeyUsage[] getSelectableSymmetricValues()
    {
        return new RefinedKeyUsage[] {
                        ZMK, ZPK, PVK, ZAK, KEK, KMC, CK_ENC, CK_MAC, CK_DEK, MK_KE, MK_AS, SK_RMAC,
                        MK_AC, MK_SMI, MK_SMC , MK_DAC, MK_DN, MK_CVC3, ZEK, DEK
        };
    }

    public static RefinedKeyUsage fromKeyType(String keyType)
    {
        for (RefinedKeyUsage rku : values())
        {
            if (rku.smExt9000.equalsIgnoreCase(keyType))
            {
                return rku;
            }
        }

        return null;
    }
}
