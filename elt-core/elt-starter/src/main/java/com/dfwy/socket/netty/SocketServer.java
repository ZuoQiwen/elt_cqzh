package com.dfwy.socket.netty;

import com.dfwy.common.utils.SpringUtils;
import com.dfwy.socket.handler.SocketServerHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 * date: 2020/5/15 16:05
 *
 * @author zuoqiwen
 */
@Slf4j
public class SocketServer extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String str = (String) msg;
        log.info("[SERVER]客户端发送报文：" + str);
//        String xml="1213";
        SocketServerHandler socketServerHandler = SpringUtils.getBean(SocketServerHandler.class);
        String xml = socketServerHandler.handler(str);
        ctx.writeAndFlush(SocketUtils.response(xml));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("socket接受参数异常",cause);
        ctx.fireExceptionCaught(cause);
    }
}
