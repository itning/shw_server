package top.yunshu.shw.server.config;

import ch.qos.logback.core.OutputStreamAppender;
import top.yunshu.shw.server.controller.admin.LogWebSocket;

/**
 * WebSocket OutputStreamAppender
 *
 * @author itning
 */
public class WebSocketOutputStreamAppender<E> extends OutputStreamAppender<E> {
    @Override
    public void start() {
        setOutputStream(LogWebSocket.getOutputStream());
        super.start();
    }
}
