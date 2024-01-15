public interface ThreadFactory {
    public Thread getThread(Runnable run);
}