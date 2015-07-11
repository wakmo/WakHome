package wak.work.cryptogram.graem.securecrypto.keycache;

import wak.work.cryptogram.graem.securecrypto.Algorithm;
import wak.work.cryptogram.graem.securecrypto.SecureCryptoException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: growles
 * Date: 09-Feb-2012
 * Time: 11:47:38
 */
public class KeyCacheReport
{
    private static final Logger log = Logger.getLogger(KeyCacheReport.class);

    //<currentPoolSize>!<maxPoolSize>!<keySize>!<CRT or ME>!<BDK, ECK or DCK>!<SmType>!<MkIdentifier>!<AdditionalData>
    private final Pattern REPORT_PATTERN = Pattern.compile("([0-9]+)!([0-9]+)!([0-9]+)!(CRT|ME|ALL)!([A-Za-z0-9\\.]*)!([A-za-z0-9]*)!(.*)");

    //Must match the Regex above
    private static final String ALG_CRT_STR = "CRT";
    private static final String ALG_MODEXP_STR = "ME";
    private static final String ALG_ALL_STR = "ALL";

    private final List<PoolRecord> poolRecords;
    private final boolean error;


    public KeyCacheReport()
    {
        poolRecords = new ArrayList<PoolRecord>();
        error = false;
    }

    public KeyCacheReport(String data)
    {
        String[] pools = data.split(";");
        poolRecords = new ArrayList<PoolRecord>(pools.length);

        boolean errorOccurred = false;
        try
        {

            for (String poolData : pools)
            {
                Matcher m = REPORT_PATTERN.matcher(poolData);
                if (!m.matches())
                {
                    throw new KeyCacheReportReadException("Invalid key cache reporting data: " + data);
                }

                int poolSize = toInt(m.group(1));
                int maxPoolSize = toInt(m.group(2));
                int keySize = toInt(m.group(3));
                String algorithmStr = m.group(4);

                Algorithm algorithm;
                if (algorithmStr.equals(ALG_CRT_STR))
                {
                    algorithm = Algorithm.RSA_CRT;
                }
                else if (algorithmStr.equals(ALG_MODEXP_STR))
                {
                    algorithm = Algorithm.RSA_MODEXP;
                }
                else if (algorithmStr.equals(ALG_ALL_STR))
                {
                    algorithm = Algorithm.RSA_ALL;
                }
                else
                {
                    throw new KeyCacheReportReadException("Invalid algorithm in key cache reporting data '" + algorithmStr + "' in: " + data);
                }

                String smType = m.group(5);
                String mkId = m.group(6);
                String additionalData = m.group(7);

                addPool(poolSize, maxPoolSize, keySize, algorithm, smType, mkId, additionalData);
            }
        }
        catch (KeyCacheReportReadException x)
        {
            log.error("An exception occurred reading the Key Cache report", x);
            errorOccurred = true;
        }
        catch (SecureCryptoException x)
        {
            log.error("An exception occurred reading the Key Cache report", x);
            errorOccurred = true;
        }

        error = errorOccurred;
    }

    public void addPool(int poolSize, int maxPoolSize, int keySize, Algorithm algorithm,
                        String smType, String mkId, String additionalData) throws SecureCryptoException
    {
        String algorithmStr;
        if (Algorithm.RSA_ALL.equals(algorithm))
        {
            algorithmStr = ALG_ALL_STR;
        }
        else if (Algorithm.RSA_CRT.equals(algorithm))
        {
            algorithmStr = ALG_CRT_STR;
        }
        else if (Algorithm.RSA_MODEXP.equals(algorithm))
        {
            algorithmStr = ALG_MODEXP_STR;
        }
        else
        {
            String errMsg = "Algorithm not supported: " + algorithm;
            log.warn(errMsg);
            throw new SecureCryptoException(errMsg);
        }

        poolRecords.add(new PoolRecord(poolSize, maxPoolSize, keySize, algorithm, algorithmStr,
                smType, mkId, additionalData));
    }

