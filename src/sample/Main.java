package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.models.*;

import java.io.File;
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
        Button buttonChooseFile = new Button("Выбрать файл");


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
                buttonClear.setEffect(new DropShadow());
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
                buttonChooseFile.setDisable(false);
                buttonCompute.setDisable(false);
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
                TextArea secondLabel = new TextArea(ManualContainer.getText());
                secondLabel.setWrapText(true);
                secondLabel.setEditable(false);

                StackPane secondaryLayout = new StackPane();
                secondaryLayout.getChildren().add(secondLabel);

                Scene secondScene = new Scene(secondaryLayout, primaryStage.getWidth() / 2, primaryStage.getHeight() * 0.8);

                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Справка по использованию");
                newWindow.setScene(secondScene);

                // Specifies the modality for new window.
                newWindow.initModality(Modality.WINDOW_MODAL);

                // Specifies the owner Window (parent) for new window
                newWindow.initOwner(primaryStage);

                // Set position of second window, related to primary window.
                newWindow.setY(primaryStage.getHeight() * 0.1);
                newWindow.setX(primaryStage.getWidth() / 4);

                newWindow.show();
            }
        });

        buttonChooseFile.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buttonChooseFile.setEffect(new DropShadow());
            }
        });
        buttonChooseFile.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buttonChooseFile.setEffect(null);
            }
        });
        final FileChooser fileChooser = new FileChooser();
        buttonChooseFile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
                buttonCompute.setDisable(true);
                buttonChooseFile.setDisable(true);
                buttonEnd.setDisable(true);
                buttonStart.setDisable(true);
                calcFromFile(file, primaryStage);
            }
        });

        root.getChildren().add(buttonStart);
        root.getChildren().add(buttonEnd);
        root.getChildren().add(buttonCompute);
        root.getChildren().add(buttonClear);
        root.getChildren().add(buttonChooseFile);
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

    public void calcFromFile(File file, Stage primaryStage)
    {
        FileReader fr = new FileReader(file);
        if (fr.getData())
        {
            int i;
            for (Line l : fr.getLines())
            {
                LinesContainer.addLine(l);
                linesSet.addLine(l);
            }
            for (Point p : fr.getPoints())
            {
                PointsSet.addPoint(p);
            }
            fromPoint = fr.getStartPoint();
            toPoint = fr.getEndPoint();
            for (Polygon p : fr.getPolygons()) {
                PolygonContainer.addPolygon(p);
                drawPoly(p);
            }

            gc.setFill(Color.GREEN);
            gc.fillOval(fromPoint.getX() - 5, fromPoint.getY() - 5, 10, 10);


            gc.setFill(Color.RED);
            gc.fillOval(toPoint.getX() - 5, toPoint.getY() - 5, 10, 10);

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

            for (i = 0; i < ll.size() - 1 ; i++)
                linesSet.addLine(new Line(ll.get(i), ll.get(i + 1)));
            gc.setLineWidth(3);
            gc.setStroke(Color.BLUE);

        }
        else {

                Text secondLabel = new Text("Неверный формат или содержимое файла.");
                secondLabel.setStrokeWidth(8);

                StackPane secondaryLayout = new StackPane();
                secondaryLayout.getChildren().add(secondLabel);

                Scene secondScene = new Scene(secondaryLayout, 400, 200);

                Stage newWindow = new Stage();
                newWindow.setTitle("Ошибка");
                newWindow.setScene(secondScene);

                newWindow.initModality(Modality.WINDOW_MODAL);

                newWindow.initOwner(primaryStage);

                newWindow.show();
        }
    }


    public static void main(String[] args) { launch(args);
    }
}
