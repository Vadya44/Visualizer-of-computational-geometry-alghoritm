package sample.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class FileReader {
    /**
     *
     * @return
     */
    public List<Polygon> getPolygons() {
        return mPolygons;
    }

    /**
     *
     * @return
     */
    public List<Point> getPoints() {
        return mPoints;
    }

    /**
     *
     * @return
     */
    public Point getStartPoint() {
        return startPoint;
    }

    /**
     *
     * @return
     */
    public Point getEndPoint() {
        return endPoint;
    }

    /**
     *
     */
    private List<Polygon> mPolygons;
    /**
     *
     */
    private List<Point> mPoints;
    /**
     *
     */
    private Point startPoint;
    /**
     *
     */
    private Point endPoint;
    /**
     *
     */
    private File mfile;

    /**
     *
     * @return
     */
    public List<Line> getLines() {
        return mLines;
    }

    /**
     *
     */
    private List<Line> mLines;


    /**
     *
     * @param file
     */
    public FileReader(File file)
    {
        mfile = file;
        mPolygons = new ArrayList<>();
        mPoints = new ArrayList<>();
        mLines = new ArrayList<>();
    }


    /**
     *
     * @return
     */
    public boolean getData()
    {
        try (FileInputStream fl = new FileInputStream(mfile))
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(fl));
            String str;
            while ((str = br.readLine()) != null)
            {
                if (str.startsWith("P"))
                {
                    str = str.substring(2);
                    String[] polyPoints = str.split(";");
                    ArrayList<Integer> points = new ArrayList<>();
                    for (String temp : polyPoints)
                        points.add(Integer.parseInt(temp));

                    if (!points.get(0).equals(points.get(points.size() - 1)))
                        return false;

                    List<Point> tempPoints = new ArrayList<>();
                    for (int i = 0; i < points.size() - 1; i++)
                        tempPoints.add(new Point(points.get(i++), points.get(i)));

                    mPoints.addAll(tempPoints);


                    Polygon tempPolygon = new Polygon();
                    for (int i = 0; i < tempPoints.size() - 1; i++) {
                        tempPolygon.addLine(new Line(tempPoints.get(i), tempPoints.get(i + 1)));
                        mLines.add(new Line(tempPoints.get(i), tempPoints.get(i + 1)));
                    }
                    mPolygons.add(tempPolygon);
                }
                if (str.startsWith("S"))
                {
                    str = str.substring(2);
                    String[] polyPoints = str.split(";");
                    ArrayList<Integer> points = new ArrayList<>();
                    for (String temp : polyPoints)
                        points.add(Integer.parseInt(temp));
                    if (points.size() != 2)
                        return false;
                    startPoint = new Point(points.get(0), points.get(1));
                }
                if (str.startsWith("E"))
                {
                    str = str.substring(2);
                    String[] polyPoints = str.split(";");
                    ArrayList<Integer> points = new ArrayList<>();
                    for (String temp : polyPoints)
                        points.add(Integer.parseInt(temp));
                    if (points.size() != 2)
                        return false;
                    endPoint = new Point(points.get(0), points.get(1));
                }
            }
        } catch (Exception e){
            return false;
        }
        mPoints.add(endPoint);
        mPoints.add(startPoint);
        PointsSet.addPoint(endPoint);
        PointsSet.addPoint(startPoint);
        return this.startPoint != null;
    }
}