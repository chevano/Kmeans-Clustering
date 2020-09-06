import java.util.*;
import java.io.*;

class Kmean
{
	public static class Point
	{
		double Xcoord;
		double Ycoord;
		int Label;
		double Distance;
		
		Point()
		{
			Xcoord = 0;
			Ycoord = 0;
			Label = 0;
			Distance = 99999.0;
		}
		
		Point(double Xcoord, double Ycoord)
		{
			this.Xcoord = Xcoord;
			this.Ycoord = Ycoord;
			Label 	    = 0;
			Distance    = 99999.0;
		}

		Point(double Xcoord, double Ycoord, int Label)
		{
			this.Xcoord = Xcoord;
			this.Ycoord = Ycoord;
			this.Label  = Label;
			Distance    = 99999.0;
		}
	}

	static int K;
	static int numPts;
	static Point pointSet[];	
	static int numRows;
	static int numCols;
	static int imgAry[][];
	static Point Kcentroids[];
	static int change;
	
	Kmean(int K_val)
	{
		K 	   = K_val;
		numPts 	   = -1;
		numRows    = -1;
		numCols	   = -1;
		change 	   = 0;
		Kcentroids = new Point[K+1];
	}

	public static void loadPointSet(String FileName) throws IOException
	{
		Scanner inFile = new Scanner( new FileReader(FileName) );
		int index = 0;
		double x;
		double y;

		// header information
		numRows  = Integer.parseInt( inFile.next() );
		numCols  = Integer.parseInt( inFile.next() );
		numPts   = Integer.parseInt( inFile.next() );

		pointSet = new Point[numPts];
		imgAry   = new int[numRows][numCols];	

		for( int row = 0; row < numRows; row++ )
			for( int col= 0; col < numCols; col++ )
				imgAry[row][col] = 0;

		while(inFile.hasNext())
		{
			x = Double.parseDouble(inFile.next());
			y = Double.parseDouble(inFile.next());
			
			pointSet[index] = new Point(x,y);	// assigning (x,y) coordinate to pointSet
			index++;
		}
		inFile.close();
	}
	
	public static void assignLabel()
	{
		int front = 0;
		int back = (numPts - 1);
		int label = 1;

		while( front <= back )
		{
			if( label > K )
				label = 1;
			pointSet[front].Label = label;
			front++;
			label++;

			if( label > K )
				label = 1;
			pointSet[back].Label = label;
			back--;
			label++;
		}
	}
	
	public static double computeDist(Point pt, Point centroid)
	{
		double x1 = pt.Xcoord;
		double y1 = pt.Ycoord;
		double x2 = centroid.Xcoord;
		double y2 = centroid.Ycoord;
		double result;
		
		result =  ( x1 - x2 ) * ( x1 - x2 );
		result += ( y1 - y2 ) * ( y1 - y2 );

		return result/numPts;
	}

	public static void DistanceMinLabel(int index)
	{
		double minDist = 99999.0;
		int minLabel   = 0;
		double dist;
		int label = 1;

		while( label <= K )
		{
			dist = computeDist(pointSet[index], Kcentroids[label]);
			if( dist < minDist )
			{
				minLabel = label;
				minDist = dist;
			}
			label++;
		}

		pointSet[index].Distance = minDist;

		if( minLabel != pointSet[index].Label )
		{
			pointSet[index].Label = minLabel;
			change++;
		}
	}
	
	public static void computeCentroids()
	{

		double sumX[]    = new double[ K+1 ];
		double sumY[]    = new double[ K+1 ];
		double totalPt[] = new double[ K+1 ];
		
		int index = 0;
		int label;
		
		while( index <= K )
		{

			sumX[index]    = 0.0;
			sumY[index]    = 0.0;
			totalPt[index] = 0.0;
			
			index++;
		}
		
		index = 1;

		while( index < numPts )
		{
			

			label        = pointSet[index].Label;
			sumX[label] += pointSet[index].Xcoord;
			sumY[label] += pointSet[index].Ycoord;
			totalPt[label]++;
			
			index++;
		}
		
		label = 1;

		while( label <= K )
		{
			double mid_x;
			double mid_y;
			
			// Prevents a divide by 0 error since the cluster is empty
			if( totalPt[label] == 0 )
			{
				mid_x = 0;
				mid_y = 0;
			}
			
			// Calculating the mean
			else
			{
				mid_x = sumX[label]/totalPt[label];
				mid_y = sumY[label]/totalPt[label];
			}
			
			Kcentroids[label] = new Point(mid_x,mid_y);

			label++;
		}
	}
	
	public static void Point2Image()
	{
		int row = 0;
		int col = 0;

		for( int index = 0; index < numPts; index++ )
		{
			row = (int) pointSet[index].Xcoord;
			col = (int) pointSet[index].Ycoord;
			imgAry[row][col] = pointSet[index].Label;
		}
	}

	public static void prettyPrint(String outputFileName, int iteration) throws IOException
	{
		BufferedWriter outFile = new BufferedWriter( new FileWriter(outputFileName,true) );

		outFile.write("========== Result of Iteration ");
		outFile.write("" + iteration);
		outFile.write(" ==========\r\n");

		for( int row = 0; row < numRows; row++ )
		{
			for( int col = 0; col < numCols; col++ )
			{
				if(imgAry[row][col] == 0)
					outFile.write(" ");
				else
					outFile.write("" + imgAry[row][col]);
			}
			outFile.write("\r\n");
		}
		outFile.close();
	}	

	public static void kMeansClustering(String outputFileName) throws IOException
	{
		int iteration = 1;
		assignLabel();
		change = 99999;
		
		while( change > 2 )
		{
			Point2Image();
			prettyPrint(outputFileName, iteration);
			computeCentroids();
			change = 0;
			
			for( int index = 0; index < numPts; index++ )
				DistanceMinLabel(index);

			iteration++;
		}
	}

	public static void main(String[] args) throws IOException
	{
		int K_val = 0;
		System.out.println("Please enter the value for K");
		Scanner input = new Scanner( System.in );

		if( input.hasNextInt() )
			K_val = input.nextInt();
		input.close();

		Kmean kmean = new Kmean(K_val);

		loadPointSet(args[0]);
		kMeansClustering(args[1]);
	}
}
