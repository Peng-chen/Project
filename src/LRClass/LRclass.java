package LRClass;

public class LRclass {

	static final int ClassType = 5;
	static final int ThetaLength = 4;

	private static double[][] Theta1 = { 
			{ 72.30012, 4.89103, -2.50389, -8.97478 },
			{ 46.84104, -5.04412, 3.12906, -3.18320 },
			{ 36.90438, -0.87683, -6.05078, 2.67253 },
			{ -9.74464, 1.73633, -3.76342, 2.62070 }, 
			{ -62.17537, -0.78368, 3.42120, 1.70898 } 
			};
	
	private static double[][] Theta = {
			{42.30636, 5.99808, -1.26123, -8.21539},
	   {75.76820, -7.77723, 4.78430, -5.06709},
	   {39.43325, -2.16125, -4.48246, 1.99500},
	  {-10.04670, 2.05001, -4.31563, 2.90011},
	  {-62.07007, -0.79825 , 3.42563, 1.70745}};

	public static int getClass(double[] CountCenter) {

		int BiggestIndex = 0;  //概率最大的类的下标
		double BiggestProbality = 0.0;  //最大的概率

		for (int classIndex = 0; classIndex < ClassType; ++classIndex) {

			double tempProbality = 0;

			for (int thetaIndex = 1; thetaIndex < ThetaLength; ++thetaIndex)

				tempProbality += Theta[classIndex][thetaIndex] * CountCenter[thetaIndex - 1];

			tempProbality += Theta[classIndex][0];

			tempProbality = sigmoid(tempProbality);
			
			if (classIndex == 0)
				BiggestProbality = tempProbality;
			else {

				//获取概率最大的那个类别

				if (tempProbality >= BiggestProbality) {
					BiggestProbality = tempProbality;
					BiggestIndex = classIndex;
				}
			}
		}
		return BiggestIndex+1;
	}
	public static double sigmoid(double input) {

		return 1.0 / (1.0 + Math.exp(-input));

	}

}
