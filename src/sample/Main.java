package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
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
    private boolean isSecondClickButton1 = false;
    private boolean isSecondClickButton2 = false;


    private Polygon temp;
    private boolean isNewPolygon = true;
    private Point lastPoint = new Point();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Button buttonStart = new Button("Выбрать начальную точку");
        Button buttonEnd = new Button("Выбрать конечную точку");
        Button buttonCompute = new Button("Вычислить путь");
        Button buttonClear = new Button("Очистить");
        Button buttonHelp = new Button("Как пользоваться");


        buttonStart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isSecondClickButton1)
                {
                    clearAll();
                }
                pointFromChoosing = true;
                PointsSet.deletePoint(fromPoint);
                buttonStart.setDisable(true);
                buttonEnd.setDisable(true);
                isSecondClickButton1 = true;
            }
        });
        buttonStart.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buttonStart.setEffect(new DropShadow());
            }
        });
        buttonStart.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buttonStart.setEffect(null);
            }
        });


        buttonEnd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isSecondClickButton2)
                {
                    clearAll();
                }
                pointToChoosing = true;
                PointsSet.deletePoint(toPoint);
                buttonEnd.setDisable(true);
                buttonStart.setDisable(true);
                isSecondClickButton2 = true;
            }
        });
        buttonEnd.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buttonEnd.setEffect(new DropShadow());
            }
        });
        buttonEnd.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buttonEnd.setEffect(null);
            }
        });

        buttonCompute.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isNewPolygon)
                {
                    PolygonContainer.addPolygon(temp);
                    drawPoly(temp);
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
        buttonCompute.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buttonCompute.setEffect(new DropShadow());
            }
        });
        buttonCompute.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buttonCompute.setEffect(null);
            }
        });


        buttonClear.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buttonCompute.setEffect(new DropShadow());
            }
        });
        buttonClear.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearAll();
                lastPoint = new Point(0, 0);
                oldPoint = new Point(0, 0);
                isEndOfLine = false;
                buttonEnd.setDisable(false);
                buttonStart.setDisable(false);
            }
        });
        buttonClear.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buttonClear.setEffect(null);
            }
        });


        buttonHelp.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buttonHelp.setEffect(new DropShadow());
            }
        });
        buttonHelp.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buttonHelp.setEffect(null);
            }
        });
        buttonHelp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Label secondLabel = new Label("I'm a Label on new Window");

                StackPane secondaryLayout = new StackPane();
                secondaryLayout.getChildren().add(secondLabel);

                Scene secondScene = new Scene(secondaryLayout, 230, 100);

                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Second Stage");
                newWindow.setScene(secondScene);

                // Specifies the modality for new window.
                newWindow.initModality(Modality.WINDOW_MODAL);

                // Specifies the owner Window (parent) for new window
                newWindow.initOwner(primaryStage);

                // Set position of second window, related to primary window.
                newWindow.setX(primaryStage.getX() + 200);
                newWindow.setY(primaryStage.getY() + 100);

                newWindow.show();
            }
        });

        root.getChildren().add(buttonStart);
        root.getChildren().add(buttonEnd);
        root.getChildren().add(buttonCompute);
        root.getChildren().add(buttonClear);
        root.getChildren().add(buttonHelp);

        primaryStage.setTitle("I see edges!");
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
                        if (PolygonContainer.contains(new Point(event.getX(), event.getY())))
                            return;
                        if (pointFromChoosing) {
                            Point p = new Point(event.getX(), event.getY());
                            PointsSet.addPoint(p);
                            pointFromChoosing = false;
                            fromPoint = p;
                            buttonStart.setDisable(false);
                            buttonEnd.setDisable(false);
                            if (!isNewPolygon)
                            {
                                PolygonContainer.addPolygon(temp);
                                drawPoly(temp);
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
                            buttonStart.setDisable(false);
                            buttonEnd.setDisable(false);
                            if (!isNewPolygon)
                            {
                                PolygonContainer.addPolygon(temp);
                                drawPoly(temp);
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
                                    drawPoly(temp);
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

        Point oldPoint = null;
        boolean isEndOfLine = false;
        gc.setLineWidth(3);
        gc.setStroke(Color.BLUE);

        Polygon temp = null;
        boolean isNewPolygon = true;
        Point lastPoint = new Point();
        FlowPane root = new FlowPane();
        isSecondClickButton1 = false;
        isSecondClickButton2 = false;

    }
    public void drawPoly(Polygon p)
    {
        gc.setFill(Color.BLUE);
        gc.fillPolygon(p.getX(), p.getY(), p.pointsSize());
    }



    public static void main(String[] args) { launch(args);
    }
}
