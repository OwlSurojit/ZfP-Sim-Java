package exportScans;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.List;
import java.util.ArrayList;

public class ExportScores {

	private static Formatter form;
	//private static List<Double[]> scores;
	private static int spr; // Scans per run
	private static double runLength; //Length of the run in weird time units 

	/**
	 * Exports the scan results of the currently visible scan
	 * @param file The file to export the data to
	 * @param samples The count of amplitudes to sample the data into
	 * @param maxAmp The maximum amplitude (The amplitude of the supplied signal)
	 * @param emitTrailingZeroes if true, ignore the trailing zeroes in the scores array and write the last positive peak to the end of the file
	 */
	public static void export(File file, int samples, int maxAmp, boolean emitTrailingZeroes) {
		List<Double[]> scores = zfP_Sim.MainWindow.getScanPanel().getScores();
		int lastIndex = scores.size() - 1;
		if (emitTrailingZeroes) {
			for (int i = scores.size() - 1; i >= 0; i--) {
				if (scores.get(i)[1] != 0.0) {
					lastIndex = i + 1;
					break;
				}
			}
		}
		Double[][] scoresArray = new Double[lastIndex][2];
		for (int i = 0; i < lastIndex; i++) {
			scoresArray[i][0] = scores.get(i)[0];
			scoresArray[i][1] = scores.get(i)[1];
		}

		export(scoresArray, file, samples, maxAmp);
	}

	/**
	 * Exports the results of one scan as a text file with a given number of amplitude samples and a given maximum amplitude.
	 * The amplitudes are rounded to whole numbers, most of them will be 0 as this is a noiseless simulation.
	 * @param scores The scan results
	 * @param file The file to export the data to
	 * @param samples The count of amplitudes to sample the data into
	 * @param maxAmp The maximum amplitude (The amplitude of the supplied signal)
	 * 
	 * @author Owl Surojit
	 * @since 04.02.2020
	 */
	public static void export(Double[][] scores, File file, int samples, int maxAmp) {
		for (Double[] d : scores) {
			for (double s : d) {
				System.out.print(s + ", ");
			}
			System.out.println();
		}
		try {
			form = new Formatter(file);
		} catch (FileNotFoundException e) {
			System.out.println("Could not open the file to store the scan data in:");
			e.printStackTrace();
		}

		runLength = scores[scores.length - 1][0];
		int k = 0;
		double peakTime = runLength / (scores.length - 1);
		for (int i = 0; i < samples; i++) {
			if (i * runLength / (samples - 1) >= scores[k][0]) {
				form.format("%d\n", Math.round(scores[k][1] * maxAmp));
				k++;
			} else
				form.format("%d\n", 0);
		}
		form.close();
	}

	/**
	 * Exports the results of one scan as a text file with a given number of amplitude samples and a given maximum amplitude.
	 * The length of the scan is restricted to a certain time.
	 * The amplitudes are rounded to whole numbers, most of them will be 0 as this is a noiseless simulation.
	 * @param scores The scan results
	 * @param scanLength The set time of the scan length
	 * @param file The file to export the data to
	 * @param samples The count of amplitudes to sample the data into
	 * @param maxAmp The maximum amplitude (The amplitude of the supplied signal)
	 */
	public static void export(Double[][] scores, double scanLength, File file, int samples, int maxAmp) {
		try {
			form = new Formatter(file);
		} catch (FileNotFoundException e) {
			System.out.println("Could not open the file to store the scan data in:");
			e.printStackTrace();
		}

		int k = 0;
		for (int i = 0; i < samples; i++) {
			if (k < scores.length && i * scanLength / (samples - 1) >= scores[k][0]) {
				form.format("%d\n", Math.round(scores[k][1] * maxAmp));
				k++;
			} else
				form.format("%d\n", 0);
		}
		form.close();
	}
}
