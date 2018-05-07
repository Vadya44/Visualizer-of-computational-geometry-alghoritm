package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import sample.models.*;

import java.util.*;

public class Main extends Application {
    private Canvas canvas = new Canvas(1800, 1800);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private FlowPane root = new FlowPane();


    LinesSet linesSet;
    boolean pointFromChoosing = false;
    boolean pointToChoosing = false;
    Point fromPoint = null;
    Point toPoint = null;

    Point oldPoint = new Point(0, 0);
    private boolean isEndOfLine = false;


    private Polygon temp;
    private boolean isNewPolygon = true;
    private Point lastPoint = new Point();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Button button1 = new Button("chooseStart");
        Button button2 = new Button("chooseEnd");
        Button button3 = new Button("Compute");
        Button button4 = new Button("Clear");


        button1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pointFromChoosing = true;
                PointsSet.deletePoint(fromPoint);
                button1.setDisable(true);
                button2.setDisable(true);
            }
        });
        button1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button1.setEffect(new DropShadow());
            }
        });


        button2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pointToChoosing = true;
                PointsSet.deletePoint(toPoint);
                button2.setDisable(true);
                button1.setDisable(true);
            }
        });
        button2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button2.setEffect(new DropShadow());
            }
        });

        button3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isNewPolygon)
                {
                    PolygonContainer.addPolygon(temp);
                    isNewPolygon = true;
                }
                Graph graph = new Graph(PointsSet.getPoints());
                graph = Graph.buildVisibilityGraph(LinesContainer.getLines());
                gc.setStroke(Color.RED);
                gc.setLineWidth(1);
                for (Line l : graph.getEdges())
                    linesSet.addLine(l);
                Dijkstra dijkstra = new Dijkstra(graph);
                dijkstra.execute(fromPoint);
                LinkedList<Point> ll = dijkstra.getPath(toPoint);

                gc.setStroke(Color.GREEN);
                gc.setLineWidth(6);

                for (int i = 0; i < ll.size() - 1 ; i++)
                    linesSet.addLine(new Line(ll.get(i), ll.get(i + 1)));
                gc.setLineWidth(3);
                gc.setStroke(Color.BLUE);
            }
        });
        button3.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button3.setEffect(new DropShadow());
            }
        });

        button4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearAll();
            }
        });

        root.getChildren().add(button1);
        root.getChildren().add(button2);
        root.getChildren().add(button3);
        root.getChildren().add(button4);

        primaryStage.setTitle("Canvas Test");
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(3);
        linesSet = new LinesSet(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, 2000, 2000));
        primaryStage.setFullScreen(true);
        primaryStage.show();
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        if (pointFromChoosing) {
                            Point p = new Point(event.getX(), event.getY());
                            PointsSet.addPoint(p);
                            pointFromChoosing = false;
                            fromPoint = p;
                            button1.setDisable(false);
                            button2.setDisable(false);
                            if (!isNewPolygon)
                            {
                                PolygonContainer.addPolygon(temp);
                                isNewPolygon = true;
                            }
                            gc.setFill(Color.GREEN);
                            gc.fillOval(p.getX() - 5, p.getY() - 5, 10, 10);
                            return;
                        }

                        if (pointToChoosing) {
                            Point p = new Point(event.getX(), event.getY());
                            PointsSet.addPoint(p);
                            pointToChoosing = false;
                            toPoint = p;
                            button1.setDisable(false);
                            button2.setDisable(false);
                            if (!isNewPolygon)
                            {
                                PolygonContainer.addPolygon(temp);
                                isNewPolygon = true;
                            }
                            gc.setFill(Color.RED);
                            gc.fillOval(p.getX() - 5, p.getY() - 5, 10, 10);
                            return;
                        }

                        if (!isEndOfLine) {
                            oldPoint = new Point(event.getX(), event.getY());
                            Point nearest = oldPoint.getNearest();
                            if (nearest == null)
                                PointsSet.addPoint(oldPoint);
                            else
                            {
                                oldPoint = nearest;
                            }
                            isEndOfLine = true;

                        }
                        else {
                            Point p = new Point(event.getX(), event.getY());
                            Point nearest = p.getNearest();
                            if (nearest == null) {
                                nearest = p;
                                PointsSet.addPoint(event.getX(), event.getY());
                            }

                            if (isNewPolygon)
                            {
                                temp = new Polygon();
                                isNewPolygon = false;
                            }
                            else
                            {
                                if (!lastPoint.equals(oldPoint)) {
                                    PolygonContainer.addPolygon(temp);
                                    temp = new Polygon();
                                }
                            }
                            temp.addLine(new Line(oldPoint, nearest));
                            lastPoint = nearest;

                            linesSet.addLine(new Line(oldPoint, nearest));
                            LinesContainer.addLine(new Line(oldPoint, nearest));

                            isEndOfLine = false;
                        }
                    }
                });

    }
    public void clearAll()
    {
        LinesContainer.clear();
        PointsSet.clear();
        PolygonContainer.clear();
        linesSet.clear();
        gc.clearRect(0, 0, 1800, 1800);
        boolean pointFromChoosing = false;
        boolean pointToChoosing = false;
        Point fromPoint = null;
        Point toPoint = null;

        Point oldPoint = new Point(0, 0);
        boolean isEndOfLine = false;


        Polygon temp;
        boolean isNewPolygon = true;
        Point lastPoint = new Point();
        FlowPane root = new FlowPane();
    }



    public static void main(String[] args) { launch(args);
    }
}
