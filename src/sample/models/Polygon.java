package sample.models;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class Polygon implements Iterable {
    private List<Line> perimeter;

    public Polygon(List<Line> perimeter){
        this.perimeter = perimeter;
    }

    public void addLine(Line line) {
        perimeter.add(line);
    }

    public boolean isBad(Point p1, Point p2)
    {
        boolean isContains1 = false;
        boolean isContains2 = false;
        for (Line l : perimeter) {
            if (l.getP1().equals(p1))
                isContains1 = true;
            if (l.getP2().equals(p2))
                isContains2 = true;
        }
        if (isContains1 && isContains2)
        {
            boolean bad = true;
            for (Line l : perimeter)
                if (l.getP1().equals(p1) && l.getP2().equals(p2)
                        || l.getP1().equals(p2) && l.getP2().equals(p1))
                    bad = false;
            return bad;
        }
        return false;
    }

    public Polygon() {
        perimeter = new ArrayList<>();
    };

    @Override
    public Iterator<Line> iterator() {
        if (perimeter.isEmpty())
            throw new NullPointerException("Empty perimeter");
        return perimeter.iterator();
    }

    @Override
    public void forEach(Consumer action) {
        if (perimeter.isEmpty())
            throw new NullPointerException("Empty perimeter");
        for (Line l : perimeter)
            action.accept(l);
    }


}
