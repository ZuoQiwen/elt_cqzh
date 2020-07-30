package com.dfwy.socket.netty;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Description:  自定义解码器
 * date: 2020/5/20 9:20
 *
 * @author zuoqiwen
 */
@Slf4j
public class FixLengthDecoder extends ByteToMessageDecoder {
    public static final int HEADER_LENGTH = 5;


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        if (in.readableBytes() < HEADER_LENGTH) {
            return;
        }
        in.markReaderIndex();
        byte[] header = new byte[HEADER_LENGTH];
        in.readBytes(header);
        //获取报文长度
        int dataLength = SocketUtils.getLengthFromString(new String(header));
        if (dataLength < 0) {
            ctx.close();
        }
        //读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }

        byte[] body = new byte[dataLength];
        in.readBytes(body);
        String xml = new String(body);
        log.debug("[Decoder]socket请求原始报文："+xml);
        list.add(xml);
    }
}
