package sample.models;

import java.util.HashSet;
import java.util.Set;

public class LinesContainer {
    private static final LinesContainer instance = new LinesContainer();
    private HashSet<Line> mLines;

    private LinesContainer() {
        mLines = new HashSet<>();
    }

    public static void clear() {
        instance.mLines = null;
        instance.mLines = new HashSet<>();
    }

    public static void addLine(Line l)
    {
        instance.mLines.add(l);
    }

    public static LinesContainer getInstance()
    {
        return instance;
    }

    public static HashSet<Line> getLines() {
        return instance.mLines;
    }

    public static void setLines(HashSet<Line> lines) {
        instance.mLines = lines;
    }

}
