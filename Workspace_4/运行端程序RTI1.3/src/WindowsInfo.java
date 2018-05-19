import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.Sigar;





public class WindowsInfo implements Runnable{
	
	private static final int CPUTIME=500;
	private static final int PERCENT=100;
	private static final int FAULTLENGTH=10;
    private static String cpuused;
	private static int kb=1024;
	private static int i=0;
	public static Statement st=null;
	public static String hostAddress;
	String m;
	
	public static Sigar sigar=new Sigar();
	public static Mem mem=null;
	public static CpuPerc cpu=null;
	public static String ifNames[]=null;
	public  CpuInfo info; 
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			mem=sigar.getMem();
			cpu=sigar.getCpuPerc();
			 ifNames=sigar.getNetInterfaceList();
			 info= sigar.getCpuInfoList()[0];
			  System.out.println("*****��ǰCPUʹ����� ��");
			  System.out.println("info_Mhz "+info.getMhz());//Cpu��Ƶ��####
			  System.out.println("#��ʹ����: " + cpu.getCombined() * 100 + "%");
			  System.out.println("#�û�ʹ����(user): " + cpu.getUser() * 100 + "%");
			  System.out.println("#ϵͳʹ����(sys): " + cpu.getSys() * 100 + "%");
			  System.out.println("#��ǰ������(idel): " + cpu.getIdle() * 100 + "%");
			  System.out.println("\n*****��ǰ�ڴ�ʹ����� ��");
			  System.out.println("#�ڴ�������" + mem.getTotal() / 1024 / 1024 + "M");
			  System.out.println("#��ʹ���ڴ棺" + mem.getUsed() / 1024 / 1024 + "M");
			  System.out.println("#ʣ���ڴ棺" + mem.getFree() / 1024 / 1024 + "M");
			  String cpuused=String.valueOf(cpu.getCombined());
			  String cpuMhz=String.valueOf(info.getMhz());
			  String memory=String.valueOf(mem.getFree() / 1024 / 1024 )+ "M";
			  String cpuTotal=String.valueOf(mem.getTotal() / 1024 / 1024 )+ "M";
			  String ip=null;
				for (int i = 0; i < ifNames.length; i++) {
					String name = ifNames[i];
					NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);

