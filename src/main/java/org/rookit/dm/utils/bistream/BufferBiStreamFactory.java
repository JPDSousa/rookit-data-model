package org.rookit.dm.utils.bistream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.Map;

import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.factory.RookitFactory;

import com.google.inject.Inject;

@SuppressWarnings("javadoc")
public class BufferBiStreamFactory implements RookitFactory<BiStream> {

	@Inject
	private BufferBiStreamFactory() {}
	
	@Override
	public BiStream createEmpty() {
		return new BufferBiStream(1024);
	}

	@Override
	public BiStream create(Map<String, Object> data) {
		final int size = (int) data.get("size");
		return new BufferBiStream(size);
	}

	private class BufferBiStream implements BiStream {

		private ByteBuffer buffer;

		public BufferBiStream(int capacity) {
			super();
			this.buffer = ByteBuffer.allocate(capacity);
		}

		@Override
		public InputStream toInput() {
			return new ByteBufferReader(buffer);
		}

		@Override
		public OutputStream toOutput() {
			return new OutputStream() {

				@Override
				public void write(int b) throws IOException {
					try {
						buffer.put((byte) b);
					} catch (BufferOverflowException ex) {
						final int newBufferSize = buffer.capacity() * 2;
						growTo(newBufferSize);
						write(b);
					}
				}
			};
		}
		
		private void growTo(int min) {

			final int old = buffer.capacity();
			int newC = old << 1;
			if (newC - min < 0) {
				newC = min;
			}
			if (newC < 0) {
				if (min < 0) { // overflow
					throw new OutOfMemoryError();
				}
				newC = Integer.MAX_VALUE;
			}
			final ByteBuffer oldWrappedBuffer = buffer;
			if (buffer.isDirect()) {
				buffer = ByteBuffer.allocateDirect(newC);
			} else {
				buffer = ByteBuffer.allocate(newC);
			}
			oldWrappedBuffer.flip();
			buffer.put(oldWrappedBuffer);
		}

		@Override
		public boolean isEmpty() {
			return buffer.position() == 0;
		}

	}
	
	private class ByteBufferReader extends InputStream {

		private final ByteBuffer snapshot;
		
		private ByteBufferReader(ByteBuffer buffer) {
			super();
			this.snapshot = buffer.duplicate();
			snapshot.flip();
		}

		@Override
		public int read() throws IOException {
			if (snapshot == null) {
				throw new IOException("read on a closed InputStream");
			}

			if (snapshot.remaining() == 0) {
				return -1;
			}
			return snapshot.get();
		}

		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			len = Math.min(len, snapshot.remaining());
		    snapshot.get(b, off, len);
		    return len;
		}
		
		
		
	}
}
