import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class Client {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("\tusage: java Client <remote_hostname>");
			System.exit(1);
		}

		String remoteName = "//" + args[0] + ":" + Registry.REGISTRY_PORT + "/remote";

		IfaceRemoteFileManager remoteFileManager;

		try {
			remoteFileManager = (IfaceRemoteFileManager) Naming.lookup(remoteName);
			System.out.println(remoteName);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
			return;
		}

		int bufferSize = 100;

		byte[] buffer = new byte[bufferSize];
		File file;
		OutputStream os;
		int readSize;
		int readOffset = 0;

		try {
			file = new File("./local-copy.txt");
			file.createNewFile();
			os = new FileOutputStream(file, true);

			while ((readSize = remoteFileManager.read("remote-original.txt", readOffset, bufferSize, buffer)) > 0) {
				os.write(buffer, 0, readSize);
				readOffset += readSize;
			}

			os.close();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		InputStream is;

		try {
			is = new FileInputStream("./local-copy.txt");
			byte[] fileContent = is.readAllBytes();
			is.close();
			remoteFileManager.write("remote-copy.txt", fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
