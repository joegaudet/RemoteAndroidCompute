package example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import com.joegaudet.remote.AbstractRemoteObject;
import com.joegaudet.remote.Remote;
import com.joegaudet.remote.RemoteProxyObject;
import com.joegaudet.remote.fields.arrays2d.Double2DArrayField;

public class DoubleMatrix extends AbstractRemoteObject implements Matrix {

	private Double2DArrayField data;

	public DoubleMatrix(int n) {
		super();
		data.set(new double[n][n]);
	}

	public DoubleMatrix(double[][] matrix) {
		super();
		this.data.set(matrix);
	}

	public DoubleMatrix() {
		super();
	}

	@Override
	@Remote
	public Matrix product(DoubleMatrix matrix) {
		long time = System.currentTimeMillis();
		int n = data.getN();
		double[][] output = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				double value = 0;
				for (int k = 0; k < n; k++) {
					value += data.get(i, k) * matrix.get(k, j);
				}
				output[i][j] = value;
			}
		}
		DoubleMatrix result = new DoubleMatrix(output);
		return result;
	}
	
	@Override
	@Remote
	public Matrix pow(int amount) {
		Matrix result = this;
		for(int i = 0; i < amount; i++){
			result = result.product(this);
		}
		return result;
	}

	@Override
	@Remote
	public Matrix parallelProduct(int threadCount, final Matrix matrix) {
		if (threadCount > matrix.size())
			return null;
//		System.out.print("\npp, \t");
		long time = System.currentTimeMillis();
		final int n = data.getN();

		int strideLength = (int) Math.floor(n / threadCount);
		int lastStride = n - threadCount * strideLength;
		lastStride = (lastStride == 0) ? strideLength : lastStride;

		final double[][] output = new double[n][n];
		Thread[] threads = new Thread[threadCount];

		final CyclicBarrier barrier = new CyclicBarrier(threadCount + 1);

		for (int threadIndex = 0; threadIndex < threadCount; threadIndex++) {
			final int thisStrideLength = threadIndex != threadCount - 1 ? strideLength : lastStride;
			final int start = threadIndex * strideLength;
			// final int thisThreadIndex = threadIndex;

			threads[threadIndex] = new Thread() {
				public void run() {

					// System.out.println("Thread : " + thisThreadIndex + " : "
					// + start + " for: " + thisStrideLength);

					for (int i = start; i < (start + thisStrideLength); i++) {
						for (int j = 0; j < n; j++) {
							double value = 0;
							for (int k = 0; k < n; k++) {
								value += data.get(i, k) * matrix.get(k, j);
							}
							output[i][j] = value;
						}
					}
					try {
						barrier.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
				};
			};
		}

		for (Thread thread : threads) {
			thread.start();
		}

		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
//		System.out.print(matrix.size() + ",\t" + (System.currentTimeMillis() - time));

		DoubleMatrix result = new DoubleMatrix(output);
		return result;
	}

	@Override
	public boolean equals(Matrix matrix) {
		boolean retval = true;
		int n = data.getN();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				retval &= matrix.get(i, j) == data.get(i, j);
				if (!retval)
					break;
			}
			if (!retval)
				break;
		}
		return retval;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DoubleMatrix))
			return false;
		return this.equals((DoubleMatrix) obj);
	}

	public static DoubleMatrix getIdentity(int n) {
		double[][] data = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				double value = 0;
				if (i == j) {
					value = 1;
				}
				data[i][j] = value;
			}
		}

		return new DoubleMatrix(data);
	}

	public static DoubleMatrix getRandom(int n) {
		double[][] data = new double[n][n];
		Random random = new Random();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				data[i][j] = random.nextDouble();
			}
		}
		return new DoubleMatrix(data);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		int n = this.data.getN();
		for (int i = 0; i < n; i++) {
			builder.append("[ ");
			for (int j = 0; j < n; j++) {
				builder.append(get(i, j)).append("\t");
			}
			builder.append(" ]\n");
		}
		return builder.toString();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {

		int[] sizes = { 10, 20, 30, 40, 50, 100, 200, 500, 1000 };
		for (int i = 0; i < sizes.length; i++) {
			DoubleMatrix identity = DoubleMatrix.getIdentity(sizes[i]);
			Matrix newInstance = RemoteProxyObject.newInstance(DoubleMatrix.getRandom(sizes[i]), Matrix.class);
			System.out.println("Trying Size: " + sizes[i]);

			long time = System.currentTimeMillis();
			newInstance.product(identity);
			
			System.out.println("Took: " + (System.currentTimeMillis() - time));
		}
	}

	@Override
	public double get(int i, int j) {
		return data.get(i, j);
	}

}
