package ca.qc.cegeptr.mat1892498.battleship.socket.handler;

import ca.qc.cegeptr.mat1892498.battleship.BattleShip;
import ca.qc.cegeptr.mat1892498.battleship.socket.Client;
import ca.qc.cegeptr.mat1892498.battleship.socket.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Client.SERVER = ctx;
//        Response requestData = new Response("{'connection': true}");
//        requestData.json = Client.GSON.toJson(new ShipLocation());
//        ctx.writeAndFlush(requestData);
        BattleShip.sendPacket("{'connection': true}");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Client.SERVER = null;
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Response responseData = (Response)msg;
        System.out.println(responseData.toString());
        super.channelRead(ctx, msg);
    }
}