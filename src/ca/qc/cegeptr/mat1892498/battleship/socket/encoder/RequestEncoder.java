package ca.qc.cegeptr.mat1892498.battleship.socket.encoder;

import ca.qc.cegeptr.mat1892498.battleship.socket.Client;
import ca.qc.cegeptr.mat1892498.battleship.socket.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

public class RequestEncoder extends MessageToByteEncoder<Response> {

    private final Charset charset = Charset.forName("UTF-8");

    protected void encode(ChannelHandlerContext channelHandlerContext, Response requestData, ByteBuf byteBuf) throws Exception {
        String json = Client.GSON.toJson(requestData);
        byteBuf.writeInt(json.length());
        byteBuf.writeCharSequence(json, charset);
    }
}
