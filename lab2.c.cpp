#include "stdio.h"
#include "math.h"
#include "Windows.h"

#define h 0.2

int main()
{	
	double x, y;
	int i = 0, answer;

	printf("Choose number of task, 1 OR 2: ");
	scanf("%d",&answer);

	if (answer == 1)
	{
		printf("Input x in the range from 3 to 8: ");
		scanf("%lf", &x);

		while(i < 5)
		{
			if(x < 3 || x > 8)
			{
				printf("Wrong variable value, input x in the range from 3 to 8: ");
				scanf("%lf", &x);
				i++;
			}
			else 
				break;
		}

		if(i == 5)
		{
			printf("LOL\n");
			system("pause");
			return 0;
		}

		printf("   x   |   y    \n__________________\n");

		while(x <= 8 + 0.0001)
		{
			if (x < 5)
			{
				y = log (abs(abs(log(x) / log(3.0))) / log(4.0)) / log(5.0);
				printf("%.3lf  |    %.3lf\n", x, y);
			}
			else 
				if (x < 7)
				{
					y = 1.0 / (pow(x, 2.0) + 16.0);
					printf("%.3lf  |    %.3lf\n", x, y);
				}
				else
				{
					y = log(x) + cos(x);
					printf("%.3lf  |    %.3lf\n", x, y);
				}
			x+=h;
		}
			}
	else 
		if(answer == 2)
		{
			printf("Input x in the range from 0 to 0.2: ");
			scanf("%lf", &x);

			while(i <= 5)
			{
				if (x < 0 || x > 0.2)
				{
					printf("\nWrong variable value, input x in the range from 0 to 0.2: ");
					scanf("%lf", &x);
					i++;
				}
				else
					break;
			}

			if (i == 5)
				{
					printf("LOL\n");
					system ("pause");
					return 0;
				}

			printf("   x    |    y     \n_______________________\n");

			while (x <= 0.2)
			{
				int k = 1 , z;
				z = 2.0*k - 1;
				y = (pow(x, 2*k) - 1)/z;
					for (;z = 2; z--)
					{
						y = (pow(x, 2*k) - 1)/z;
					}

				x+=0.02;
				printf("%lf      |      %lf\n", x, y);		
			}
		}
		else
			printf("Programm is finished\n");

	system("pause");
	return 0;
}