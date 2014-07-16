/****************************** WQUnionPathCompressionUF.java ******************************************************************
* ------ solves the following problem -----
* Weighted quick-union with path compression :: In the original solution they have missed the part where the size 
* needs to be re-adjusted too in find()
* WQUnionPathCompressionUF
* @author :: amit nagarkoti
**************************************************************************************************************/

import java.util.Scanner;

public class WQUnionPathCompressionUF {

  private int[] id;
  private int[] sz;
  private int N;

  public WQUnionPathCompressionUF (int size) {
   
   id = new int[size];
   sz = new int[size];
   N = size;
 
   for(int i=0;i<size;i++) {
     id[i] = i;
     sz[i] = 1; 
   }

  }

  public boolean connected (int p, int q) {
    return find(p) == find(q);
  }
  
  public int find (int p) {
    int root = p;
    
    // finding root
    while (root != id[root]) root = id[root];
    
    // path compression
    while(id[p] != root) {
      id[p] = root;
      sz[id[p]] = sz[id[p]] - sz[p]; // size gets decremented as node moves up
      p = id[p];
    }
    return root;
		
  }

  public void union (int p, int q) {

    int rootp = find(p);
    int rootq = find(q);
    
    if(rootp == rootq) return;

    if(sz[rootp] > sz[rootq]) {
      id[rootq] = rootp;
      sz[rootp] = sz[rootp] + sz[rootq];
    } else {
      id[rootp] = rootq;
      sz[rootq] = rootp; 
    }

    N--;   
  }

  public int count () {
	return N;
  }

  // test method
  public static void main (String[] args) {
    long startTime = System.currentTimeMillis();
    Scanner sc = new Scanner(new java.io.BufferedInputStream(System.in));
    int n = sc.nextInt();
    WQUnionPathCompressionUF wuf = new WQUnionPathCompressionUF(n);
    for(int i=0;i<n;i++) {
      wuf.union(sc.nextInt(), sc.nextInt());
    }
    System.out.println(wuf.count());
    System.out.println("execution took :: " + (System.currentTimeMillis() - startTime)/1000 + " s");
    
  }

}