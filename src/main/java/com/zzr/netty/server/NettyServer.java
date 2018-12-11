package com.zzr.netty.server;

import com.zzr.netty.codec.PacketDecoder;
import com.zzr.netty.codec.PacketEncoder;
import com.zzr.netty.codec.Spliter;
import com.zzr.netty.server.handler.LoginRequestHandler;
import com.zzr.netty.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;

public class NettyServer {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        /**
         * 启动服务端的时候有很多api可以调用，此处举几个常用的例子
         * handler();childHandler()
         * attr();childAttr()
         * option();childOption()
         * 可以发现都是一一对称的，未带child的api都是针对于服务端设置，带child都是对客户端的连接设置
         */
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //（1）线程模型
        serverBootstrap.group(bossGroup,workerGroup)
                //（2）IO模型
                .channel(NioServerSocketChannel.class)
                /**
                 * 服务端启动时候执行，不论是否启动成功都会触发，比如bind端口的时候，如若第一次绑定端口失败会触发
                 * 那尝试绑定第二次的时候也会触发
                 */
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {
                        System.out.println("服务端启动了");
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                //（3）连接读写处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
//                        nioSocketChannel.pipeline().addLast(new FirstServerHandler());
//                        nioSocketChannel.pipeline().addLast(new ServerHandler());
                        nioSocketChannel.pipeline().addLast(new Spliter());
                        nioSocketChannel.pipeline().addLast(new PacketDecoder());
                        nioSocketChannel.pipeline().addLast(new LoginRequestHandler());
                        nioSocketChannel.pipeline().addLast(new MessageRequestHandler());
                        nioSocketChannel.pipeline().addLast(new PacketEncoder());
                    }
                });
        bind(serverBootstrap,PORT);
    }

    private static void bind(ServerBootstrap serverBootstrap,final int port){
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("端口[" + port + "]绑定成功!");
                }else{
                    System.err.println("端口[" + port + "]绑定失败!");
                    bind(serverBootstrap,port+1);
                }
            }
        });
    }
}
