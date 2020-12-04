package exportScans;

import control.Scan;
import control.Sender;
import drawing.DrawPanel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import control.Body;
import geometry.Point;
import geometry.Ray;
import geometry.Vector;
import zfP_Sim.ScanPanel;

public class MeasuringRun {

	public static void mr2dNaive(Body body, DrawPanel simPanel, ScanPanel scanPanel, int startX, int endX, int y,
			double angle, int rayCount, double scatterAngle, int reflectionCount, double velocity, double sampleRate,
			int sampleCount, String dir) {
		(new File(dir)).mkdirs();
		int samples = (int) ((endX - startX) / sampleRate);
		for (int i = 0; i < samples; i++) {
			// begin of copy from MainWindow.simStartButtonActionPerformed

			double radians = Math.toRadians(-angle);
			double rayX = Math.cos(radians);
			double rayY = Math.sin(radians);
			Sender sender = new Sender(new Ray(new Point(startX + i * sampleRate, (double) y), new Vector(rayX, rayY)),
					20);
			Scan scan = new Scan(body, sender, reflectionCount, velocity, 0);

			if (rayCount == 1) {
				simPanel.simulate(scan.reflections(), -2);
				scanPanel.setScores(scan.scan_A());
			} else {
				simPanel.simulate(scan.MultiReflections(rayCount, scatterAngle), -2);
				scanPanel.setScores(scan.processScan_A3(scan.MultiScan_A(rayCount, scatterAngle)));

				ExportScores.export(new File(dir + "\\" + i + ".txt"), sampleCount, 1020, false); //TODO set sample rate
			}
		}
	}

	public static void mr2d(Body body, DrawPanel simPanel, ScanPanel scanPanel, int startX, int endX, int y,
			double angle, int rayCount, double scatterAngle, int reflectionCount, double velocity, double sampleRate,
			int sampleCount, String dir) {
		(new File(dir)).mkdirs();
		int samples = (int) ((endX - startX) / sampleRate);
		ArrayList<Double[][]> scoresList = new ArrayList<Double[][]>();
		double latestPeakTime = 0;

		for (int i = 0; i < samples; i++) {
			// begin of copy from MainWindow.simStartButtonActionPerformed

			double radians = Math.toRadians(-angle);
			double rayX = Math.cos(radians);
			double rayY = Math.sin(radians);
			Sender sender = new Sender(new Ray(new Point(startX + i * sampleRate, (double) y), new Vector(rayX, rayY)),
					20);
			Scan scan = new Scan(body, sender, reflectionCount, velocity, 0);

			if (rayCount == 1) {
				simPanel.simulate(scan.reflections(), -2);
				scanPanel.setScores(scan.scan_A());
			} else {
				simPanel.simulate(scan.MultiReflections(rayCount, scatterAngle), -2);
				scanPanel.setScores(scan.processScan_A3(scan.MultiScan_A(rayCount, scatterAngle)));
				List<Double[]> scores = zfP_Sim.MainWindow.getScanPanel().getScores();
				int lastIndex = scores.size();
				Double[][] scoresArray = new Double[lastIndex][2];
				for (int j = 0; j < lastIndex; j++) {
					scoresArray[j][0] = scores.get(j)[0];
					scoresArray[j][1] = scores.get(j)[1];
				}
				scoresList.add(scoresArray);
				int lastPositiveIndex = lastIndex;
				for (int j = scores.size() - 1; j >= 0; j--) {
					if (scores.get(j)[1] != 0.0) {
						lastPositiveIndex = j;
						break;
					}
				}
				if (scoresArray[lastPositiveIndex][0] > latestPeakTime)
					latestPeakTime = scoresArray[lastPositiveIndex][0];
			}
		}

		System.out.println(latestPeakTime);

		for (int i = 0; i < samples; i++) {

			ExportScores.export(scoresList.get(i), latestPeakTime, new File(dir + "\\" + i + ".txt"), sampleCount,
					1020); //TODO set sample rate

		}
	}

}
