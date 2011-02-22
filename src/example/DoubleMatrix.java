//package example;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.channels.Channel;
//import java.nio.channels.FileChannel;
//import java.nio.channels.SocketChannel;
//import java.util.Random;
//import java.util.concurrent.BrokenBarrierException;
//import java.util.concurrent.CyclicBarrier;
//
//import com.joegaudet.remote.Remote;
//
//public class DoubleMatrix implements Matrix {
//
//	private double[][] data;
//	private int n;
//
//	public DoubleMatrix(int n) {
//		this.n = n;
//		data = new double[n][n];
//	}
//
//	public DoubleMatrix(double[][] matrix) {
//		this.data = matrix;
//		this.n = matrix.length;
//	}
//	
//	public DoubleMatrix(){
//		
//	}
//
//	@Override
//	@Remote
//	public Matrix product(Matrix matrix) {
//		System.out.print("\nsp, \t");
//		long time = System.currentTimeMillis();
//		double[][] output = new double[n][n];
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < n; j++) {
//				double value = 0;
//				for (int k = 0; k < n; k++) {
//					value += data[i][k] * matrix.get(k, j);
//				}
//				output[i][j] = value;
//			}
//		}
//		System.out.print(matrix.size() + ",\t" + (System.currentTimeMillis() - time));
//		DoubleMatrix result = new DoubleMatrix(output);
//		return result;
//	}
//
//	@Override
//	@Remote
//	public Matrix parallelProduct(int threadCount, final Matrix matrix) {
//		if (threadCount > matrix.size())
//			return null;
//		System.out.print("\npp, \t");
//		long time = System.currentTimeMillis();
//		
//		int strideLength = (int) Math.floor(n / threadCount);
//		int lastStride = n - threadCount * strideLength;
//		lastStride = (lastStride == 0 ) ? strideLength : lastStride; 
//		
//		final double[][] output = new double[n][n];
//		Thread[] threads = new Thread[threadCount];
//
//		final CyclicBarrier barrier = new CyclicBarrier(threadCount + 1);
//		
//		for (int threadIndex = 0; threadIndex < threadCount; threadIndex++) {
//			final int thisStrideLength = threadIndex != threadCount - 1 ? strideLength : lastStride;
//			final int start = threadIndex * strideLength;
////			final int thisThreadIndex = threadIndex;
//			
//			threads[threadIndex] = new Thread() {
//				public void run() {
//					
////					System.out.println("Thread : " + thisThreadIndex + " : " + start + " for: " + thisStrideLength);
//					
//					for (int i = start; i < (start + thisStrideLength); i++) {
//						for (int j = 0; j < n; j++) {
//							double value = 0;
//							for (int k = 0; k < n; k++) {
//								value += data[i][k] * matrix.get(k, j);
//							}
//							output[i][j] = value;
//						}
//					}
//					try {
//						barrier.await();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					} catch (BrokenBarrierException e) {
//						e.printStackTrace();
//					}
//				};
//			};
//		}
//		
//		for(Thread thread: threads){
//			thread.start();
//		}
//		
//		try {
//			barrier.await();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (BrokenBarrierException e) {
//			e.printStackTrace();
//		}
//		System.out.print(matrix.size() + ",\t" + (System.currentTimeMillis() - time));
//		
//		DoubleMatrix result = new DoubleMatrix(output);
//		return result;
//	}
//
//	@Override
//	public boolean equals(Matrix matrix) {
//		boolean retval = true;
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < n; j++) {
//				retval &= matrix.get(i, j) == data[i][j];
//				if (!retval)
//					break;
//			}
//			if (!retval)
//				break;
//		}
//		return retval;
//	}
//
//	public static DoubleMatrix getIdentity(int n) {
//		double[][] data = new double[n][n];
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < n; j++) {
//				double value = 0;
//				if (i == j) {
//					value = 1;
//				}
//				data[i][j] = value;
//			}
//		}
//
//		return new DoubleMatrix(data);
//	}
//
//	public static DoubleMatrix getRandom(int n) {
//		double[][] data = new double[n][n];
//		Random random = new Random();
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < n; j++) {
//				data[i][j] = random.nextDouble();
//			}
//		}
//		return new DoubleMatrix(data);
//	}
//
//	@Override
//	public String toString() {
//		StringBuilder builder = new StringBuilder();
//		for (int i = 0; i < n; i++) {
//			builder.append("[ ");
//			for (int j = 0; j < n; j++) {
//				builder.append(get(i, j)).append("\t");
//			}
//			builder.append(" ]\n");
//		}
//		return builder.toString();
//	}
//
//	public static void main(String[] args) throws FileNotFoundException, IOException {
//		int[] sizes = {10, 20, 30, 40, 50, 100, 200, 300, 400, 500, 1000};
//		for(int i = 0; i < sizes.length; i++){
//			File file = new File(sizes[i] + ".obj");
//			System.err.println("Transmitting file (" + file.getName() + "): " + file.length());
//			long time = System.currentTimeMillis();
//			FileChannel in = new FileInputStream(file).getChannel();
//			SocketChannel channel = null; 
//			try{
//				channel = SocketChannel.open(new InetSocketAddress("50.56.73.9", 1099));
//				in.transferTo(0, file.length(), channel);
//			}
//			finally{
//				if(channel != null) channel.close();
//			}
//			double took = (System.currentTimeMillis() - time) / 1000; // s
//			System.out.println("Took: " + took + "s byteRate: " + (file.length() / took / (1024 * 1024)));
////			Matrix identity = DoubleMatrix.getIdentity(size);
////			
////			long time = System.currentTimeMillis();
////			random.parallelProduct(4, identity);
////			System.out.print("\nrpp, \t" + size + ", \t" + (System.currentTimeMillis() - time));
////			
////			time = System.currentTimeMillis();
////			random.product(identity);
////			System.out.print("\nrsp, \t" + size + ", \t" + (System.currentTimeMillis() - time));
////			
////			time = System.currentTimeMillis();
////			random2.product(identity);
////			System.out.println("lsp, \t\t" + size + ", " + (System.currentTimeMillis() - time));
//		}
//	}
//
//	@Override
//	public double get(int i, int j) {
//		return data[i][j];
//	}
//
//	@Override
//	public int size() {
//		return data.length;
//	}
//
//	@Override
//	public void writeRemoteObject(Channel channel) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void readRemoteObject(Channel channel) {
//		// TODO Auto-generated method stub
//		
//	}
//}
