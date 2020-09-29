package org.incolo.springpulsar.core.sample;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;

public class CustomData extends GeneratedMessageV3 {
	public String f1;
	public Integer f2;

	public CustomData() {
	}

	public String getF1() {
		return f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}

	public Integer getF2() {
		return f2;
	}

	public void setF2(Integer f2) {
		this.f2 = f2;
	}

	@Override
	protected FieldAccessorTable internalGetFieldAccessorTable() {
		throw new UnsupportedOperationException("This should never get called. It is there to satisfy the contract");
	}

	@Override
	protected Message.Builder newBuilderForType(BuilderParent parent) {
		throw new UnsupportedOperationException("This should never get called. It is there to satisfy the contract");
	}

	@Override
	public Message.Builder newBuilderForType() {
		throw new UnsupportedOperationException("This should never get called. It is there to satisfy the contract");
	}

	@Override
	public Message.Builder toBuilder() {
		throw new UnsupportedOperationException("This should never get called. It is there to satisfy the contract");
	}

	@Override
	public Message getDefaultInstanceForType() {
		throw new UnsupportedOperationException("This should never get called. It is there to satisfy the contract");
	}
}
