package sample.models;

import javafx.scene.canvas.GraphicsContext;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Класс рисуемого хранилища отрезков
 */
public class LinesSet implements Iterable {
    /**
     * Графический контекст, на котором рисуются отрезки
     */
    GraphicsContext gc;



    /**
     * Очистка хранилища
     */
    public void clear()
    {
        this.lines = new HashSet<>();
    }

    /**
     * Конструктор по ГК
     * @param gc Графический контекст
     */
    public LinesSet(GraphicsContext gc)
    {
        lines = new HashSet<>();
        this.gc = gc;
    }

    /**
     * Функция, добавляющая отрезок в хранилище
     * @param line Добавляемый отрезок
     */
    public void addLine(Line line) {
        this.lines.add(line);
        gc.beginPath();
        gc.moveTo(line.getP1().getX(), line.getP1().getY());
        gc.lineTo(line.getP2().getX(), line.getP2().getY());
        gc.stroke();
    }

    /**
     * Хранилище отрезков
     */
    private HashSet<Line> lines;

    /**
     * Итератор по хранилищу
     */
    @Override
    public Iterator iterator() {
        return lines.iterator();
    }


}
