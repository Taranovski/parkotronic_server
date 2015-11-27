/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nonstop.parkotronic.server.service;

import org.junit.*;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Alyx
 */
public class TrackingServiceImplTest {

    public TrackingServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
        SpotInfo spotInfo1 = new SpotInfo();
        spotInfo1.setX(2);
        spotInfo1.setY(3);
        spotInfo1.setD(5);
        SpotInfo spotInfo2 = new SpotInfo();
        spotInfo2.setX(9);
        spotInfo2.setY(2);
        spotInfo2.setD(5);
        SpotInfo spotInfo3 = new SpotInfo();
        spotInfo3.setX(3);
        spotInfo3.setY(10);
        spotInfo3.setD(5);

        List<SpotInfo> infos = Arrays.asList(spotInfo1, spotInfo2, spotInfo3);

        Position position = Triangulation.getPosition(infos, 1.005);

        System.out.println(position);

        double lowerX = 5;
        double higherX = 7;

        double lowerY = 5;
        double higherY = 7;

        assertTrue(position.getX() > lowerX);
        assertTrue(position.getX() < higherX);

        assertTrue(position.getY() > lowerY);
        assertTrue(position.getY() < higherY);
    }

    class SpotInfo {

        private double x;
        private double y;
        private double d;

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getD() {
            return d;
        }

        public void setD(double d) {
            this.d = d;
        }

        @Override
        public String toString() {
            return "SpotInfo{" + "x=" + x + ", y=" + y + ", d=" + d + '}';
        }

    }

    static class Position {

        private double x;
        private double y;
        private double err;

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getErr() {
            return err;
        }

        public void setErr(double err) {
            this.err = err;
        }

        @Override
        public String toString() {
            return "Position{" + "x=" + x + ", y=" + y + ", err=" + err + '}';
        }

    }

    static class Triangulation {

        public static Position getPosition(List<SpotInfo> spotInfos, double distanceKoefficient) {
            Position position = new Position();

            SpotInfo initial = spotInfos.get(0);
            Area area = new Area(new Ellipse2D.Double(
                    initial.getX() - initial.getD(),
                    initial.getY() - initial.getD(),
                    initial.getD() * 2 * distanceKoefficient,
                    initial.getD() * 2 * distanceKoefficient));

            for (int i = 1; i < spotInfos.size(); i++) {
                SpotInfo currentSpotInfo = spotInfos.get(i);
                Area tempArea = new Area(new Ellipse2D.Double(
                        currentSpotInfo.getX() - currentSpotInfo.getD(),
                        currentSpotInfo.getY() - currentSpotInfo.getD(),
                        currentSpotInfo.getD() * 2 * distanceKoefficient,
                        currentSpotInfo.getD() * 2 * distanceKoefficient));
                area.intersect(tempArea);

            }

            if (area.getBounds().getWidth() > 0 && area.getBounds().getHeight() > 0) {
                position.setX(area.getBounds().getCenterX());
                position.setY(area.getBounds().getCenterY());
                double dx = area.getBounds().getWidth() / 2;
                double dy = area.getBounds().getHeight() / 2;
                position.setErr(Math.sqrt(dx * dx + dy * dy) / 2);
            }

            return position;
        }
    }
}
