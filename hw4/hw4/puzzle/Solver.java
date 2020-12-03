package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.HashMap;

/**
 * Class solves the puzzle by best-first search.
 * @author Ziyu
 */
public class Solver {
    /** Priority queue to store the state nodes. */
    private MinPQ<SearchNode> pq = new MinPQ<>();
    /** The solution path. */
    private Stack<WorldState> path = new Stack<>();
    /** Record the states that occurred before earlier search. */
    private HashMap<WorldState, Priority> marked = new HashMap();
    /** Mark the goal state node. */
    private SearchNode goal;
    /** Record the total number of enqueue operations. */
    private int timeConsuming;

    /**
     * Helper class which contains the information needed for
     * priority comparison between different states.
     */
    private class Priority implements Comparable<Priority> {
        /** The moves and distance to goal, for priority estimation. */
        private int moves, dist;

        /**
         * Construct a new priority object with the parameters.
         * @param m moves
         * @param d distance to goal state
         */
        Priority(int m, int d) {
            moves = m;
            dist = d;
        }

        @Override
        public int compareTo(Priority o) {
            return (moves + dist == o.moves + o.dist
                    ? moves - o.moves : moves + dist - o.moves - o.dist);
        }
    }

    /**
     * Helper class for the construction of solution seeking tree. Each
     * solution-finding step defines a search node.
     */
    private class SearchNode implements Comparable<SearchNode> {
        /** The state of step. */
        public WorldState state;
        /** Number of moves from the initial state. */
        public int moves;
        /** Point to the previous state node. */
        public SearchNode prev;
        /** Priority. */
        private Priority priority;

        /**
         * Construct new search node with given parameters.
         * @param s state
         * @param m moves
         * @param p previous node
         */
        public SearchNode(WorldState s, int m, SearchNode p) {
            state = s;
            moves = m;
            prev = p;
            priority = new Priority(moves, state.estimatedDistanceToGoal());
        }

        @Override
        public int compareTo(SearchNode other) {
            return priority.compareTo(other.priority);
        }
    }

    /**
     * Constructor which solves the puzzle, computing everything necessary for
     * moves() and solution() to not have to solve the problem again.
     * Solves the puzzle using the A* algorithm. Assumes a solution exists.
     * @param initial worldstate
     */
    public Solver(WorldState initial) {
        goal = null;
        timeConsuming = 1;
        SearchNode head = new SearchNode(initial, 0, null);
        pq.insert(head);
        marked.put(initial, head.priority);

        while (!pq.isEmpty()) {
            SearchNode min = pq.delMin();
            marked.put(min.state, min.priority);
            if (min.state.isGoal()) {
                goal = min;
                break;
            } else {
                for (WorldState nextState : min.state.neighbors()) {
                    if (!marked.containsKey(nextState)) {
                        pq.insert(new SearchNode(nextState,
                                min.moves + 1, min));
                        timeConsuming++;
                    } else {
                        Priority nextP = new Priority(min.moves + 1,
                                nextState.estimatedDistanceToGoal());
                        if (nextP.compareTo(marked.get(nextState)) < 0) {
                            pq.insert(new SearchNode(nextState,
                                    min.moves + 1, min));
                            timeConsuming++;
                        }
                    }
                }
            }
        }

        while (goal != null) {
            path.push(goal.state);
            goal = goal.prev;
        }
    }

    /**
     * Return the time consuming during the solving process.
     */
    /*public int time() {
        return timeConsuming;
    }
*/
    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     */
    public int moves() {
        return path.size() - 1;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     */
    public Iterable<WorldState> solution() {
        return path;
    }
}
