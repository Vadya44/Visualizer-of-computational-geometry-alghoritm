package sample.models;

import javafx.scene.canvas.GraphicsContext;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class LinesSet implements Iterable {
    /**
     *
     */
    GraphicsContext gc;

    /**
     *
     * @return
     */
    public HashSet<Line> getLines() {
        return lines;
    }

    /**
     *
     */
    public void clear()
    {
        this.lines = new HashSet<>();
    }

    /**
     *
     * @param gc
     */
    public LinesSet(GraphicsContext gc)
    {
        lines = new HashSet<>();
        this.gc = gc;
    }

    /**
     *
     * @param line
     */
    public void addLine(Line line) {
        this.lines.add(line);
        gc.beginPath();
        gc.moveTo(line.getP1().getX(), line.getP1().getY());
        gc.lineTo(line.getP2().getX(), line.getP2().getY());
        gc.stroke();
    }

    /**
     *
     */
    private HashSet<Line> lines;

    /**
     *
     * @return
     */
    @Override
    public Iterator iterator() {
        return lines.iterator();
    }


}
