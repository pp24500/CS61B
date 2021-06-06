package byog.Core;

import java.util.List;

public interface Space {

    void wrapWalls();

    int distToTop();

    int distToBottom();

    int distToLeft();

    int distToRight();

    List<Space> branch();
}
