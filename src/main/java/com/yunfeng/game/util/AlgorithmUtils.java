package com.yunfeng.game.util;

import java.nio.ByteBuffer;

public class AlgorithmUtils {
    //byte 与 int 的相互转换
    public static byte intToByte(int x) {
        return (byte) x;
    }

    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }

    public static void main(String[] args) {
        byte[] b = intToByteArray(923);
        System.out.println(byteArrayToInt(b));

    }

    //byte 数组与 int 的相互转换
    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

//    private static ByteBuffer buffer = ByteBuffer.allocate(8);

    //byte 数组与 long 的相互转换
    public static byte[] longToBytes(ByteBuffer buffer, long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(ByteBuffer buffer, byte[] bytes) {
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }
}
