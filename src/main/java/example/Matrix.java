package example;

import com.joegaudet.remote.RemoteObject;

public abstract class Matrix implements RemoteObject{
	public abstract Matrix product(Matrix matrix);
	public abstract boolean equals(Matrix matrix);
	public abstract double get(int i, int j);
	public abstract int objectSize();
	public abstract Matrix parallelProduct(int threadCount, Matrix matrix);
}