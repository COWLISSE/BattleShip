package ca.qc.cegeptr.mat1892498.battleship.socket;

import ca.qc.cegeptr.mat1892498.battleship.socket.decoder.ResponseDecoder;
import ca.qc.cegeptr.mat1892498.battleship.socket.encoder.RequestEncoder;
import ca.qc.cegeptr.mat1892498.battleship.socket.handler.ClientHandler;
import com.google.gson.Gson;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    public static ChannelHandlerContext SERVER = null;
    public static Gson GSON = new Gson();

    public Client() throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new RequestEncoder(), new ResponseDecoder(), new ClientHandler());
                }
            });
            ChannelFuture f = b.connect("51.254.161.172", 30000).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
