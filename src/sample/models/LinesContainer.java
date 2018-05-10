package sample.models;

import java.util.HashSet;

/**
 *
 */
public class LinesContainer {
    /**
     *
     */
    private static final LinesContainer instance = new LinesContainer();
    /**
     *
     */
    private HashSet<Line> mLines;

    /**
     *
     */
    private LinesContainer() {
        mLines = new HashSet<>();
    }

    /**
     *
     */
    public static void clear() {
        instance.mLines = null;
        instance.mLines = new HashSet<>();
    }

    /**
     *
     * @param l
     */
    public static void addLine(Line l)
    {
        instance.mLines.add(l);
    }

    /**
     *
     * @return
     */
    public static HashSet<Line> getLines() {
        return instance.mLines;
    }


}
