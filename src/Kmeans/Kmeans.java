package Kmeans;


import tools.RandCreate;

public class Kmeans {

	/**
	 * double []data: data need Cluster
	 * 
	 * int ClusterClassNumber :Cluster number
	 * 
	 * int iteration :is the iteration times
	 * 
	 * return double[] center :the result of Cluster
	 * 
	 * 
	 */

	public static double[] getKmeanCenter(double[] data, int ClusterClassNumber, int iteration) {


		double[] center = new double[ClusterClassNumber];
		double minCost = 0.0;


		// calculate for 5 Times and find min Cost as center

		for (int index = 0; index < 5; ++index) {

			double[] tempCenter = getKmeans(data, ClusterClassNumber, iteration);

			if (index == 0 || tempCenter[ClusterClassNumber] < minCost) {
				minCost = tempCenter[ClusterClassNumber];
				System.arraycopy(tempCenter, 0, center, 0, ClusterClassNumber);

			}
		}
		return center;

	}

	/**
	 * double []data: data need Cluster
	 * 
	 * int ClusterClassNumber :Cluster number
	 * 
	 * int iteration :is the iteration times
	 * 
	 * return ClusterCenter : result of Cluster and last index is about the
	 * total cost ,the length of this array is ClusterClassNumber+1
	 * 
	 */

	public static double[] initClusterCenterWith(double []data,int ClusterClassNumber){

		double min=data[0];
		double max=data[0];
		for(int i=1;i<data.length;++i){
			if(data[i]>max) max=data[i];
			if(data[i]<min) min=data[i];
			
		}

//        Log.i("init->Center1:", min + " " + max + " ");

		double []Center=new double[ClusterClassNumber];
		
		double betten=(max-min)/(ClusterClassNumber-1);
		if(ClusterClassNumber==1)return Center;
		for(int i=0;i<ClusterClassNumber;++i){
			Center[i]=min+i*betten;
			
			
		}

//        Log.i("init->Center1:", Center[0] + " " + Center[1] + " " + Center[2]+" ");
		
		return Center;
	}
	
	
	public static double[] getKmeans(double[] data, int ClusterClassNumber, int iteration) {
		double[] ClusterCenter;
		// random initial Cluster center
		double[] beforeCenter = initClusterCenter(data, ClusterClassNumber);
//		double[] beforeCenter = initClusterCenterWith(data, ClusterClassNumber);
		ClusterCenter = beforeCenter;
		int count = 0;
		for (int ite = 0; ite < iteration; ++ite) {

			//find ClusterCenter

			ClusterCenter = findClosestClusterCenter(data, ClusterCenter, ClusterClassNumber);
			// save  total distance
			double errorNow = 0.0;
			for (int index = 0; index < ClusterClassNumber; ++index) {
				double temperor = ClusterCenter[index] - beforeCenter[index];
				errorNow += temperor * temperor;
			}
			if (errorNow < 0.00002) {
				++count;
			} else {
				count = 0;
			}
			// get break condition

			if (count >= 5)
				break;
		}
		return ClusterCenter;
	}

	/**
	 * double []data: data need Cluster
	 * 
	 * 
	 * int ClusterClassNumber :Cluster number
	 * 
	 * 
	 * return double[]ClusterCenterTemp :The initial cluster centers
	 * 
	 */

	public static double[] initClusterCenter(double[] data, int ClusterClassNumber) {

		Object randCenter[] = RandCreate.create(ClusterClassNumber, 0, data.length);
		double[] ClusterCenterTemp = new double[ClusterClassNumber];

		for (int iterator = 0; iterator < ClusterClassNumber; ++iterator) {

			int inde = (int) randCenter[iterator];

			ClusterCenterTemp[iterator] = data[inde];


		}

		return ClusterCenterTemp;
	}

	/**
	 * double []data :data that need Cluster
	 * 
	 * double []ClusterCenterTemp :The initial cluster centers
	 * 
	 * int ClusterCenterNumber : The number of clusters
	 * 
	 * return double[] NewCenter :result of Cluster and last index is about the
	 * total cost
	 * 
	 */

	public static double[] findClosestClusterCenter(double[] data, double[] ClusterCenterTemp,
			int ClusterCenterNumber) {

		// result of Cluster and last is about the total cost


		double[] NewCenter = new double[ClusterCenterNumber + 1];

		int[] CenterCount = new int[ClusterCenterNumber];

		double costNow = 0.0;

		for (int iterator = 0; iterator < data.length; ++iterator) {

			double minCost = 0;
			int minCostIndex = 0;

			for (int eachCenter = 0; eachCenter < ClusterCenterNumber; ++eachCenter) {

				double tempCost = data[iterator] - ClusterCenterTemp[eachCenter];
				tempCost = tempCost * tempCost;

				if (eachCenter < 1) {

					minCost = tempCost;
					minCostIndex = 0;

				} else if (tempCost < minCost) {

					minCost = tempCost;
					minCostIndex = eachCenter;
				}
			}

			costNow += minCost;

			CenterCount[minCostIndex] = CenterCount[minCostIndex] + 1;

			NewCenter[minCostIndex] = NewCenter[minCostIndex] + data[iterator];

		}



//        Log.i("calcu->tempCenter:40", CenterCount[0] + " " + CenterCount[1] + " " + CenterCount[2]);

		for (int ite = 0; ite < ClusterCenterNumber; ++ite) {

			if (CenterCount[ite] >= 1)
            {
                NewCenter[ite] = NewCenter[ite]/CenterCount[ite];
            }

			else{
				NewCenter[ite] = 0.0;
//                Log.i("calcu-tempCenter:41","nonono");
            }

		}

		NewCenter[ClusterCenterNumber] = costNow;

//        Log.i("calcu->tempCenter:42", NewCenter[0] + " " + NewCenter[1] + " " + NewCenter[2]+" "+NewCenter[3]);

		return NewCenter;

	}

}
