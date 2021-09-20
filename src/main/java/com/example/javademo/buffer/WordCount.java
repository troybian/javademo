package com.example.javademo.buffer;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;

public class WordCount {

    private static final ForkJoinPool pool = new ForkJoinPool();
    @Test
    public void compareWithSingle() throws IOException {
        String fileName = "word";
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));
        long start = System.currentTimeMillis();
        int len;
        HashMap<String, Integer> total = new HashMap<>();
        byte[] buf = new byte[512];
        while ((len = in.read(buf)) != -1) {
            byte[] bytes = Arrays.copyOfRange(buf, 0, len);
            String str = new String(bytes);
            HashMap<String, Integer> hashMap = countByString(str);
            for (Map.Entry<String,Integer> entry: hashMap.entrySet()) {
                incKey(entry.getKey(), total, entry.getValue());
            }
        }
        in.close();
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(System.currentTimeMillis() - start + "ms");
        System.out.println(total.size());
    }

    @Test
    public void comapreWithMulti() throws ExecutionException, InterruptedException {
        run("word",20*1024*1024);
    }


    private void run(String fileName, int chunkSize) throws ExecutionException, InterruptedException {
        File file = new File(fileName);
        long fileSize = file.length();
        long position = 0;
        ArrayList<Future<HashMap<String, Integer>>> tasks = new ArrayList<>();
        while (position < fileSize) {
            long next = Math.min(position + chunkSize, fileSize);
            MultiWC wc = new MultiWC(position, next, fileName);
            Future<HashMap<String, Integer>> task = pool.submit(wc);
            position = next;
            tasks.add(task);
        }
        System.out.format("split to %d tasks \n", tasks.size());
        HashMap<String, Integer> totalTask = new HashMap<>();
        for (Future task : tasks) {
            Map<String, Integer> map = (Map<String, Integer>)task.get();
            for (Map.Entry<String, Integer> entry: map.entrySet()) {
                incKey(entry.getKey(), totalTask, entry.getValue());
            }
        }
        System.out.println("total:" + totalTask.size());
    }
    static class MultiWC implements Callable<HashMap<String, Integer>> {
        private long start;
        private long end;
        private String fileName;

        public MultiWC() {
        }
        public MultiWC(long start, long end, String fileName) {
            this.start = start;
            this.end = end;
            this.fileName = fileName;
        }
        @Override
        public HashMap<String, Integer> call() throws Exception {
            FileChannel channel = new RandomAccessFile(fileName, "rw").getChannel();
            MappedByteBuffer mbuf = channel.map(FileChannel.MapMode.READ_ONLY, this.start, this.end - this.start);
            String str = StandardCharsets.UTF_8.decode(mbuf).toString();
            return countByString(str);
        }
    }

    private static HashMap<String, Integer> countByString(String str) {
        HashMap<String, Integer> map = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(str);
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            incKey(word, map, 1);
        }
        return map;
    }

    private static void incKey(String word, Map<String,Integer> map, int n) {
        if (map.containsKey(word)) {
            map.put(word, map.get(word) + n);
        }else {
            map.put(word, n);
        }
    }
}
