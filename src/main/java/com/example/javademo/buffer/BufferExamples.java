package com.example.javademo.buffer;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousByteChannel;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;
import java.util.concurrent.Future;

public class BufferExamples {
    @Test
    public void gen() throws IOException {
        Random r = new Random();
        String fileName = "word";
        int bufSize = 4 * 1024;
        BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(fileName), bufSize);
//        FileOutputStream fout = new FileOutputStream(fileName);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            for (int j = 0; j < 5; j++) {
                fout.write(97 + r.nextInt(5));
            }
            fout.write(' ');
        }
        fout.close();
        System.out.println(System.currentTimeMillis() - start);
    }
    @Test
    public void read_test() throws IOException {
        String fileName = "word";
        FileInputStream in = new FileInputStream(fileName);
        long start = System.currentTimeMillis();
        int b;
        while ((b = in.read()) != 1) {

        }
        in.close();
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    @Test
    public void read_test_withBuffer() throws IOException {
        String fileName = "word";
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));
        long start = System.currentTimeMillis();
        int b;
        byte[] buf = new byte[4 * 1024];
        while ((b = in.read(buf)) != -1) {

        }
        in.close();
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    @Test
    public void read_test_nio() throws IOException {
        String fileName = "word";
        FileChannel channel = new FileInputStream(fileName).getChannel();
        ByteBuffer buff = ByteBuffer.allocate(1024 * 4);
        long start = System.currentTimeMillis();
        while (channel.read(buff) != -1) {
            //读取数据
            buff.flip();
            System.out.println(new String(buff.array()));
            buff.clear();
        }

        channel.close();
        System.out.format("%dms\n", System.currentTimeMillis() -start);
    }

    public void test_chinese() {

    }
    @Test
    public void test_async_read() throws Exception {
        String fileName = "word";
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(fileName), StandardOpenOption.READ);
        ByteBuffer bbuf = ByteBuffer.allocate(4 * 1024);
        Future<Integer> operation = channel.write(bbuf, 0);
        while (!operation.isDone());
        bbuf.flip();
        Integer integer = operation.get();
    }

    public void test_async_read_completion() throws IOException {
        String fileName = "word";
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(fileName), StandardOpenOption.READ);


    }


}
