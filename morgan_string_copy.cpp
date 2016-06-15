#include <cstdio>
#include <algorithm>
#include <cstring>
#include <cmath>
#include <vector>
#include <iostream>
#include <string>

using namespace std;

struct entry {
    int nr[2];
    int p;
};

// compare two entry structs
int cmp(const entry& a,const entry& b) {
    return a.nr[0]==b.nr[0] ?(a.nr[1]<b.nr[1] ?1: 0): (a.nr[0]<b.nr[0] ?1: 0);
}

// get length of matching prefixes 
int get_matching_prefix_size(int *arr, int N, int stp, int x, int y) {
	int k, ret = 0;
	if (x == y) return N - x;
	for (k = stp-1; k >= 0 && x < N && y < N; k--)
        if (arr[k*N + x] == arr[k*N + y])
            x += 1 << k, y += 1 << k, ret += 1 << k;
	return ret;
}

// get lex_str 
void calculate_lex_str(const string& s1,const string& s2) {

	int l1 = s1.size(), l2 = s2.size();
	int N = l1 + l2;
	int n_steps = static_cast<int> (floor(log2(N)) + 1);
	int *P = new int[(n_steps + 1) * N]; 
	string sc = s1 + s2;
	int i;
	
	// generate suffix array 
	for(i = 0; i < N; i++) {
		P[i] = sc[i] - 'A';
	}

	struct entry L[N];
	int stp, cnt;

	for(stp = 1, cnt = 1; cnt < N; stp++, cnt *= 2) {

        for(i = 0; i < N; i++) {
            L[i].nr[0] = P[(stp- 1) * N + i];
            L[i].nr[1] = (i + cnt) < N? P[(stp- 1) * N + i+ cnt]:-1;
            L[i].p= i;
        }

        sort(L, L+N, cmp);
        for(i=0; i < N; i++)
            P[N*stp + L[i].p] = i> 0 && L[i].nr[0]==L[i-1].nr[0] && L[i].nr[1] == L[i- 1].nr[1] ? P[N*stp + L[i-1].p] : i;

    }

    // merge s1, s2 lexicographically
    
    int m_len = 0, is1, is2;
    string res;

    for(is1 = 0, is2 = l1; is1 < l1 && is2 < N;) {
    	
    	if(sc[is1] < sc[is2]) { // string 1 has smaller char in front
    		res += sc[is1];
    		is1++;
    	} else if(sc[is1] > sc[is2]) { // string 2 has smaller char in front
    		res += sc[is2];
    		is2++;
    	} else { // same front char search for max matching prefix

    		m_len = get_matching_prefix_size(P, N, n_steps, is1, is2);

    		if(is1 + m_len == l1 && is2 + m_len == N) { //case 1 full string match choose first string
    			res += sc[is1];
    			is1++;
    		} else if(is1 + m_len == l1 && is2 + m_len < N) { // case 2 first string is exhausted
    			if(sc[is1 + m_len -1] < sc[is2 + m_len]) {
    				res += sc[is1];
    				is1++;
    			} else {
    				res += sc[is2];
    				is2++;
    			}

    		} else if(is1 + m_len < l1 && is2 + m_len == N) { // case 3 second string is exhausted
    			if(sc[is1 + m_len] < sc[is2 + m_len -1]) {
    				res += sc[is1];
    				is1++;
    			} else {
    				res += sc[is2];
    				is2++;
    			}
    		} else { // none exhausted check for next element
    			if(sc[is1 + m_len] < sc[is2 + m_len]) {
    				res += sc[is1];
    				is1++;
    			} else {
    				res += sc[is2];
    				is2++;
    			}
    		}
    	}
    	// end if-else
    }
    // end for

    if(is1 != l1) 
    	res += sc.substr(is1, l1 - is1);

    if(is2 != N)
    	res += sc.substr(is2, N - is2);

    cout << res << endl;

    delete[] P;

}

int main() {
 	int T;
 	cin >> T;
 	string s1, s2;

 	while(T > 0) {
 		cin >> s1 >> s2;
 		calculate_lex_str(s1, s2);
 		T--;
 	}

    return 0;
}