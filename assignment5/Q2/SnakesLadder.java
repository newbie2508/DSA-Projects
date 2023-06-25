import java.io.*;
import java.util.*;

public class SnakesLadder extends AbstractSnakeLadders{
	int N, M;
	int snakes[];
	int ladders[];
	int[] atox;
	int[] ntox;
	int[] ascending;
	int[] descending;
	int sizeascending=0;
	int sizedescending=0;
	private void dorecursion(int num,int distance,ArrayList<ArrayList<Integer>> snakesforntox,ArrayList<ArrayList<Integer>> laddersforntox,boolean visitedarray[],Queue<Integer> bfs2){
		if(snakesforntox.get(num)==null && laddersforntox.get(num)==null && visitedarray[num]==false){
			bfs2.add(num);
			visitedarray[num]=true;
			ntox[num]=distance;
			return;
		}
		visitedarray[num]=true;
		ntox[num]=distance;
		bfs2.add(num);
		if(snakesforntox.get(num)!=null){
			for(int k=0;k<snakesforntox.get(num).size();k++){
				if(visitedarray[snakesforntox.get(num).get(k)]==false){
					dorecursion(snakesforntox.get(num).get(k), distance, snakesforntox, laddersforntox,visitedarray,bfs2);
				}
			}
		}
		if(laddersforntox.get(num)!=null){
			for(int k=0;k<laddersforntox.get(num).size();k++){
				if(visitedarray[laddersforntox.get(num).get(k)]==false){
					dorecursion(laddersforntox.get(num).get(k), distance, snakesforntox, laddersforntox,visitedarray,bfs2);
				}
			}
		}
	}
	public SnakesLadder(String name)throws Exception{
		File file = new File(name);
		BufferedReader br = new BufferedReader(new FileReader(file));
		N = Integer.parseInt(br.readLine());
        
        M = Integer.parseInt(br.readLine());

	    snakes = new int[N];
		ladders = new int[N];
	    for (int i = 0; i < N; i++){
			snakes[i] = -1;
			ladders[i] = -1;
		}
		for(int i=0;i<M;i++){
            String e = br.readLine();
            StringTokenizer st = new StringTokenizer(e);
            int source = Integer.parseInt(st.nextToken());
            int destination = Integer.parseInt(st.nextToken());

			if(source<destination){
				ladders[source] = destination;
			}
			else{
				snakes[source] = destination;
			}
        }
		br.close();
		ascending=new int[N];
		descending=new int[N];
		atox=new int[N+1];
		for(int i=0;i<=N;i++){
			atox[i]=0;
		}
		int distance=0;
		Queue<Integer> bfs=new LinkedList<Integer>();
		boolean visitedarray[]=new boolean[N+2];
		for(int i=0;i<=N+1;i++){
			visitedarray[i]=false;
		}
		bfs.add(0);
		visitedarray[0]=true;
		atox[0]=distance;
		int low=0;
		boolean yos=true;
		while(!bfs.isEmpty()){
			// if(yo==N){
			// 	break;
			// }
			distance++;
			int x=bfs.size();
			int max=0;
			for(Integer item: bfs) {
				if(max<item){
					max=item;
				}
			}
			if(max==100){
				yos=false;
			}
			if(yos){
				ascending[low]=max;
				sizeascending++;
				low++;
			}
			for(int j=0;j<=x-1;j++){
				// if(yo==N){
				// 	break;
				// }
				int top=bfs.peek();
				bfs.remove();
				for(int i=top+1;i<=top+6;i++){
					if(i>N){
						continue;
					}
					if(visitedarray[i]==true){
						continue;
					}
					if(i==N){
						bfs.add(i);
						visitedarray[i]=true;
						atox[i]=distance;
						continue;
					}
					if(snakes[i]!=-1 || ladders[i]!=-1){
						if(snakes[i]!=-1 && visitedarray[snakes[i]]){
							visitedarray[i]=true;
							atox[i]=distance;
							continue;
						}
						if(ladders[i]!=-1 && visitedarray[ladders[i]]){
							visitedarray[i]=true;
							atox[i]=distance;
							continue;
						}
						int curr=i;
						while(snakes[curr]!=-1 || ladders[curr]!=-1){
							visitedarray[curr]=true;
							atox[curr]=distance;
							if(snakes[curr]!=-1){
								if(visitedarray[snakes[curr]]==true){
									break;
								}
								curr=snakes[curr];
							}
							else if(ladders[curr]!=-1){
								if(visitedarray[ladders[curr]]==true){
									break;
								}
								curr=ladders[curr];
							}
						}
						if(snakes[curr]!=-1 || ladders[curr]!=-1){
							visitedarray[curr]=true;
							atox[curr]=distance;
							continue;
						}
						visitedarray[curr]=true;
						atox[curr]=distance;
						bfs.add(curr);
						continue;
					}
					bfs.add(i);
					visitedarray[i]=true;
					atox[i]=distance;
				}
			}	
		}
		for(int i=0;i<=N;i++){
			if(atox[i]==0){
				atox[i]=-1;
			}
		}
		ArrayList<ArrayList<Integer>> snakesforntox;
		ArrayList<ArrayList<Integer>> laddersforntox;
		snakesforntox=new ArrayList<ArrayList<Integer>>();
		laddersforntox=new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<=N-1;i++){
			snakesforntox.add(null);
			laddersforntox.add(null);
		}
		for(int i=0;i<=N-1;i++){
			if(ladders[i]!=-1){
				if(snakesforntox.get(ladders[i])!=null){
					snakesforntox.get(ladders[i]).add(i);
					continue;
				}
				ArrayList<Integer> yo=new ArrayList<Integer>();
				yo.add(i);
				snakesforntox.set(ladders[i],yo);
			}
		}
		for(int i=0;i<=N-1;i++){
			if(snakes[i]!=-1){
				if(laddersforntox.get(snakes[i])!=null){
					laddersforntox.get(snakes[i]).add(i);
					continue;
				}
				ArrayList<Integer> yo=new ArrayList<Integer>();
				yo.add(i);
				laddersforntox.set(snakes[i],yo);
			}
		}
		Queue<Integer> bfs2=new LinkedList<Integer>();
		ntox=new int[N+2];
		for(int i=0;i<=N+1;i++){
			ntox[i]=0;
		}
		for(int i=0;i<=N+1;i++){
			visitedarray[i]=false;
		}
		distance=0;
		bfs2.add(N);
		visitedarray[N]=true;
		ntox[N]=distance;
		low=0;
		boolean ans=true;
		while(!bfs2.isEmpty()){
			// if(yo==N){
			// 	break;
			// }
			distance++;
			int x=bfs2.size();
			int min=2147483641;
			for (Integer item: bfs2) {
				if(ans==false){
					break;
				}
				if(min>item){
					min=item;
				}
			}
			if(min==1){
				ans=false;
			}
			if(ans){
				descending[low]=min;
				low++;
				sizedescending++;
			}
			for(int j=0;j<=x-1;j++){
				// if(yo==N){
				// 	break;
				// }
				int top=bfs2.peek();
				bfs2.remove();
				for(int i=top-1;i>=top-6;i--){
					if(i<1){
						continue;
					}
					if(visitedarray[i]==true){
						continue;
					}
					if(i==1){
						bfs2.add(i);
						visitedarray[i]=true;
						ntox[i]=distance;
						continue;
					}
					if(i==N){
						bfs2.add(i);
						visitedarray[i]=true;
						ntox[i]=distance;
						continue;
					}
					if(snakes[i]!=-1){
						continue;
					}
					if(ladders[i]!=-1){
						continue;
					}
					if(snakesforntox.get(i)!=null || laddersforntox.get(i)!=null){
						visitedarray[i]=true;
						ntox[i]=distance;
						bfs2.add(i);
						if(snakesforntox.get(i)!=null){
							for(int k=0;k<snakesforntox.get(i).size();k++){
								if(visitedarray[snakesforntox.get(i).get(k)]==false){
									dorecursion(snakesforntox.get(i).get(k), distance, snakesforntox, laddersforntox,visitedarray,bfs2);
								}
							}
						}
						if(laddersforntox.get(i)!=null){
							for(int k=0;k<laddersforntox.get(i).size();k++){
								if(visitedarray[laddersforntox.get(i).get(k)]==false){
									dorecursion(laddersforntox.get(i).get(k), distance, snakesforntox, laddersforntox,visitedarray,bfs2);
								}
							}
						}
					}
					else if(snakesforntox.get(i)==null && laddersforntox.get(i)==null){
						bfs2.add(i);
						visitedarray[i]=true;
						ntox[i]=distance;
					}
				}
			}	
		}
		for(int i=0;i<=N+1;i++){
			if(ntox[i]==0){
				ntox[i]=-1;
			}
		}
	}
	public int OptimalMoves()
	{
		/* Complete this function and return the minimum number of moves required to win the game. */
		return atox[N];
	}

	public int Query(int x, int y){
		/* Complete this function and 
			return +1 if adding a snake/ladder from x to y improves the optimal solution, 
			else return -1. */
			int sum1=atox[x]+ntox[y];
			int sum2=atox[y]+ntox[x];
			int min=0;
			if(sum1<sum2){
				min=sum1;
			}
			else{
				min=sum2;
			}
			if(min<atox[N]){
				return 1;
			}
			return -1;
	}

	public int[] FindBestNewSnake()
	{
		int result[] = {-1, -1};
		/* Complete this function and 
			return (x, y) i.e the position of snake if adding it increases the optimal solution by largest value,
			if no such snake exists, return (-1, -1) */
		int min=0;
		if(atox[N]==-1){
			min=2147483641;
		}
		else{
			min=atox[N];
		}
		int i=sizeascending-1;
		int j=1;
		while(i>0 && j<sizedescending){
			if(ascending[i]>descending[j]){
				if(min>(i+j)){
					min=i+j;
					result[0]=ascending[i];
					result[1]=descending[j];
				}
				i--;
			}
			else{
				j++;
			}
		}
		if(atox[N]==-1){
			return result;
		}
		if(min<atox[N]){
			return result;
		}
		if(min>=atox[N]){
			result[0]=-1;
			result[1]=-1;
		}
		return result;
	}
	// public static void main(String[] args) throws Exception{
	// 	SnakesLadder yo=new SnakesLadder("input.txt");
	// 	// System.out.println(yo.OptimalMoves());
	// 	int[] ar=yo.FindBestNewSnake();
	// 	System.out.println(ar[0]);
	// 	System.out.println(ar[1]);
	// 	System.out.println(yo.OptimalMoves());
	// 	// System.out.println(yo.Query(2,1022));
	// 	// System.out.println(yo.Query(671,95));
	// 	// System.out.println(yo.Query(815,109));
	// 	// System.out.println(yo.Query(788,903));
	// }   
}