    public String toData()
    {
        StringBuilder sb = new StringBuilder();

        for (PoolRecord record : poolRecords)
        {
            sb.append(record.poolSize).append("!");
            sb.append(record.maxPoolSize).append("!");
            sb.append(record.keySize).append("!");
            sb.append(record.algorithmStr).append("!");
            sb.append(record.smType).append("!");
            sb.append(record.mkId).append("!");

            String additionalData = record.additionalData;
            if (additionalData == null)
            {
                additionalData = "";
            }
            sb.append(additionalData).append(";");
        }

        return sb.toString();
    }

    public String toShortReport()
    {
        String reportStr;

        if (error)
        {
            reportStr = "ERROR - See logs";
            log.error("Cannot parse key cache data from SCI Server.  Check the versions of both the SCI Concentratotr and the GUI Client are the same.");
        }
        else
        {
            int totalPoolSize = 0;
            int totalMaxPoolSize = 0;

            StringBuilder reportBuff = new StringBuilder();

            for (PoolRecord record : poolRecords)
            {
                totalPoolSize += record.poolSize;
                totalMaxPoolSize += record.maxPoolSize;

                reportBuff.append(" [").append(record.keySize).append("/").append(record.algorithmStr)
                        .append(": ").append(record.poolSize).append("/").append(record.maxPoolSize)
                        .append("]");
            }

            StringBuilder buff = new StringBuilder();
            buff.append(totalPoolSize).append(" of ").append(totalMaxPoolSize);
            buff.append(reportBuff);

            reportStr = buff.toString();

        }

        return reportStr;
    }

    public String toFullReport()
    {
        StringBuilder report = new StringBuilder();

        if (error)
        {
            report.append("Report is not available:\n\nAn error occurred reading the report data from SCI.");
        }
        else
        {
            int totalPoolSize = 0;
            int totalMaxPoolSize = 0;

            for (PoolRecord record : poolRecords)
            {
                totalPoolSize += record.poolSize;
                totalMaxPoolSize += record.maxPoolSize;

                report.append("Key Pool:");
                report.append("\n    Current pool size: ").append(record.poolSize);
                report.append("\n    Maximum pool size: ").append(record.maxPoolSize);
                report.append("\n    Key size         : ").append(record.keySize);
                report.append("\n    Key algorithm    : ").append(record.algorithm);
                report.append("\n    SM Type          : ").append(record.smType);
                report.append("\n    MK Identifier    : ").append(record.mkId);
                if (record.additionalData != null && record.additionalData.length() > 0)
                {
                    report.append("\n    Additional Data  : ").append(record.additionalData);
                }
                report.append("\n\n");
            }

            report.append("\nTOTAL COMBINED POOL SIZE: ").append(totalPoolSize);
            report.append("\nTOTAL COMBINED MAX POOL SIZE: ").append(totalMaxPoolSize);
        }

        return report.toString();
    }


    private int toInt(String str) throws KeyCacheReportReadException
    {
        try
        {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException x)
        {
            throw new KeyCacheReportReadException("Invalid data in Key Cache Reporting string: " + str, x);
        }
    }


    private class PoolRecord
    {
        private final int poolSize;
        private final int maxPoolSize;
        private final int keySize;
        private final Algorithm algorithm;
        private final String algorithmStr;
        private final String smType;
        private final String mkId;
        private final String additionalData;

        private PoolRecord(int poolSize, int maxPoolSize, int keySize, Algorithm algorithm,
                           String algorithmStr, String smType, String mkId, String additionalData)
        {
            this.poolSize = poolSize;
            this.maxPoolSize = maxPoolSize;
            this.keySize = keySize;
            this.algorithm = algorithm;
            this.algorithmStr = algorithmStr;
            this.smType = smType;
            this.mkId = mkId;
            this.additionalData = additionalData;
        }
    }

    private class KeyCacheReportReadException extends Exception
    {
        private KeyCacheReportReadException(String message)
        {
            super(message);
        }

        private KeyCacheReportReadException(String message, Throwable cause)
        {
            super(message, cause);
        }
    }
}
