package example;

import com.joegaudet.remote.RemoteObject;

public interface Matrix extends RemoteObject{
	public Matrix product(Matrix matrix);
	public boolean equals(Matrix matrix);
	public double get(int i, int j);
	public Matrix parallelProduct(int threadCount, Matrix matrix);
}