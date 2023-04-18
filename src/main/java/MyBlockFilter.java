// Java Program to Illustrate MyBlockFilter

// Importing required classes
import java.io.IOException;
import java.io.PrintWriter;
// Importing all servlet classes
import javax.servlet.*;
import java.net.InetAddress;


import java.net.*;


// Class
// Implementing Filter class
public class MyBlockFilter implements Filter {

    // Method
    public void init(FilterConfig config)
            throws ServletException
    {
    }

    // Method
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException
    {

            String domainName = "J9N15M3.bla.is.keysight.com";
            InetAddress address = InetAddress.getByName(domainName);
            String ip = address.getHostAddress();
            System.out.println("IP address of " + domainName + ": " + address.getHostAddress());

//....................................................................................................................
                NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
                InterfaceAddress interfaceAddress = networkInterface.getInterfaceAddresses().get(0);
                String subnetMask = toIPv4Address(interfaceAddress.getNetworkPrefixLength());
                System.out.println("Subnet mask: " + subnetMask);
 //...................................................................................................... calculating main system subnet id

        InetAddress ipAddress = InetAddress.getByName(ip);
        InetAddress subnetMaskCal = InetAddress.getByName(subnetMask);

        String subnetIdMain = getSubnetId(ipAddress, subnetMaskCal);

        System.out.println("Subnet ID: " + subnetIdMain);


//.......................................................................................................

        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost.getHostAddress());

        String pcip = localHost.getHostAddress();
//........................................................................................................
        InetAddress ipAddress1 = InetAddress.getByName(pcip);
        InetAddress subnetMaskCal1 = InetAddress.getByName(subnetMask);

        String subnetIdPer = getSubnetId(ipAddress1, subnetMaskCal1);

        System.out.println("Subnet ID per: " + subnetIdPer);



        PrintWriter out = resp.getWriter();
        System.out.println(ip);


        if (subnetIdMain.equals(subnetIdPer)) {
            fc.doFilter(req, resp);

        }
        else{

            out.print(
                    "Your ip address is blocked by this websites!!");

        }


    }

    // Method
    public void destroy() {}
    private static String toIPv4Address(int prefixLength) {
        int mask = 0xffffffff << (32 - prefixLength);
        return String.format("%d.%d.%d.%d",
                (mask >>> 24) & 0xff,
                (mask >>> 16) & 0xff,
                (mask >>> 8) & 0xff,
                mask & 0xff);
    }

    private static String getSubnetId(InetAddress ipAddress, InetAddress subnetMask) {
        byte[] ipBytes = ipAddress.getAddress();
        byte[] maskBytes = subnetMask.getAddress();

        byte[] subnetIdBytes = new byte[ipBytes.length];
        for (int i = 0; i < ipBytes.length; i++) {
            subnetIdBytes[i] = (byte) (ipBytes[i] & maskBytes[i]);
        }

        try {
            return InetAddress.getByAddress(subnetIdBytes).getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unable to construct subnet ID", e);
        }
    }
}
