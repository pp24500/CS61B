package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.HashMap;

public final class Solver {

    private MinPQ<SearchNode> pq = new MinPQ<>();
    private Stack<WorldState> path = new Stack<>();
    private HashMap<WorldState, Priority> marked = new HashMap();
    private SearchNode goal;
    private int timeConsuming;

    /**
     * Helper class which contains the information needed for
     * priority comparison between different states.
     */
    private class Priority implements Comparable<Priority> {

        private int moves, dist;

        public Priority(int m, int d) {
            moves = m;
            dist = d;
        }

        @Override
        public int compareTo(Priority o) {
            return (moves + dist == o.moves + o.dist ?
                    moves - o.moves : moves + dist - o.moves - o.dist);
        }
    }

    /**
     * Helper class for the construction of solution seeking tree. Each
     * solution-finding step defines a search node.
     */
    private class SearchNode implements Comparable<SearchNode> {

        public WorldState state;
        public int moves;
        public SearchNode prev;
        private Priority priority;

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
                        pq.insert(new SearchNode(nextState, min.moves + 1, min));
                        timeConsuming++;
                    } else {
                        Priority nextP = new Priority(min.moves + 1, nextState.estimatedDistanceToGoal());
                        if (nextP.compareTo(marked.get(nextState)) < 0) {
                            pq.insert(new SearchNode(nextState, min.moves + 1, min));
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
    public int time() {
        return timeConsuming;
    }

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