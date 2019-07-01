import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class Server {

	public static void main(String args[]) throws RemoteException {
		String remoteName = "//localhost:" + Registry.REGISTRY_PORT + "/remote";

		IfaceRemoteFileManager remoteFileManager = new RemoteFileManager();

		try {
			Naming.rebind(remoteName, remoteFileManager);
			System.out.println(remoteName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
