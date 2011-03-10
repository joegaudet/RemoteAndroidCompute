import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class DumbServer {

	public static void main(String[] args) throws IOException {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 1099);
		ssc.socket().bind(isa);
		int output = 0;
		for (;;) {
			SocketChannel sc = ssc.accept();
			System.out.println("Accepting hannel");
			FileChannel out = new FileOutputStream(new File(output++ + ".out")).getChannel();
			
			try {
				int amount = 0;
				int start = 0;
				while((amount = (int) out.transferFrom(sc, start, 2048)) != 0) start += amount;
			} finally {
				// Make sure we close the channel (and hence the socket)
				sc.close();
			}
		}
	}
}
