package com.jaguarliu.rpc.framework.core.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer extends Thread{

    ServerSocketChannel serverSocketChannel = null;
    Selector selector = null;

    SelectionKey selectionKey = null;

    public void initServer() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        // 设置为非阻塞模式,默认serverSocketChannel是采用了阻塞模式
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));

        selectionKey= serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }
    @Override
    public void run(){
        while (true) {
            try {
                //默认这里会堵塞
                int selectKey = selector.select();
                if (selectKey > 0) {
                    //获取到所有的处于就绪状态的channel，selectionKey中包含了channel的信息
                    Set<SelectionKey> keySet = selector.selectedKeys();
                    Iterator<SelectionKey> iter = keySet.iterator();

                    while (iter.hasNext()) {
                        SelectionKey selectionKey = iter.next();
                        //需要清空，防止下次重复处理
                        iter.remove();
                        //就绪事件，处理连接
                        if (selectionKey.isAcceptable()) {
                            accept(selectionKey);
                        }
                        //读事件，处理数据读取
                        if (selectionKey.isReadable()) {
                            read(selectionKey);
                        }
                        //写事件，处理写数据
                        if (selectionKey.isWritable()) {
                        }
                    }
                }


            }catch (IOException e) {
                e.printStackTrace();
                try {
                    serverSocketChannel.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public void accept(SelectionKey key) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println("conn is acceptable");
            socketChannel.configureBlocking(false);
            //将当前的channel交给selector对象监管，并且有selector对象管理它的读事件
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(SelectionKey selectionKey) {
        try {
            SocketChannel channel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(100);
            int len = channel.read(byteBuffer);
            if (len > 0) {
                byteBuffer.flip();
                byte[] byteArray = new byte[byteBuffer.limit()];
                byteBuffer.get(byteArray);
                System.out.println("NioSocketServer receive from client:" + new String(byteArray,0,len));
                selectionKey.interestOps(SelectionKey.OP_READ);
            }
        } catch (Exception e) {
            try {
                serverSocketChannel.close();
                selectionKey.cancel();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        NioServer server = new NioServer();
        server.initServer();
        server.start();
    }





}
