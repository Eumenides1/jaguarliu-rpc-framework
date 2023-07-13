package com.jaguarliu.rpc.framework.core.common;

import java.io.Serializable;
import java.util.Arrays;

import static com.jaguarliu.rpc.framework.core.common.constants.RpcConstants.MAGIC_NUMBER;

public class RpcProtocol implements Serializable {
    private static final long serialVersionUID = -5842578441934800777L;
    // 魔法数，主要是在做服务通讯的时候定义的一个安全检测，确认当前请求的协议是否合法。
    private short magicNumber = MAGIC_NUMBER;
    // 协议传输核心数据的长度。这里将长度单独拎出来设置有个好处，当服务端的接收能力有限，可以对该字段进行赋值。当读取到的网络数据包中的contentLength字段已经超过预期值的话，就不会去读取content字段。
    private int contentLength;
    // 核心的传输数据，这里核心的传输数据主要是请求的服务名称，请求服务的方法名称，请求参数内容。为了方便后期扩展，这些核心的请求数据我都统一封装到了
    private byte[] content;

    public RpcProtocol(byte[] content) {
        this.contentLength = content.length;
        this.content = content;
    }

    public short getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(short magicNumber) {
        this.magicNumber = magicNumber;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "RpcProtocol{" +
                "contentLength=" + contentLength +
                ", content=" + Arrays.toString(content) +
                '}';
    }


}
