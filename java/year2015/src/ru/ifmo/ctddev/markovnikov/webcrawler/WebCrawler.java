package ru.ifmo.ctddev.markovnikov.webcrawler;

import info.kgeorgiy.java.advanced.crawler.*;
import javafx.util.Pair;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Class that downloads websites recursively in parallel with the specified depth and returns all the files and links
 * that are downloaded during the bypass.
 *
 * @author nikita markovnikov
 * @see info.kgeorgiy.java.advanced.crawler.Crawler
 * @see info.kgeorgiy.java.advanced.crawler.URLUtils
 * @see info.kgeorgiy.java.advanced.crawler.Downloader
 * @see info.kgeorgiy.java.advanced.crawler.Document
 */
public class WebCrawler implements Crawler {
    private final Downloader downloader;
    private final ExecutorService downloadService;
    private final ExecutorService extractService;
    private final int perHost;
    private HashMap<String, IOException> mapForResult = new HashMap<>();

    /**
     * Constructor of the class.
     *
     * @param downloader  class to download
     * @param downloaders maximum number of downloads
     * @param extractors  maximum number of extracts
     * @param perHost     maximum number from the host
     * @see java.util.concurrent.Executors
     * @see info.kgeorgiy.java.advanced.crawler.Downloader
     */
    public WebCrawler(Downloader downloader, int downloaders, int extractors, int perHost) {
        this.downloader = downloader;
        this.downloadService = Executors.newFixedThreadPool(downloaders);
        this.extractService = Executors.newFixedThreadPool(extractors);
        this.perHost = perHost;
    }

    /**
     * Downloads pages and files starting with the given url.
     *
     * @param url   website to start the bypass
     * @param depth maximum depth of the website from the starting one
     * @return list of urls of website visited
     * @see java.util.concurrent.Semaphore
     * @see java.util.concurrent.ExecutorService
     * @see java.util.concurrent.ConcurrentHashMap
     * @see java.util.concurrent.ConcurrentLinkedQueue
     */
    @Override
    public Result download(String url, int depth) {
        final Semaphore counter = new Semaphore(Integer.MAX_VALUE);
        final PriorityBlockingQueue<Pair<String, Integer>> priorityBlockingQueue =
                new PriorityBlockingQueue<>(100, new Comparator<Pair<String, Integer>>() {
                    @Override
                    public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                        return Integer.compare(o2.getValue(), o1.getValue());
                    }
                });

        final ConcurrentMap<String, Semaphore> hostsMap = new ConcurrentHashMap<>();
        final ConcurrentMap<String, Object> visited = new ConcurrentHashMap<>();
        final ConcurrentMap<String, Object> recievedMap = new ConcurrentHashMap<>();
        final ConcurrentMap<String, Object> res = new ConcurrentHashMap<>();
        try {
            counter.acquire();
            priorityBlockingQueue.add(new Pair<>(url, depth));
            downloadService.submit(() -> this.download(counter, res, priorityBlockingQueue, hostsMap, visited, recievedMap));
        } catch (InterruptedException ignored) {
            return null;
        }

        try {
            counter.acquire(Integer.MAX_VALUE);
        } catch (InterruptedException ignored) {
        }

        return new Result(res.keySet().stream().collect(Collectors.toList()), mapForResult);
    }

    /**
     * Closes the class -- stops the class from visiting websites.
     */
    @Override
    public void close() {
        downloadService.shutdown();
        if (!downloadService.isShutdown()) {
            try {
                downloadService.awaitTermination(2, TimeUnit.SECONDS);
            } catch (InterruptedException ignored) {
            } finally {
                downloadService.shutdownNow();
            }
        }
        extractService.shutdown();
        if (!extractService.isShutdown()) {
            try {
                extractService.awaitTermination(2, TimeUnit.SECONDS);
            } catch (InterruptedException ignored) {
            } finally {
                extractService.shutdownNow();
            }
        }
    }

    private void download(final Semaphore counter, final ConcurrentMap<String, Object> res, final PriorityBlockingQueue<Pair<String, Integer>> priorityBlockingQueue,
                          final ConcurrentMap<String, Semaphore> hostsMap, final ConcurrentMap<String, Object> visited,
                          final ConcurrentMap<String, Object> recievedMap) {
        try {
            if (!priorityBlockingQueue.isEmpty()) {
                Pair<String, Integer> pair = priorityBlockingQueue.poll();
                String url = pair.getKey();
                int depth = pair.getValue();
                if (!visited.containsKey(url)) {
                    try {
                        String host = URLUtils.getHost(url);
                        synchronized (hostsMap) {
                            hostsMap.putIfAbsent(host, new Semaphore(perHost));
                        }

                        if (hostsMap.get(host).tryAcquire()) {
                            try {
                                counter.acquire();
                                visited.put(url, new Object());
                                Document doc = downloader.download(url);
                                res.put(url, new Object());
                                extractService.submit(() ->
                                        extract(depth, res, doc, visited, counter, hostsMap, priorityBlockingQueue, recievedMap));
                            } catch (IOException e) {
                                mapForResult.put(url, e);
                                counter.release();

                            } catch (InterruptedException e) {
                                System.err.println("It's impossible to download " + url);
                            } finally {
                                hostsMap.get(host).release();
                                if (!priorityBlockingQueue.isEmpty()) {
                                    try {
                                        counter.acquire();
                                        downloadService.submit(() ->
                                                download(counter, res, priorityBlockingQueue, hostsMap, visited, recievedMap));
                                    } catch (InterruptedException ignored) {
                                    }
                                }
                            }
                        } else {
                            if (recievedMap.containsKey(url)) try {
                                hostsMap.get(host).acquire();
                                recievedMap.remove(url);
                            } catch (InterruptedException ignored) {
                            } finally {
                                hostsMap.get(host).release();
                            }
                            priorityBlockingQueue.add(pair);
                            recievedMap.putIfAbsent(url, new Object());
                        }
                    } catch (MalformedURLException e) {
                        System.err.println("Host's error " + url);
                    }
                }
            }
        } finally {
            counter.release();
        }
    }

    private void extract(final int depth, final ConcurrentMap<String, Object> res, final Document document, ConcurrentMap<String, Object> visited, final Semaphore counter,
                         final ConcurrentMap<String, Semaphore> hostsMap, final PriorityBlockingQueue<Pair<String, Integer>> queue,
                         final ConcurrentMap<String, Object> recievedMap) {
        try {

            if (document == null)
                counter.release();

            if (depth > 1) {

                assert document != null;

                List<String> links = document.extractLinks();
                links.parallelStream().distinct().filter(link -> {
                    return !visited.containsKey(link);
                }).forEach(new Consumer<String>() {
                    @Override
                    public void accept(String url) {
                        try {
                            counter.acquire();
                            queue.add(new Pair<>(url, depth - 1));
                            downloadService.submit(() -> WebCrawler.this.download(counter, res, queue, hostsMap, visited, recievedMap));
                        } catch (InterruptedException ignored) {
                        }
                    }
                });
            }
        } catch (IOException e) {
            System.err.println("It's impossible to extract links " + document.toString());
        } finally {
            counter.release();
        }
    }
}