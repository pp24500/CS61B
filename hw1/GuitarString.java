package synthesizer;

/**
 * The guitar string applies Karplus-Strong algorithm,
 * which is quite easy to implement with a BoundedQueue.
 * @author Ziyu Cheng
 */
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency SR / frequency.
    * The buffer is initially filled with zeros. */
    public GuitarString(double frequency) {
        int capacity = (int) Math.round(SR / frequency);
        buffer = new ArrayRingBuffer<>(capacity);
        while (!buffer.isFull()) {
            buffer.enqueue(0.0);
        }
    }

    /** Pluck the guitar string by replacing the buffer with white noise.
    * Dequeue everything in the buffer, and replace it with random numbers
    * between -0.5 and 0.5. double r = Math.random() - 0.5; */
    public void pluck() {
        while (!buffer.isEmpty()) {
            buffer.dequeue();
        }
        while (!buffer.isFull()) {
            double r = Math.random() - 0.5;
            buffer.enqueue(r);
        }
    }

    /** Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm, Dequeue the front sample and enqueue
     * a new sample that is the average of the two multiplied by
     * the DECAY factor. Do not call StdAudio.play(). */
    public void tic() {
        double front1 = buffer.dequeue();
        double front2 = buffer.peek();
        buffer.enqueue(0.5 * DECAY * (front1 + front2));
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
