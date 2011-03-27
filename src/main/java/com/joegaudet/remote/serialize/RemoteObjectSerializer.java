package com.joegaudet.remote.serialize;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.Stack;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.ObjectField;
import com.joegaudet.remote.fields.RemoteableField;
import com.joegaudet.remote.visitors.OneTimeVisitor;
import com.joegaudet.remote.visitors.SchemaSizeVisitor;

public class RemoteObjectSerializer extends OneTimeVisitor {

	private ByteBuffer buffer;
	private Stack<RemoteObject> remoteObjects;

	public RemoteObjectSerializer() {
		remoteObjects = new Stack<RemoteObject>();
	}


	public void serialize(RemoteObject object, ByteBuffer buffer) {
		int schemaSize = new SchemaSizeVisitor(object).computeSchemaSize();
		this.buffer = buffer;
		writeToBuffer(object, schemaSize, buffer);
	}
	
	public ByteBuffer serialize(RemoteObject object) {
		int schemaSize = new SchemaSizeVisitor(object).computeSchemaSize();
		buffer = ByteBuffer.allocate(schemaSize + 4);
		writeToBuffer(object, schemaSize, buffer);
		return buffer;
	}


	private void writeToBuffer(RemoteObject object, int schemaSize, ByteBuffer buffer) {
		buffer.putInt(schemaSize);
		object.serialize(buffer);
		visitedHashCodes.add(object.hashCode());

		for (RemoteableField<?> field : object.getFields()) {
			if (field.isDirty()) {
				field.acceptVisitor(this);
			}
		}

		while (remoteObjects.size() != 0) {
			RemoteObject pop = remoteObjects.pop();

			pop.serialize(buffer);

			for (RemoteableField<?> field : pop.getFields()) {
				if (field.isDirty()) {
					field.acceptVisitor(this);
				}
			}
		}
	}

	public void writeObjectToChannel(RemoteObject object, WritableByteChannel channel) throws IOException {
		ByteBuffer buffer = serialize(object);
		buffer.rewind();
		channel.write(buffer);
	}

	@Override
	protected void visitPrimative(RemoteableField<?> visitable) {
		visitable.serialize(buffer);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void visitNewObject(RemoteableField<?> visitable) {
		visitable.serialize(buffer);
		RemoteObject remoteObject = ((ObjectField<RemoteObject>) visitable).get();
		if (remoteObject != null) {
			remoteObjects.push(remoteObject);
		}
	}

	@Override
	protected void visitOldObject(RemoteableField<?> visitable) {
		visitable.serialize(buffer);
	}

	@Override
	public boolean shouldVisitChildren(RemoteableField<?> field) {
		return false;
	}

}
