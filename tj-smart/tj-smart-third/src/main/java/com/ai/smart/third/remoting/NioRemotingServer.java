package com.ai.smart.third.remoting;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class NioRemotingServer {
    static final int PORT = Integer.parseInt(System.getProperty("port", "8699"));
    public static void main(String[] args) throws Exception {
        // Configure SSL.


        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();

                            p.addLast(new StringEncoder(),new StringDecoder(),new DiscardServerHandler());
                        }
                    });

            // Bind and start to accept incoming connections.
//            InetSocketAddress address =new InetSocketAddress(8699);
            ChannelFuture f = b.bind("10.189.70.188",PORT).sync();
            InetSocketAddress addr = (InetSocketAddress) f.channel().localAddress();
            System.out.println(" addr : "+addr.getAddress().getHostAddress()+"   port : "+addr.getPort());
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
