# Kmeans-Clustering

Project 8: K-means clustering
Problem statement: Given a list of 2-D points, and a cluster number, K, the task is to partition the input point set into K clusters such that points within a cluster are closer to the centroid of their own cluster centroid than to the centroids of all other clusters. (You are to implement the algorithm steps given in class.)

*Remark: YOU MUST IMPLEMENT THE ALGORITHMS GIVEN IN THIS SPECS, otherwise, you will receive 0 pt for this project if you USE different ALGORITHM!

********************************
Language: Java

Project points: 10 pts

Due date (soft copy): 11/11/2019 Monday *2 hours* after midnight
	+2 pts for early submission: 11/9/2019 Saturday before midnight
	-1 pt due: 11/12/2019 Tuesday after midnight 
-2 pt due: 11/13/2019 Wednesday after midnight
	- 10 pts After 11/13/2019 for all students who did not submit soft copy
 
Due Date (hard copy):   all hard copies are due on 11/14/2019 Thursday in class
All projects without hard copy after 11/14/2019 will receive 0 pts 
even you have submit soft copy on time and even if it works.

*******************************
I. input specifications:
*******************************
input-1 - inFile (args[0]) : a text file with the following format:
The first text line is the dimension (#of rows and # of columns) of the image array, 
The second text line is number of points in the data point set, then
follows by a list of points in x-y coordinates.
	
For example:
		40  50  // The image has 40 rows and 50 columns
		25  	// There are 25 data points in the point set
    		12  30 // A point on row 12 and column 30
     		10  21 // A point on row 10 and column 21
    		:	
		:
Input-2 (console): You will ask the user from console to get K.

*******************************
II. Output specifications:
*******************************
outFile (args[1]): 2D displays of the result of clustering after each iteration. 
(Call  prettyPrint method to output 2-D arrays, one per iteration. See PrettyPrintImgAry method below.) 
*** make sure the output of your 2D arrays all line-up row by row and column by column.

For example (the clustering results of the input in the above):


*** Result of iteration 1 ****
 
    1 1 1      
  1 1 1 2      
    2 2       
          1 2
        1 2 2 
        1 2 2  
        2 2 2 
:
:
:
:

*** Result of iteration 4 ****
 
    1 1 1      
  1 1 1 1      
    1 1       
          2 2
        2 2 2 
        2 2 2  
        2 2 2 

*******************************
III. Data structure:
*******************************
- A Kmean class
- A Point class:
- Xcoord (double) // convert to int when plotting onto 2D imgAry
- Ycoord (double) // convert to int when plotting onto 2D imgAry
- Label (int)// initialize to 0
- Distance (double) // the distance to its own cluster centroid.
			// initialize to 99999.00

- K (int) // K clusters 
- numPts (int) // initialize to 0 
	- pointSet[numPts] (Point) // 1D array of Point class
// to be dynamically allocated during run-time
// initially set all points distance to 99999.0 a large distance
// Please note: 
	- numRows (int) 
	- numCols (int)
	- imgAry (** int) // a 2D array, size of  numRows by numCols

- Kcentroids[](Point) // 1D array of centroid class
// to be dynamically allocated in class constructor, size of K+1
// we do NOT want to use 0 as cluster label so the cluster label
// will run from 1 to K, therefore, the size of array is K+1
// Distance is set to 0.0.

	- change (int) // for tracking the label changes, initialize to 0

	- constructor (.. )


- loadPointSet (inFile, pointSet ) // Algorithm is given below.
// read each point from input file and store it onto
// the pointSet array.
// initially set all points’ Distance to 9999.0.
				
	- assignLabel (pointSet, K)// see algorithm below
//assign each point a label from 1 to K

	- kMeansClustering (pointSet, K) // see algorithm below

- computeCentroids (pointSet, Kcentroids ) // see algorithm below
	// Go thru the entire pointSet array only once
// to compute the centroids of each of the K clusters
// Store the computed centroids in each Kcentroids[i], 
// where i = 1 to K.

	- DistanceMinLable (pointSet[index], Kcentroids) // see algorithm below
// compute the distance from a point pointSet[index]
// to each of the K centroids.
// check to see if pointSet[index]’s label needs to be change or not.

- double computeDist(pt, centroid) 
// Compute the distance from pt to a given centroid.
// The method returns the distance
// YOUR SHOULD KNOW HOW TO WRITE THIS METHOD!
- Point2Image (pointSet, imgAry) // output to outFile2
// for each point i, in pointSet, plot the pointSet[i].Label 
// onto the imgAry.  You need to convert pointSet[i]’s Xcood and Ycood // to integer. 
    // YOUR SHOULD KNOW HOW TO WRITE THIS METHOD!

- prettyPrint (imgAry, outFile, iteration)
	// write Caption indicating the number of iteration
// write the imgAry to outFile2 as follows:
		// if imgAry(i,j) > 0 
print imgAry(i,j)
else print one blank space.
// YOUR SHOULD KNOW HOW TO WRITE THIS METHOD!

*******************************
IV. main (...)
*******************************
Step 0:  
inFile, outFile ← Open from args[0] and args[1]
numRows, numCols ← read from inFile.
imgAry ← Dynamically allocate a 2-D arrays, size numRows by numCols.
numPts ← read from inFile
pointSet ← Dynamically allocate the pointSet array, size of numPts  	 
 
K ← ask user to input K from console
Kcentroids[K] ← Dynamically allocate centroids struct, size of K+1

Step 1: loadPointSet (inFile, pointSet)
Step 2: kMeansClustering (pointSet, K)
Step 3: close all files
*******************************
V. loadPointSet (inFile, pointSet)
*******************************
Step 0: index ← 0
Step 1: x, y ← read from inFile
Step 2: pointSet[index].Xcoord ← (double) x
	pointSet[index].Ycoord ← (double) y
pointSet[index].label← 0
pointSet[index].Distance ← 99999.00
Step 3: index ++
Step 4: repeat step 1 to step 3 until the end of inFile

*******************************
VI. assignLabel (pointSet, K)
*******************************
Step 0: front ← 0
	  back ← numPts – 1
	  label ← 1
Step 1: if label > K
         label ← 1
Step 2: pointSet[front].label ←  label
	   front ++
	   label++
Step 3:  pointSet[back].label ← label
	   Back --
	   label++   
Step 4: repeat step 1 to step 3 while front <= back

*******************************
VII. Point2Image (pointSet, imgAry) 
*******************************
// for each point i, in pointSet, plot the pointSet[i].Label 
// onto the imgAry.  You need to convert pointSet[i]’s Xcood and Ycood to       //  integer. YOUR SHOULD KNOW HOW TO WRITE THIS METHOD!

*******************************
VIII. kMeansClustering (pointSet[], K)
*******************************
Step 0: iteration ← 0
Step 1: assignLable (pointSet, K) // see algorithm below.
Step 2: Point2Image (pointSet, imgAry) 
   printImage (imgAry, outFile, iteration) 
Step 3: change ← 0
Step 4: computeCentroids (pointSet, Kcentroids) // see algorithm below
Step 5: index ← 0
Step 6: DistanceMinLable (pointSet[index], Kcentroids) 
// for each point, compute the K distances from the point to K //centroids; see algorithm below
Step 7: index ++ 
Step 8: repeat step 6 to step 7 while index < numPts
Step 9: iteration++
Step 10: repeat step 2 to step 9 until change <= 2
*******************************
IX. computeCentroids (pointSet[], Kcentroids[] ) 
*******************************
Step 0: sumX[] ← dynamically allocate 1-D array, size of K+1, of double
			// initialize to 0.0
   sumY[] ← dynamically allocate 1-D array, size of K+1, of double
// initialize to 0.0
   totalPt[] ← dynamically allocate 1-D array, size of K+1
		// initialize to 0
 
Step 1: index ← 0
Step 2: label ← pointSet[index].label // get the point’s cluster label
	sumX[label] += pointSet[index].Xcoord
  	sumY[label] += pointSet[index].Ycoord
	totalPt[label] ++

Step 3: index++
Step 4: repeat step 2 to step 3 while index < numPts
step 5: label ← 1
step 6: Kcentroids[label].Xcoord ←(sumX[label]/ totalPt[label]) 
	   Kcentroids[label].Ycoord ←(sumY[label]/ totalPt[label])
Step 7: label ++
Step 8: repeat step 6 to step 7 while label <= K

*******************************
X. DistanceMinLable (pt, Kcentroids[])
*******************************
Step 0: minDist ← 99999.00 
	  minLabel ← 0
Step 1: label ← 1
Step 2: dist ← computeDist(pt, Kcentroids[label])
	   if dist < minDist
            minLabel ← label
		 minDist ←dist
Step 3: label ++
Step 4: repeat step 2 to step 3 while label <= K
Step 5: pt.Distance ← minDist
Step 6: if pt.Label != minLabel 
		pt.Label ← minLabel
		change++

*******************************
XI. double computeDist(pt, Kcentroids[label])
*******************************
 
// Compute the distance from pt to a given centroid.
// The method returns the distance
// YOUR SHOULD KNOW HOW TO WRITE THIS METHOD!
 
