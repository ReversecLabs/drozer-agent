package com.reversec.jsolar.api.transport;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

public class SocketTransportTest {
    @Test
    public void closesSocketAfterStreamInitializationFails() {
        Socket socket = new Socket() {
            @Override
            public InputStream getInputStream() throws IOException {
                throw new IOException("stream initialization failed");
            }
        };
        SocketTransport transport = new SocketTransport(socket);
        transport.close();
        assertTrue(socket.isClosed());
        transport.close();
    }

    @Test
    public void closesAConnectedSocket() throws Exception {
        InetAddress loopback = InetAddress.getLoopbackAddress();
        try (ServerSocket server = new ServerSocket(0, 1, loopback);
             Socket socket = new Socket(loopback, server.getLocalPort());
             Socket peer = server.accept()) {
            InputStream input = socket.getInputStream();
            new SocketTransport(socket).close();
            assertTrue(socket.isClosed());
            assertThrows(IOException.class, () -> input.read());
        }
    }
}
