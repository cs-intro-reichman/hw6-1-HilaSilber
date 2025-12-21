import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);

		image = grayScaled(tinypic);
		// System.out.println();
		// print(image);
		// for (int i = 0; i < image.length; i++){
		// 	for (int j = 0; j < image[i].length; j++){
		// 		System.out.print(image[i][j].getBlue());
		// 	}
		// }
		
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		for (int i = 0; i < numRows; i ++){
			for (int j = 0; j < numCols; j++){
				int r = in.readInt();
				int g = in.readInt();
				int b = in.readInt();
				Color c = new Color (r, g, b);
				image [i][j] = c;
			}

		}
		// Reads the RGB values from the file into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		//// Replace the following statement with your code.
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		for (int i = 0; i < image.length; i ++){
			for (int j = 0; j < image[i].length; j++){
				print (image[i][j]);
			}
		}
		//// Replace this comment with your code
		//// Notice that all you have to so is print every element (i,j) of the array using the print(Color) function.
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		int index = 0;
		Color [][] fimge = new Color [image.length][image[0].length];
		for (int i = 0; i < image.length; i++){
			for (int j = image[i].length - 1; j >= 0; j--){
				fimge[i][index] = image[i][j];
				index ++;
			}
			index = 0;
		}

		//// Replace the following statement with your code
		return fimge;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		int index = 0;
		Color [][] fimge = new Color [image.length][image[0].length];
		for (int i = image.length - 1; i >= 0; i--){
				fimge[index] = image[i];
				index ++;
			}
		return fimge;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		int r = pixel.getRed();
		int g = pixel.getGreen();
		int b = pixel.getBlue();
		
		int lum = (int)(r * 0.299 + g * 0.587 + b * 0.114);
		Color c = new Color (lum, lum, lum);
		//// Replace the following statement with your code
		return c;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color [][] gray = new Color[image.length][image[0].length];
		for (int i = 0; i < image.length; i++){
			for (int j = 0; j < image[i].length; j++){
				gray[i][j] = luminance(image[i][j]);
			}
		}
		return gray;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		//// Replace the following statement with your code
		int h0 = image.length;
		int w0 = image[0].length;
		Color [][] s = new Color[height][width];
		for (int i = 0; i < height; i ++){
			for (int j = 0; j < width; j ++){
				s[i][j] = image [(int)(i * (double)(h0) / height)][(int)(j * (double)(w0) / width)];
			}
		}
		return s;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int rc1 = c1.getRed();
		int gc1 = c1.getGreen();
		int bc1 = c1.getBlue();
		int rc2 = c2.getRed();
		int gc2 = c2.getGreen();
		int bc2 = c2.getBlue();

		int rb = (int)Math.round((alpha * (double)(rc1)) + (1-alpha) * (double)(rc2));
		int gb = (int)Math.round((alpha * (double)(gc1)) + (1-alpha) * (double)(gc2));
		int bb = (int)Math.round((alpha * (double)(bc1)) + (1-alpha) * (double)(bc2));


		return new Color(rb, gb, bb);
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		Color [][] b = new Color[image1.length][image1[0].length];
		for (int i = 0; i < image1.length; i++){
			for (int j = 0; j < image1[0].length; j++){
				Color c1 = (image1[i][j]);
				Color c2 = (image2[i][j]);
				b[i][j] = blend(c1, c2, alpha);
			}
		}

		//// Replace the following statement with your code
		return b;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		int sh = source.length;
		int sw = source[0].length;

		Color [][] sTarget = scaled(target, sw, sh);

		for (int i = 0; i <= n; i++){
			double a = (n-i)/(double)(n);
			Color [][] m = blend(source, sTarget, a);
			Runigram.display(m);
			StdDraw.pause(500);

		}
		//// Replace this comment with your code
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