					if ((ifconfig.getFlags() & 1L) <= 0L) {
//						System.out.println("!IFF_UP...skipping getNetInterfaceStat");
						continue;
					}
				}

				ip=InetAddress.getLocalHost().getHostAddress();
			   wirteServerInfo( ip,cpuused, memory,cpuMhz,cpuTotal);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}



	}
	public static  void wirteServerInfo(String ip,String cpu,String memory,String cpuMhz,String cpuTotal) throws SQLException
	{
		Connection con= RunReadDB.getConnection();
		try {
			String sql="delete from server_state where SERVER_IP='"+ip+"'";
			st=(Statement)con.createStatement();
			st.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		String sql="insert into server_state values('"+ip+"','"+cpu+"','"+cpuMhz+"','"+cpuTotal+"','"+memory+"')";
		st=(Statement)con.createStatement();
		st.executeUpdate(sql);
		System.out.println("insert successed�� "+ip);
		System.out.println("AAAAAAAAAAAA");

		
	}
//	public static String getCpuRatioForWindows() {
//		
//        try { 
//            String procCmd = System.getenv("windir") + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount"; 
//            // ȡ������Ϣ 
//            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd)); 
//            Thread.sleep(CPUTIME); 
//            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd)); 
//            if (c0 != null && c1 != null) { 
//                long idletime = c1[0] - c0[0]; 
//                long busytime = c1[1] - c0[1]; 
//                return Double.valueOf(PERCENT * (busytime)*1.0 / (busytime + idletime)).intValue()+"%"; 
//            } else { 
//                return "CPUʹ����:"+0+"%"; 
//            } 
//        } catch (Exception ex) { 
//            ex.printStackTrace(); 
//            return "CPUʹ����:"+0+"%"; 
//        } 
//    } 
//    private static long[] readCpu(final Process proc) { 
//        long[] retn = new long[2]; 
//        try { 
//            proc.getOutputStream().close(); 
//            InputStreamReader ir = new InputStreamReader(proc.getInputStream()); 
//            LineNumberReader input = new LineNumberReader(ir); 
//            String line = input.readLine(); 
//            if (line == null || line.length() < FAULTLENGTH) { 
//                return null; 
//            } 
//            int capidx = line.indexOf("Caption"); 
//            int cmdidx = line.indexOf("CommandLine"); 
//            int rocidx = line.indexOf("ReadOperationCount"); 
//            int umtidx = line.indexOf("UserModeTime"); 
//            int kmtidx = line.indexOf("KernelModeTime"); 
//            int wocidx = line.indexOf("WriteOperationCount"); 
//            long idletime = 0; 
//            long kneltime = 0; 
//            long usertime = 0; 
//            while ((line = input.readLine()) != null) { 
//                if (line.length() < wocidx) { 
//                    continue; 
//                } 
//                // �ֶγ���˳��Caption,CommandLine,KernelModeTime,ReadOperationCount, 
//                // ThreadCount,UserModeTime,WriteOperation 
//                String caption =substring(line, capidx, cmdidx - 1).trim(); 
//                String cmd = substring(line, cmdidx, kmtidx - 1).trim(); 
//                if (cmd.indexOf("wmic.exe") >= 0) { 
//                    continue; 
//                } 
//                String s1 = substring(line, kmtidx, rocidx - 1).trim(); 
//                String s2 = substring(line, umtidx, wocidx - 1).trim(); 
//                if (caption.equals("System Idle Process") || caption.equals("System")) { 
//                    if (s1.length() > 0) 
//                        idletime += Long.valueOf(s1).longValue(); 
//                    if (s2.length() > 0) 
//                        idletime += Long.valueOf(s2).longValue(); 
//                    continue; 
//                } 
//                if (s1.length() > 0) 
//                    kneltime += Long.valueOf(s1).longValue(); 
//                if (s2.length() > 0) 
//                    usertime += Long.valueOf(s2).longValue(); 
//            } 
//            retn[0] = idletime; 
//            retn[1] = kneltime + usertime; 
//            return retn; 
//        } catch (Exception ex) { 
//            ex.printStackTrace(); 
//        } finally { 
//            try { 
//                proc.getInputStream().close(); 
//            } catch (Exception e) { 
//                e.printStackTrace(); 
//            } 
//        } 
//        return null; 
//    }
//	   private static String substring(String src, int start_idx, int end_idx) { 
//	   byte[] b = src.getBytes(); 
//	   String tgt = ""; 
//	   for (int i = start_idx; i <= end_idx; i++) { 
//	    tgt += (char) b[i]; 
//	   } 
//	   return tgt; 
//	  } 
//	public static String getMemory()
//	{
//		String str=null;
//		OperatingSystemMXBean osmxb=(OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
//	    // �ܵ������ڴ�+�����ڴ�
//	    long totalvirtualMemory = Runtime.getRuntime().totalMemory();
//	    System.out.println(totalvirtualMemory);
//	    System.out.println("�����ڴ�MB "+totalvirtualMemory);
//	    //totalvirtualMemory = osmxb.getCommittedVirtualMemorySize();
//	    //System.out.println(totalvirtualMemory);
//	    // ʣ��������ڴ�
//	    long freePhysicalMemorySize = Runtime.getRuntime().freeMemory();
//	    System.out.println("�����ڴ�MB "+freePhysicalMemorySize);
//	    Double compare=(Double)(1-freePhysicalMemorySize*1.0/totalvirtualMemory)*100;
//	     str=compare.intValue()+"%";
//	     System.out.println(str);
//	    return str;
//	}
//	public String getIPAddress() throws IOException
//	{
//		String encording="UTF-8";
////		File file=new File("IP.txt");
////		InputStreamReader read=new InputStreamReader(new FileInputStream(file),encording);
////		FileOutputStream fos=new FileOutputStream(file);
////		BufferedReader bufferedReader=new BufferedReader(read);
//		String ip=InetAddress.getLocalHost().getHostAddress();
//		System.out.println(ip);
//		return ip;
//	}
	
//	  public String getDefaultIpAddress() {  
//	        String address = null;  
//	        try {  
//	            address = InetAddress.getLocalHost().getHostAddress();  
//	            // û�г����쳣��������ȡ����IPʱ�����ȡ���Ĳ�������ѭ�ص�ַʱ�ͷ���  
//	            // ������ͨ��Sigar���߰��еķ�������ȡ  
//	            if (!NetFlags.LOOPBACK_ADDRESS.equals(address)) {  
//	                return address;  
//	            }  
//	        } catch (UnknownHostException e) {  
//	            // hostname not in DNS or /etc/hosts  
//	        }  
//	        Sigar sigar = new Sigar();  
//	        try {  
//	            address = sigar.getNetInterfaceConfig().getAddress();  
//	        } catch (SigarException e) {  
//	            address = NetFlags.LOOPBACK_ADDRESS;  
//	        } finally {  
//	            sigar.close();  
//	        }  
//	        return address;  
//	    }  
//	public void testNetIfList() throws Exception {  
//        Sigar sigar = new Sigar();  
//        String ifNames[] = sigar.getNetInterfaceList();  
//        for (int i = 0; i < ifNames.length; i++) {  
//            String name = ifNames[i];  
//            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);  
//            print("\nname = " + name);// �����豸��  
//            print("Address = " + ifconfig.getAddress());// IP��ַ  
//            print("Netmask = " + ifconfig.getNetmask());// ��������  
//            if ((ifconfig.getFlags() & 1L) <= 0L) {  
//                print("!IFF_UP...skipping getNetInterfaceStat");  
//                continue;  
//            }  
//            try {  
//
//
//                NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);  
//            	System.out.println(ifstat.getSpeed());
//                print("RxPackets = " + ifstat.getRxPackets());// ���յ��ܰ�����  
//                print("TxPackets = " + ifstat.getTxPackets());// ���͵��ܰ�����  
//                print("RxBytes = " + ifstat.getRxBytes());// ���յ������ֽ���  
//                print("TxBytes = " + ifstat.getTxBytes());// ���͵����ֽ���  
//                print("RxErrors = " + ifstat.getRxErrors());// ���յ��Ĵ������  
//                print("TxErrors = " + ifstat.getTxErrors());// �������ݰ�ʱ�Ĵ�����  
//                print("RxDropped = " + ifstat.getRxDropped());// ����ʱ�����İ���  
//                print("TxDropped = " + ifstat.getTxDropped());// ����ʱ�����İ���  
//                long x=ifstat.getRxBytes();
//                long y=ifstat.getTxBytes();
//                long z=x+y;
//                double t=z*8*100/ifstat.getSpeed();
//                System.out.println(t);
//            } catch (SigarNotImplementedException e) {  
//            } catch (SigarException e) {  
//                print(e.getMessage());  
//            }  
//        }  
//    }  
  
    void print(String msg) {  
        System.out.println(msg);  
    }  
}
