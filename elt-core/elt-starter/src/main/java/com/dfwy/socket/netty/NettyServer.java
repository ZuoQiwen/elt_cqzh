package com.dfwy.socket.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 * date: 2020/5/15 16:00
 *
 * @author zuoqiwen
 */
@Slf4j
public class NettyServer {
    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public void start(){
        // 配置服务端的NIO线程组

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new FixLengthDecoder())
                                    .addLast(new StringDecoder(CharsetUtil.UTF_8))
                                    .addLast(new SocketServer());
                        }
                    });
            // 绑定端口，同步等待成功
            ChannelFuture channelFuture = serverBootstrap.bind("127.0.0.1",this.port).sync();
            log.info("socket服务器启动成功："+port);
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            log.error("socket服务器启动失败："+e);
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }



    public static void main(String[] args) throws Exception {
        new NettyServer(2222).start();
        System.out.println("[SEREVER]服务器启动");
    }
}
