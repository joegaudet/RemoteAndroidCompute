package com.joegaudet.remote.serialize;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.ObjectField;
import com.joegaudet.remote.fields.RemoteableField;

public class RemoteObjectDeserializer {

	private ByteBuffer buffer = ByteBuffer.allocate(4);
	private Map<Integer, RemoteObject> objectMap;

	public RemoteObjectDeserializer() {
		this.objectMap = new HashMap<Integer, RemoteObject>();
	}

	public RemoteObject readObjectFromChannel(ReadableByteChannel channel) throws IOException, ClassNotFoundException {
		buffer.rewind();
		channel.read(buffer);
		buffer.rewind();

		int schemaLength = buffer.getInt();
		ByteBuffer schemaBuffer = ByteBuffer.allocate(schemaLength);
		channel.read(schemaBuffer);
		schemaBuffer.rewind();

		RemoteObject retval = null;
		Set<RemoteObject> objects = new HashSet<RemoteObject>();
		while (schemaBuffer.hasRemaining()) {

			// this object
			int size = schemaBuffer.getInt();
			// the remaing we want to drill to for this object
			int offset = schemaBuffer.remaining() - size + 4;
			int thisHashCode = schemaBuffer.getInt();

			try {
				RemoteObject remoteObject = (loadClass(schemaBuffer)).getConstructor().newInstance();
				
				remoteObject.deserialize(schemaBuffer);
				
				// deserialize the fields that came along
				while (schemaBuffer.remaining() != offset) {
					int hashCode = schemaBuffer.getInt();

					RemoteableField<?> fieldForHashCode = remoteObject.fieldForHashCode(hashCode);

					if (fieldForHashCode != null) {
						fieldForHashCode.deserialize(schemaBuffer);
					}
				}

				objectMap.put(thisHashCode, remoteObject);
				objects.add(remoteObject);
				
				if (retval == null) {
					retval = remoteObject;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for(RemoteObject object: objects){
			for(ObjectField<?> field : object.getObjectFields()){
				if(field.isDirty()){
					field.setWithCast(objectMap.get(field.getTempHashCode()));
					field.setTempHashCode(0);
				}
			}
		}
		return retval;
	}

	public RemoteObject readObjectFromByteBuffer(ByteBuffer buffer) throws IOException, ClassNotFoundException {
		int schemaLength = buffer.getInt();
		RemoteObject retval = null;
		Set<RemoteObject> objects = new HashSet<RemoteObject>();
		int schemaOffset = buffer.remaining() - schemaLength;

		while (buffer.remaining() != schemaOffset) {
			// this object
			int size = buffer.getInt();
			// the remaing we want to drill to for this object
			int offset = buffer.remaining() - size + 4;
			int thisHashCode = buffer.getInt();

			try {
				RemoteObject remoteObject = (loadClass(buffer)).getConstructor().newInstance();
				
				remoteObject.deserialize(buffer);
				
				// deserialize the fields that came along
				while (buffer.remaining() != offset) {
					int hashCode = buffer.getInt();

					RemoteableField<?> fieldForHashCode = remoteObject.fieldForHashCode(hashCode);
					if (fieldForHashCode != null) {
						fieldForHashCode.deserialize(buffer);
					}
				}

				objectMap.put(thisHashCode, remoteObject);
				objects.add(remoteObject);
				
				if (retval == null) {
					retval = remoteObject;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for(RemoteObject object: objects){
			for(ObjectField<?> field : object.getObjectFields()){
				if(field.isDirty()){
					field.setWithCast(objectMap.get(field.getTempHashCode()));
					field.setTempHashCode(0);
				}
			}
		}
		return retval;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends RemoteObject> T readObjectFromChannel(FileChannel channel, Class<T> class1) throws IOException, ClassNotFoundException {
		return (T) readObjectFromChannel(channel);
	}

	@SuppressWarnings("unchecked")
	private Class<? extends RemoteObject> loadClass(ByteBuffer schemaBuffer) throws ClassNotFoundException {
		int classNameSize = schemaBuffer.getInt();
		byte[] stringArray = new byte[classNameSize];
		schemaBuffer.get(stringArray);
		String className = new String(stringArray);
		Class<? extends RemoteObject> klass = (Class<? extends RemoteObject>) Class.forName(className);
		return klass;
	}

}
